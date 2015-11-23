package client;

import java.util.ArrayList;

public class CameraHandler {
	private int index;
	private boolean packageRead;
	private TimeStampedImageComparator comparator;

	private ArrayList<ArrayList<TimeStampedImage>> imageBuffers;

	public CameraHandler() {
		imageBuffers = new ArrayList<ArrayList<TimeStampedImage>>(2);

		imageBuffers.add(new ArrayList<TimeStampedImage>());
		imageBuffers.add( new ArrayList<TimeStampedImage>());
		index = 0;
		packageRead = true; //true from start
		comparator= new TimeStampedImageComparator();
	}
/**
 * Get index of which imagebuffer to use
 * @return index
 */
	public synchronized int cameraIndex() {
		// TODO Auto-generated method stub
		int temp = index;
		index = (index + 1) % 2; // modolu division med 2 ifall vi anropar fler
									// gånger än 2?
		return temp;
	}

/**
 * Write to the imagebuffer specified by the index
 * @param timestamp - time the image was captured
 * @param motionDetected - true if motion was detected when the image was captured
 * @param image - the image
 * @param cameraIndex - index for the buffer
 */
	public synchronized void writeToBuffer(long timestamp, boolean motionDetected, byte[] image, int cameraIndex) {
		imageBuffers.get(cameraIndex).add(new TimeStampedImage(timestamp, motionDetected, image));

	}
/**
 * Blocking method that returns when the last messages has been handled. 
 * To be used to wait until read-thread has handled the answer of the most recent request.
 */
	public synchronized void request() {

		while (!packageRead) { // ändra logic ifall slow-mode
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		packageRead=false;
		notifyAll();
		
	}
	/**
	 * When read-thread has handled the answer of the most recent request
	 */
	public synchronized void confirmRead(){
		packageRead = true;
		notifyAll();
	}
	public synchronized TimeStampedImage getLatestImage(int index){ //should it remove?
		imageBuffers.get(index).sort(comparator); // reaally create new comp?
		return imageBuffers.get(index).remove(0);
	}
	public synchronized void getLatestImages(){
		
	}

}
