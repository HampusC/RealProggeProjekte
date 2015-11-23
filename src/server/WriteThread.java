package server;

import java.io.IOException;
import java.io.OutputStream;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class WriteThread extends Thread {
	
	private OutputStream os;
	private AxisM3006V camera;
	private ServerMonitor sm;
	
	public WriteThread(AxisM3006V camera, OutputStream os, ServerMonitor sm){
		this.os = os;
		this.camera = camera;
		this.sm=sm;
	}
	
	public void run(){
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE + 10]; //kanske image buffer size inte är längden av bilden (getJPEG kanske är istället)
		while(true) {
			sm.shouldSend();
			jpeg[0] = (byte) 1;
			jpeg[1] = (byte) (camera.motionDetected() ? 1 : 0);
			camera.getTime(jpeg, 3);
			int len = camera.getJPEG(jpeg, 3 + AxisM3006V.TIME_ARRAY_SIZE);
			jpeg[2] = (byte) len;
			try {
				os.write(jpeg);
				os.flush(); // flushar den innan klienten hinner läsa den?
				System.out.println("sent");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
