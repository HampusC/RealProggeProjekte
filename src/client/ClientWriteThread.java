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
		while (!isInterrupted()) {
			camH.request(cameraIndex);
			if(!firstTime)
			camH.waitInIdle(lastTime);
		
			try {
				output.write(1);
				// flush
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lastTime = System.currentTimeMillis();
			if(firstTime){
				firstTime = false;
			}
		}
	try {
		output.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


}
