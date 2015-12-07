package server;

import java.io.IOException;
import java.io.OutputStream;

import se.lth.cs.eda040.proxycamera.AxisM3006V;

public class WriteThread extends Thread {

	private OutputStream os;
	private ServerMonitor sm;
	private AxisM3006V myCamera;

	public WriteThread(AxisM3006V myCamera, OutputStream os, ServerMonitor sm) {
		this.os = os;
		this.myCamera = myCamera;
		this.sm = sm;
	}

	public void run() {
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE + 10];
		try {
			while (!isInterrupted()) {
				sm.shouldSend();
				jpeg[0] = (byte) 1;
				int len = myCamera.getJPEG(jpeg, 6 + AxisM3006V.TIME_ARRAY_SIZE);
				jpeg[1] = (byte) (myCamera.motionDetected() ? 1 : 0);
				myCamera.getTime(jpeg, 2);
				jpeg[10] = (byte) (len >> 24);
				jpeg[11] = (byte) (len >> 16);
				jpeg[12] = (byte) (len >> 8);
				jpeg[13] = (byte) (len >> 0);
				os.write(jpeg);
				os.flush();
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			myCamera.close();
		}

	}

}
