package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientWriteThread extends Thread {
	private OutputStream output;
	private CameraHandler camH;

	private long lastTime;
	private boolean firstTime;
	private int cameraIndex;

	/**
	 * Creates a write thread for the client
	 * 
	 * @param os
	 *            - the output stream
	 * @param camH
	 *            - the monitor
	 * @param cameraIndex
	 *            - the index of the camera
	 */
	public ClientWriteThread(OutputStream os, CameraHandler camH, int cameraIndex) {
		this.output = os;
		this.camH = camH;
		this.cameraIndex = cameraIndex;
		firstTime = true;
	}

	public void run() {
		camH.confirmRead(cameraIndex);
		lastTime = System.currentTimeMillis();
		try {
			while (!isInterrupted()) {
				camH.request(cameraIndex);
				if (!firstTime)
					camH.waitInIdle(lastTime);

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
			System.out.println("clientviewthread: should be dead");
			this.interrupt();
		}

	}
}