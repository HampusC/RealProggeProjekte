package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ViewThread extends Thread {
	CameraHandler camH;
	GUI gui;

	public ViewThread(CameraHandler camH) {
		this.camH = camH;
		gui = new GUI();

	}

	public void run() {
		testImage();
		 while(true){
			 
		
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
			gui.refresh(imageInByte);
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ViewThread viewThread = new ViewThread(new CameraHandler());
		viewThread.start();
	}
}
