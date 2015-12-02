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

//	public void run1() {
//		// camH.newImage();
//		TimeStampedImage temp1 = camH.getLatestImage(0);
//		TimeStampedImage temp2 = camH.getLatestImage(1);
//		long time1 = temp1.getTimestamp();
//		long time2 = temp2.getTimestamp();
//
//		while (true) {
//			if ((temp1.getMotionDetected() || temp2.getMotionDetected()) && client.isAutoMode()) {
//				System.out.println(client.isAutoMode());
//				gui.setMode(Client.MOVIE_MODE);
//				System.out.println("mode motion changed to motion movie");
//			}
//			long diff = time1 - time2;
//			System.out.println("diff = " + diff);
//
//			// if more than ~10 images offsync "in row" make asyncro, if back to
//			// 0 make synchro
//			if (client.isAutoMode()) {
//				if (Math.abs(diff) > Client.MAX_DIFF) {
//					offSyncImages++;
//					if (offSyncImages > offSyncLimit) {
//						gui.setSyncType(Client.ASYNCHRONOUS_MODE);
//						offSyncImages = offSyncLimit;
//					}
//				} else {
//					offSyncImages--;
//					if (offSyncImages < 0) {
//						gui.setSyncType(Client.SYNCHRONOUS_MODE);
//						offSyncImages = 0;
//					}
//				}
//			}
//
//			if (client.isSyncMode()) {
//
//				if (diff > 0) {
//					gui.refresh(temp2.getImage(), 1, System.currentTimeMillis() - temp2.getTimestamp());
//
//					// camH.newImage();
//					temp2 = camH.getLatestImage(1);
//					time2 = temp2.getTimestamp();
//				}
//				if (diff < 0) {
//					gui.refresh(temp1.getImage(), 0, System.currentTimeMillis() - temp1.getTimestamp());
//
//					// camH.newImage();
//					temp1 = camH.getLatestImage(0);
//					time1 = temp1.getTimestamp();
//				} else {
//					gui.refresh(temp1.getImage(), 0, System.currentTimeMillis() - temp1.getTimestamp());
//					gui.refresh(temp2.getImage(), 1, System.currentTimeMillis() - temp2.getTimestamp());
//					// camH.newImage();
//					temp1 = camH.getLatestImage(0);
//					temp2 = camH.getLatestImage(1);
//					time1 = temp1.getTimestamp();
//					time2 = temp2.getTimestamp();
//				}
//			} else {
//
//				gui.refresh(temp1.getImage(), 0, System.currentTimeMillis() - temp1.getTimestamp()); // samma
//																										// som
//																										// ovanstående
//																										// else,
//				// duplicerad kod?
//				gui.refresh(temp2.getImage(), 1, System.currentTimeMillis() - temp2.getTimestamp());
//				// camH.newImage();
//				temp1 = camH.getLatestImage(0);
//				temp2 = camH.getLatestImage(1);
//				time1 = temp1.getTimestamp();
//				time2 = temp2.getTimestamp();
//			}
//		}
//
//	}

	public void run() {
		while (true) {
			TimeStampedImage img = camH.nextImageToShow();
			//set auto if motion detected
			checkMotion(img);
			
			gui.setAutoLabel(camH.isAutoMode());
			gui.setSyncTypeLabel(camH.isSyncMode());
			gui.setIdleModeLabel(camH.isIdleMode());
			gui.refresh(img.getImage(), img.getCameraIndex(), System.currentTimeMillis() - img.getTimestamp());
		}
	}
	/**
	 * Method to check for motion, uses the "motionDetected" attribute in TimeStampedImage
	 * @param img
	 */
	public void checkMotion(TimeStampedImage img){
		if (img.getMotionDetected() && camH.isAutoMode() && camH.isIdleMode()) {
			gui.setIdleMode(false);
			gui.motionTriggedBy(img.getCameraIndex());
			System.out.println("mode motion changed to motion movie");
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
