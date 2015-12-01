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
		lastTime = System.currentTimeMillis();
		firstTime = true;
	}

	public void run() {
		System.out.println("clientwrite thread: first time");
		camH.confirmRead(cameraIndex);
		while (!this.isInterrupted()) {
			try {
			System.out.println("client write thread before request");
				camH.request(cameraIndex);
			System.out.println("client write thread after request");
				if (!firstTime)
					camH.waitInIdle(lastTime);
				try {
					output.write(1);
					// flush
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				lastTime = System.currentTimeMillis();
				if (firstTime) {
					firstTime = false;
				}

				try {
					output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					
				}
			} catch (Exception e) {
				
				System.out.println("write thread interupt");
				
			}
		}
		System.out.println("client write thread before write 2");
		try {
			output.write(0);
			output.flush();
			System.out.println("output was closed");
			output.close();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		camH.setThreadsInterrupted(true);
		
	}

}
