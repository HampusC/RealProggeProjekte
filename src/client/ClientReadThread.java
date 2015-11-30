package client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class ClientReadThread extends Thread {
	private InputStream input;
	private CameraHandler camH;
	private int cameraIndex;

	public ClientReadThread(InputStream is, CameraHandler camH, int cameraIndex) {
		this.input = is;
		this.cameraIndex = cameraIndex;
		this.camH = camH;
	}

	public void run() {
		while (true) {

			int maxToRead = AxisM3006V.IMAGE_BUFFER_SIZE + 10; // should be + 14
			byte[] buffer = new byte[maxToRead];
			int read = 0;
			int result = 0;
			while (read < maxToRead && result != -1) {
				try {
					result = input.read(buffer, read, maxToRead - read);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (result != -1)
					read = read + result;
			}
			// bufffer logic
			if (buffer[0] == 1) { // vilkor = är bild

				boolean motionDetected;
				if (buffer[1] == 1) {
					motionDetected = true;
					System.out.println("motion detected");

				} else {
					motionDetected = false;
				}

				// Timestamp 8 bytes
				long timestamp = 0;
				for (int i = 2; i < 10; i++) {
					timestamp = (timestamp << 8) + (buffer[i] & 0xff);
				}
				// Image, lentgh long
				ByteBuffer b = ByteBuffer.allocate(4);
				b.put(buffer, 10, 4);
				b.position(0);
				int length = b.getInt();
				
				byte[] image = Arrays.copyOfRange(buffer, 14, length + 14); // ngra
																			// av
																			// bytsen
																			// i
																			// buffer
				// System.out.println(" recived: first bit in pic " + image[0]+
				// " last byte " + image[image.length-1]);
				camH.writeToBuffer(timestamp, motionDetected, image, cameraIndex);
			}
			camH.confirmRead(cameraIndex);
		}

	}
}
