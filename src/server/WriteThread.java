package server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class WriteThread extends Thread {

	private OutputStream os;
	private AxisM3006V camera;
	private ServerMonitor sm;

	public WriteThread(AxisM3006V camera, OutputStream os, ServerMonitor sm) {
		this.os = os;
		this.camera = camera;
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
		while (!sm.closedRequested()) {
			sm.shouldSend();
			jpeg[0] = (byte) 1; // 1 if image
			jpeg[1] = (byte) (camera.motionDetected() ? 1 : 0);
			camera.getTime(jpeg, 2);

			// the image, written on 14 and onwards
			int len = camera.getJPEG(jpeg, 6 + AxisM3006V.TIME_ARRAY_SIZE);
		
//			System.out.println("first bit in pic " + jpeg[6 + AxisM3006V.TIME_ARRAY_SIZE] + " last byte "
//					+ jpeg[6 + AxisM3006V.TIME_ARRAY_SIZE + len - 1]);

			// the length of the image, converted from int to 4 byte
			ByteBuffer b = ByteBuffer.allocate(4);
			b.putInt(len);
			byte[] result = b.array();
			for (int i = 0; i < 4; i++) {
				jpeg[i + 10] = result[i];
			}

			try {
				os.write(jpeg);
				os.flush(); // flushar den innan klienten hinner läsa den?
			
			} catch (IOException e) {
			break;
			}
		}
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sm.closeConfirmed();
	}
}
