package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientWriteThread extends Thread {
	private OutputStream output;
	private ClientMonitor monitor;

	private long lastTime;
	private boolean firstTime;
	private int cameraIndex;

	/**
	 * Creates a write thread for the client
	 * 
	 * @param os
	 *            - the output stream
	 * @param monitor
	 *            - the client monitor
	 * @param cameraIndex
	 *            - the index of the camera
	 */
	public ClientWriteThread(OutputStream os, ClientMonitor monitor, int cameraIndex) {
		this.output = os;
		this.monitor = monitor;
		this.cameraIndex = cameraIndex;
		firstTime = true;
	}

	public void run() {
		monitor.confirmRead(cameraIndex);
		lastTime = System.currentTimeMillis();
		try {
			while (!isInterrupted()) {
				monitor.request(cameraIndex);
				if (!firstTime)
					monitor.waitInIdle(lastTime);

				output.write(1);
				output.flush();
				lastTime = System.currentTimeMillis();
				if (firstTime) {
					firstTime = false;
				}

			}

			output.flush();
			output.close();
		} catch (IOException e) {
			this.interrupt();
		}

	}
}