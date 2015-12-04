package client;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class ClientMonitor {
	private int index;
	private boolean[] packageRead;
	private boolean idleMode;
	private int offSyncImages;
	private final int offSyncLimit = 8;
	private boolean syncMode;
	private boolean isAutoMode;
	private final int BUFFERT_LIMIT = 50;
	private final int SYNC_DELAY = 500;
	public final static long MAX_DIFF = 200;
	public final static long DELAY_FOR_IDLE = 5000;
	private ArrayList<PriorityQueue<TimeStampedImage>> imageBuffers;

	public ClientMonitor() {
		imageBuffers = new ArrayList<PriorityQueue<TimeStampedImage>>(2);

		imageBuffers.add(new PriorityQueue<TimeStampedImage>());
		imageBuffers.add(new PriorityQueue<TimeStampedImage>());
		index = 0;
		packageRead = new boolean[2]; // true from start
		packageRead[0] = true;
		packageRead[1] = true;
		idleMode = true;
		offSyncImages = 0;
		syncMode = true;
		isAutoMode = true;

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
		while (imageBuffers.get(cameraIndex).size() > BUFFERT_LIMIT) {
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
				// e.printStackTrace();
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

	/**
	 * A blocking method that gives the oldest (since grabbed from a camera) of
	 * all images in all buffers
	 * 
	 * @return An appropiate image to show
	 */
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
		} else if (temp2 == null) {
			temp = imageBuffers.get(0).poll();

		} else {
			checkDelayDiff(temp1.getTimestamp(), temp2.getTimestamp());
			System.out.println("delay checked for sync");
			if (temp1.getTimestamp() > temp2.getTimestamp()) {

				temp = imageBuffers.get(1).poll();
			} else {
				temp = imageBuffers.get(0).poll();
			}
		}
		long delay = System.currentTimeMillis() - temp.getTimestamp();
		System.out.println("delay is " + delay);
		while (syncMode && delay < SYNC_DELAY) {
			try {
				wait(SYNC_DELAY - delay);
				delay = System.currentTimeMillis() - temp.getTimestamp();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("crnt size imgae buffer 1 " + imageBuffers.get(0).size() + " img buffer 2 = "
				+ imageBuffers.get(1).size());
		notifyAll();
		return temp;
	}

	private void checkDelayDiff(long temp1, long temp2) {
		if (isAutoMode) {
			long diff = temp1 - temp2;
			System.out.println("dif between pics is " + diff);
			if (Math.abs(diff) > MAX_DIFF) {
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

	/**
	 * A blocking method, blocks until appropiate time from has passed
	 * (DELAY_FOR_IDLE) since "lastTime"
	 * 
	 * @param lastTime
	 *            - lastTime a request for a picture was made
	 */
	public synchronized void waitInIdle(long lastTime) {

		if (idleMode) {
			long diff = System.currentTimeMillis() - lastTime;
			while ((diff < 5000) && idleMode) {
				try {
					System.out.println(diff);
					wait(5000 - diff);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				diff = System.currentTimeMillis() - lastTime;
			}

		}

	}

	/**
	 * Set current mode
	 * 
	 * @param boolean
	 *            true for idle mode false for movie mode
	 */
	public synchronized void setIdle(boolean b) {
		idleMode = b;
		notifyAll();

	}

	/**
	 * Return wheater or not system is in Idle mode
	 * 
	 * @return true if Idle mode false if Movie mode
	 */
	public synchronized boolean isIdleMode() {
		return idleMode;
	}

	/**
	 * Set current SyncMode
	 * 
	 * @param mode
	 *            true for sync and false for async
	 */
	public synchronized void setSyncMode(boolean mode) {
		syncMode = mode;
		notifyAll();

	}

	/**
	 * Return wheater or not system is in sync mode
	 * 
	 * @return true if sync mode false if async mode
	 */
	public synchronized boolean isSyncMode() {
		return syncMode;
	}

	/**
	 * Return wheater or not system is in Auto mode
	 * 
	 * @return true if Auto mode false if Manual mode
	 */
	public synchronized boolean isAutoMode() {
		return isAutoMode;
	}

	/**
	 * Set Auto mode
	 * 
	 * @param mode
	 *            true for auto and false for manual
	 */
	public synchronized void setAutoMode(boolean b) {
		isAutoMode = b;
		notifyAll();
	}

	/**
	 * Set current SyncMode
	 * 
	 * @param int
	 *            true for sync and false for async
	 */
	public synchronized void flushBuffert(int index) {
		imageBuffers.get(index).clear();
		notifyAll();

	}

	/**
	 * Blocking method - blocks until buffer is empty
	 * 
	 * @param int
	 *            index - the index of the buffer to wait for clear
	 */
	public synchronized void buffertConfirmedCleared(int index) {
		while (!imageBuffers.get(index).isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
