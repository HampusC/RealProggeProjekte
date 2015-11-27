package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ViewThread extends Thread {
	CameraHandler camH;
	GUI gui;

	public ViewThread(CameraHandler camH, Client client) {
		this.camH = camH;
		gui = new GUI(client);

	}

	public void run() {
		while (true) {
			camH.newImage();

			TimeStampedImage temp1 = camH.getLatestImage(0);
			TimeStampedImage temp2 = camH.getLatestImage(1);
			
			if (temp1 != null) {
				gui.refresh(temp1.getImage(), 0);
			}
			if (temp2 != null) {
				gui.refresh(temp2.getImage(), 1);
			}
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
			gui.refresh(imageInByte, 0);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
