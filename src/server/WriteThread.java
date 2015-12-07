package server;

import java.io.IOException;
import java.io.OutputStream;

import se.lth.cs.eda040.realcamera.AxisM3006V;

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
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE + 10]; // kanske
																	// image
																	// buffer
																	// size inte
																	// är
																	// längden
																	// av bilden
																	// (getJPEG
																	// kanske är
																	// istället)
		System.out.println("write thread virginity");
	
		try{
		while (!isInterrupted()) {
			System.out.println("write before should");
			sm.shouldSend();
		
			jpeg[0] = (byte) 1; // 1 if image

			// the image, written on 14 and onwards
			System.out.println("write after should");
			int len = myCamera.getJPEG(jpeg, 6 + AxisM3006V.TIME_ARRAY_SIZE);
			System.out.println("write after getjpeg");
			jpeg[1] = (byte) (myCamera.motionDetected() ? 1 : 0);
			System.out.println("write after motiondeteccted");
			myCamera.getTime(jpeg, 2);
			System.out.println("write after gettime");
//			System.out.println("first bit in pic " + jpeg[6 + AxisM3006V.TIME_ARRAY_SIZE] + " last byte "
//					+ jpeg[6 + AxisM3006V.TIME_ARRAY_SIZE + len - 1]);

			// the length of the image, converted from int to 4 byte
			jpeg[10] = (byte) (len >> 24);
			jpeg[11] = (byte) (len >> 16);
			jpeg[12] = (byte) (len >> 8);
			jpeg[13] = (byte) (len >> 0);

			
			os.write(jpeg);
			System.out.println("write after write");
			os.flush(); // flushar den innan klienten hinner läsa den?
			
			
		}
		os.flush();
		os.close();
		//myCamera.close(); //finns myCamera.destroy();
			
		} catch (IOException e) {

			myCamera.close();
			//e.printStackTrace();
			}
			
		}
		
	
}
