package client;


public class ViewThread extends Thread {
	private ClientMonitor monitor;
	private GUI gui;

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
	 * @param img
	 */
	public void checkMotion(TimeStampedImage img) {
		if (img.getMotionDetected() && monitor.isAutoMode() && monitor.isIdleMode()) {
			gui.setIdleMode(false);
			gui.motionTriggedBy(img.getCameraIndex());
		}
	}


}
