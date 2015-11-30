package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ViewThread extends Thread {
	private CameraHandler camH;
	private GUI gui;
	private Client client;
	private int offSyncImages;
	private final int offSyncLimit = 10;

	public ViewThread(CameraHandler camH, Client client) {
		this.camH = camH;
		gui = new GUI(client);
		this.client = client;
		offSyncImages = 0;

	}

	public void run() {
		camH.newImage();

		TimeStampedImage temp1 = camH.getLatestImage(0);
		TimeStampedImage temp2 = camH.getLatestImage(1);
		long time1 = temp1.getTimestamp();
		long time2 = temp2.getTimestamp();

		while (true) {
			long diff = time1 - time2;
			System.out.println("diff = " + diff);

			// if more than ~10 images offsync "in row" make asyncro, if back to
			// 0 make synchro
			if (Math.abs(diff) > Client.MAX_DIFF) {
				offSyncImages++;
				if (offSyncImages > offSyncLimit) {
					// gui.setsynchroniuz(false)
					offSyncImages = offSyncLimit;
				}
			} else {
				offSyncImages--;
				if (offSyncImages < 0) {
					// gui.setsynchroniuz(true)
					offSyncImages = 0;
				}
			}
			// ha en if som kollar synchronious mode, if true gör det nedan
			if (client.isSyncMode()) {
				System.out.println("mode = sync");
				if (diff > 0) {
					gui.refresh(temp2.getImage(), 1);
			
					camH.newImage();
					temp2 = camH.getLatestImage(1);
					time2 = temp2.getTimestamp();
				}
				if (diff < 0) {
					gui.refresh(temp1.getImage(), 0);
				
					camH.newImage();
					temp1 = camH.getLatestImage(0);
					time1 = temp1.getTimestamp();
				} else {
					gui.refresh(temp1.getImage(), 0);
					gui.refresh(temp2.getImage(), 1);
					camH.newImage();
					temp1 = camH.getLatestImage(0);
					temp2 = camH.getLatestImage(1);
					time1 = temp1.getTimestamp();
					time2 = temp2.getTimestamp();
				}
			} else {
				System.out.println("mode = NoSync");
				gui.refresh(temp1.getImage(), 0); // samma som ovanstående else,
													// duplicerad kod?
				gui.refresh(temp2.getImage(), 1);
				camH.newImage();
				temp1 = camH.getLatestImage(0);
				temp2 = camH.getLatestImage(1);
				time1 = temp1.getTimestamp();
				time2 = temp2.getTimestamp();
			}
			// else{
			// long diff = time2-time1;
			// System.out.println(" (time2) diffen är " +diff);
			// gui.refresh(temp1.getImage(), 0);
			// try {
			// sleep(diff);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// gui.refresh(temp2.getImage(), 1);
			// }

			// if (temp1 != null) {
			// gui.refresh(temp1.getImage(), 0);
			// }
			// if (temp2 != null) {
			// gui.refresh(temp2.getImage(), 1);
			// }
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
