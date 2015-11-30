package client;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class CameraHandler {
	private int index;
	private boolean[] packageRead;
	private boolean idleMode;
	
	private TimeStampedImageComparator comparator;
	

	private ArrayList<PriorityQueue<TimeStampedImage>> imageBuffers;

	public CameraHandler() {
		imageBuffers = new ArrayList<PriorityQueue<TimeStampedImage>>(2);

		imageBuffers.add(new PriorityQueue<TimeStampedImage>()); //tänk över struktur
		imageBuffers.add( new PriorityQueue<TimeStampedImage>());
		index = 0;
		packageRead = new boolean[2]; //true from start
		packageRead[0] = true;
		packageRead[1] = true;
		idleMode = true;
		
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
	
		notifyAll();
		

	}
/**
 * Blocking method that returns when the last messages has been handled. 
 * To be used to wait until read-thread has handled the answer of the most recent request.
 */
	public synchronized void request(int cameraIndex) {

		while (!packageRead[cameraIndex]) { // ändra logic ifall slow-mode
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		packageRead[cameraIndex]=false;
		notifyAll();
		
	}
	/**
	 * When read-thread has handled the answer of the most recent request
	 */
	public synchronized void confirmRead(int cameraIndex){
		packageRead[cameraIndex] = true;
		notifyAll();
	}
	public synchronized boolean isEmpty(int index){
		return imageBuffers.get(index).isEmpty();
	}
	public synchronized TimeStampedImage getLatestImage(int index){ //should it remove?
		if(imageBuffers.get(index).isEmpty()){
			return null; //throw error?
		}
		

		TimeStampedImage temp = imageBuffers.get(index).poll();
		notifyAll();
		return temp;
	}
	public synchronized void newImage() {
		while(imageBuffers.get(0).isEmpty() || imageBuffers.get(1).isEmpty() ){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("imnages ready");
	}
	public synchronized boolean idleMode(){
		return idleMode;
	}
	public synchronized void setIdle(boolean b) {
		idleMode = b;
		
	}



}
