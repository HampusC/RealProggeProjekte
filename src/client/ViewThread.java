package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ViewThread extends Thread {
	private ClientMonitor monitor;
	private GUI gui;
	private Client client;
	private int offSyncImages;
	private final int offSyncLimit = 10;

	/**
	 * Creates a viewthread to update the GUI
	 * 
	 * @param monitor
	 *            - the client monitor
	 * @param client
	 *            - the client
	 */
	public ViewThread(ClientMonitor monitor, Client client) {
		this.monitor = monitor;
		gui = new GUI(client, monitor);
		this.client = client;
		offSyncImages = 0;

	}

	public void run() {
		while (true) {
			TimeStampedImage img = monitor.nextImageToShow();
			checkMotion(img);

			gui.setAutoLabel(monitor.isAutoMode());
			gui.setSyncTypeLabel(monitor.isSyncMode());
			gui.setIdleModeLabel(monitor.isIdleMode());
			gui.refresh(img.getImage(), img.getCameraIndex(), System.currentTimeMillis() - img.getTimestamp());
		}
	}

	/**
	 * Method to check for motion, uses the "motionDetected" attribute in
	 * TimeStampedImage
	 * 
	 * @param img
	 */
	public void checkMotion(TimeStampedImage img) {
		if (img.getMotionDetected() && monitor.isAutoMode() && monitor.isIdleMode()) {
			gui.setIdleMode(false);
			gui.motionTriggedBy(img.getCameraIndex());
		}
	}

	/**
	 * Method to display a static test image.
	 */
	private void testImage() {
		byte[] imageInByte;

		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(new File("Images/lena.jpg"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
			gui.refresh(imageInByte, 0, 0);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
