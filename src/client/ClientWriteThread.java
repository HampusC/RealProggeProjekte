package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientWriteThread extends Thread {
	private OutputStream output;
	private CameraHandler camH;

	private long lastTime;
	private boolean firstTime;
	private int cameraIndex;

	public ClientWriteThread(OutputStream os, CameraHandler camH, int cameraIndex) {
		this.output = os;
		this.camH = camH;
		this.cameraIndex = cameraIndex;
		
		firstTime = true;
	}

	public void run() {
		System.out.println("clientwrite thread: first time");
		camH.confirmRead(cameraIndex);
		lastTime = System.currentTimeMillis();
		try {
			while (!isInterrupted()) {

				camH.request(cameraIndex);
				
				
				if (!firstTime)
					camH.waitInIdle(lastTime);

				output.write(1);
				output.flush();
				System.out.println("client reqest img");
				// flush

				lastTime = System.currentTimeMillis();
				if (firstTime) {
					firstTime = false;
				}


			}

			output.flush();
			output.close();
			// try {
			// output.write(0);
			// output.flush();
			// System.out.println("output was closed");
			// output.close();
			// } catch (IOException e1) {
			// //e1.printStackTrace();
			// }

		} catch (IOException e) {
			System.out.println("cliwentviewthread: should be dead");
			e.printStackTrace();
			this.interrupt();
		}

	}
}