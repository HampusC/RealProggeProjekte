package client;

import java.util.ArrayList;

public class CameraHandler {
	private int index;
	private boolean packageRead;

	private ArrayList<ArrayList<TimeStampedImage>> imageBuffers;

	public CameraHandler() {
		imageBuffers = new ArrayList<ArrayList<TimeStampedImage>>(2);

		imageBuffers.set(0, new ArrayList<TimeStampedImage>());
		imageBuffers.set(1, new ArrayList<TimeStampedImage>());
		index = 0;
	}

	public synchronized int cameraIndex() {
		// TODO Auto-generated method stub
		int temp = index;
		index = (index + 1) % 2; // modolu division med 2 ifall vi anropar fler
									// gånger än 2?
		return temp;
	}

	public synchronized void byteToRead() {
		// TODO Auto-generated method stub

	}

	public synchronized void writeToBuffer(long timestamp, boolean motionDetected, byte[] image, int cameraIndex) {
		imageBuffers.get(cameraIndex).add(new TimeStampedImage(timestamp, motionDetected, image));

	}

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
	public synchronized void confirmRead(){
		packageRead = true;
		notifyAll();
	}

}
