package client;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class CameraHandler {
	private int index;
	private boolean[] packageRead;
	private boolean idleMode;
	private int offSyncImages;
	private final int offSyncLimit = 3;
	private boolean syncMode;
	private boolean oneCamera;
	private boolean threadsInterrupted;
	private boolean isAutoMode;
	private final int BUFFERT_LIMIT= 5;
	private final int SYNC_DELAY= 600;

	private TimeStampedImageComparator comparator;

	private ArrayList<PriorityQueue<TimeStampedImage>> imageBuffers;

	public CameraHandler() {
		imageBuffers = new ArrayList<PriorityQueue<TimeStampedImage>>(2);

		imageBuffers.add(new PriorityQueue<TimeStampedImage>()); // tänk över
																	// struktur
		imageBuffers.add(new PriorityQueue<TimeStampedImage>());
		index = 0;
		packageRead = new boolean[2]; // true from start
		packageRead[0] = true;
		packageRead[1] = true;
		idleMode = true;
		offSyncImages = 0;
		syncMode = true;
		oneCamera = true;
		threadsInterrupted = false;
		isAutoMode=true;

		comparator = new TimeStampedImageComparator();

	}

	/**
	 * Get index of which imagebuffer to use
	 * 
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
	 * 
	 * @param timestamp
	 *            - time the image was captured
	 * @param motionDetected
	 *            - true if motion was detected when the image was captured
	 * @param image
	 *            - the image
	 * @param cameraIndex
	 *            - index for the buffer
	 */
	public synchronized void writeToBuffer(long timestamp, boolean motionDetected, byte[] image, int cameraIndex) {
		while(imageBuffers.get(cameraIndex).size()>BUFFERT_LIMIT){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imageBuffers.get(cameraIndex).add(new TimeStampedImage(timestamp, motionDetected, image, cameraIndex));

		notifyAll();

	}

	/**
	 * Blocking method that returns when the last messages has been handled. To
	 * be used to wait until read-thread has handled the answer of the most
	 * recent request.
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
		packageRead[cameraIndex] = false;
		notifyAll();

	}

	/**
	 * When read-thread has handled the answer of the most recent request
	 */
	public synchronized void confirmRead(int cameraIndex) {
		packageRead[cameraIndex] = true;
		notifyAll();
	}

	public synchronized boolean isEmpty(int index) {
		return imageBuffers.get(index).isEmpty();
	}

	public synchronized TimeStampedImage getLatestImage(int index) { // should
																		// it
																		// remove?

		while (imageBuffers.get(index).isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		TimeStampedImage temp = imageBuffers.get(index).poll();
		notifyAll();
		System.out.println("crnt size imgae buffer 1 " + imageBuffers.get(0).size() + " img buffer 2 = "
				+ imageBuffers.get(1).size());
		return temp;
	}

	public synchronized TimeStampedImage nextImageToShow() {
		TimeStampedImage temp = null;
		
			while (imageBuffers.get(0).isEmpty() && imageBuffers.get(1).isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			

			TimeStampedImage temp1 = imageBuffers.get(0).peek();
			TimeStampedImage temp2 = imageBuffers.get(1).peek();
			if (temp1 == null) {
				temp = imageBuffers.get(1).poll();
			} else if(temp2 ==null) {
				temp = imageBuffers.get(0).poll();

			} else{
				checkDelayDiff(temp1.getTimestamp(), temp2.getTimestamp());
				System.out.println("delay checked for sync");
				if (temp1.getTimestamp() > temp2.getTimestamp()) {

					temp = imageBuffers.get(1).poll();
				} else {
					temp = imageBuffers.get(0).poll();
				}
			}
			long delay = System.currentTimeMillis()-temp.getTimestamp();
			System.out.println("delay is " + delay);
		while(syncMode&&delay<SYNC_DELAY){
				try {
					wait(SYNC_DELAY-delay);
					delay = System.currentTimeMillis()-temp.getTimestamp();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		System.out.println("crnt size imgae buffer 1 " + imageBuffers.get(0).size() + " img buffer 2 = "
				+ imageBuffers.get(1).size());
		notifyAll();
		return temp;
			
//			if (temp1.getTimestamp() > temp2.getTimestamp()) {
//
//				temp = imageBuffers.get(1).poll();
//			} else {
//				temp = imageBuffers.get(0).poll();
//			}
//
//			System.out.println("sync mode");
//
//			notifyAll();
//
//		} else {
//			while (imageBuffers.get(0).isEmpty() && imageBuffers.get(1).isEmpty()) {
//				try {
//					wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//			TimeStampedImage temp1 = imageBuffers.get(0).peek();
//			TimeStampedImage temp2 = imageBuffers.get(1).peek();
//			if (temp1 == null) {
//				temp = imageBuffers.get(1).poll();
//			} else {
//				temp = imageBuffers.get(0).poll();
//
//			}
//			System.out.println("no sync mode");
//		}
//		return temp;
	}

	private void checkDelayDiff(long temp1, long temp2) {
		if (isAutoMode) {
			long diff = temp1 - temp2;
			System.out.println("dif between pics is " + diff);
			if (Math.abs(diff) > Client.MAX_DIFF) {
				offSyncImages++;
				if (offSyncImages > offSyncLimit) {
					syncMode = false;
					offSyncImages = offSyncLimit;
					System.out.println("syncMode false");
					notifyAll();
				}
			} else {
				offSyncImages--;
				if (offSyncImages < 0) {
					syncMode = true;
					System.out.println("syncMode true");
					offSyncImages = 0;
					notifyAll();
				}
			}

		}
	}

	// public synchronized void newImage() {
	// while(imageBuffers.get(0).isEmpty() || imageBuffers.get(1).isEmpty() ){
	// try {
	// wait();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// }
	
	public synchronized void waitInIdle(long lastTime) {

		if (idleMode) {
			long diff = System.currentTimeMillis() - lastTime;
			while ((diff < 5000) && idleMode) {
				try {
					System.out.println(diff);
					wait(5000 - diff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				diff = System.currentTimeMillis() - lastTime;
			}

		}

	}

	public synchronized void setIdle(boolean b) {
		idleMode = b;
		notifyAll();

	}
	
	public synchronized boolean isIdleMode(){
		return idleMode;
	}

	public synchronized void setSyncMode(boolean mode) {
		syncMode = mode;
		notifyAll();

	}

	public synchronized boolean isSyncMode() {
		// TODO Auto-generated method stub
		return syncMode;
	}

	public synchronized void onlyOneCamera(boolean b) {
		oneCamera = b;
		System.out.println("onbly one camera = " + b);
		notifyAll();

	}

//	public synchronized void waitForInterrupted() {
//		while (!threadsInterrupted) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		threadsInterrupted = false;
//		notifyAll();
//
//	}
//
//	public synchronized void setThreadsInterrupted(boolean threadsInterrupted) {
//		this.threadsInterrupted = threadsInterrupted;
//		notifyAll();
//	}

	public synchronized boolean isAutoMode() {
		return isAutoMode;
	}

	public synchronized void setAutoMode(boolean b) {
		isAutoMode=b;
		notifyAll();
	}

	public synchronized void flushBuffert(int index) {
	imageBuffers.get(index).clear();
		
	}

}
