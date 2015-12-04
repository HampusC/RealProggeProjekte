package client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class ClientReadThread extends Thread {
	private InputStream input;
	private ClientMonitor monitor;
	private int cameraIndex;

	/**
	 * Creates the read thread in the client
	 * @param is - the input stream
	 * @param monitor - the client monitor
	 * @param cameraIndex - the index of the camera
	 */
	public ClientReadThread(InputStream is, ClientMonitor monitor, int cameraIndex) {
		this.input = is;
		this.cameraIndex = cameraIndex;
		this.monitor = monitor;
	}

	public void run() {
		try {
			while (!isInterrupted()) {

				int maxToRead = AxisM3006V.IMAGE_BUFFER_SIZE + 10; // should be
																	// + 14
				byte[] buffer = new byte[maxToRead];
				int read = 0;
				int result = 0;

				while (read < maxToRead && result != -1) {
					result = input.read(buffer, read, maxToRead - read);
					if (result != -1)
						read = read + result;
				}
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
					// length of the image, long
					ByteBuffer b = ByteBuffer.allocate(4);
					b.put(buffer, 10, 4);
					b.position(0);
					int length = b.getInt();
					
					//the image
					byte[] image = Arrays.copyOfRange(buffer, 14, length + 14); 
					System.out.println("before buffer - delay is " + (System.currentTimeMillis()-timestamp));
					monitor.writeToBuffer(timestamp, motionDetected, image, cameraIndex);
				
				}
				monitor.confirmRead(cameraIndex);

			}
			input.close();
			
		} catch (IOException e) {
			monitor.confirmRead(cameraIndex);
		}

	}
}
