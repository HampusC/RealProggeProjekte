package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientWriteThread extends Thread {
	private OutputStream output;
	private CameraHandler camH;
	private boolean idleMode;
	private long lastTime;
	private boolean firstTime;

	public ClientWriteThread(OutputStream os, CameraHandler camH) {
		this.output = os;
		this.camH = camH;
		lastTime = System.currentTimeMillis();
		firstTime = true;
	}

	public void run() {
		while (true) {
			camH.request();
			// try {
			// sleep(1000); //used to slow down, remove
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			if (idleMode) {
				long diff = System.currentTimeMillis() - lastTime;
				while (diff < 5000) {
					try {
						sleep(5000 - diff);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					diff = System.currentTimeMillis() - lastTime;
				}
			}
			if (firstTime) {
				firstTime = false;
			}
			try {
				output.write(1);
				// flush
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastTime = System.currentTimeMillis();
		}
	}

	public void idleMode(boolean b) {
		idleMode = b;
	}

}
