package client;

public class TimeStampedImage implements Comparable {
	private long timestamp;
	private boolean motionDetected;
	private byte[] image;
	private int cameraNbr;

	/**
	 * Creates an image object with a timestamp and various extra attributes
	 * 
	 * @param timestamp
	 *            - the time the image was taken
	 * @param motionDetected
	 *            - true if motion was detected, false otherwise
	 * @param image
	 *            - the image
	 * @param cameraIndex
	 *            - the index of the camera the image was taken from
	 */
	public TimeStampedImage(long timestamp, boolean motionDetected, byte[] image, int cameraIndex) {
		this.timestamp = timestamp;
		this.motionDetected = motionDetected;
		this.image = image;
		this.cameraNbr = cameraIndex;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof TimeStampedImage) {
			TimeStampedImage temp = (TimeStampedImage) o;
			if (timestamp - temp.timestamp < 0) {
				return -1;
			} else if (timestamp - temp.timestamp > 0) {
				return 1;
			}
			return 0;
		}
		throw new ClassCastException("not of TimeStampedImage type");
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof TimeStampedImage) {
			if (this.compareTo(o) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the time of when the image was taken.
	 * 
	 * @return - the time
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Return the image as an byte array
	 * 
	 * @return - the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Return wheater or not motion was detected
	 * 
	 * @return - true if motion was detected, false otherwise
	 */
	public boolean getMotionDetected() {
		return motionDetected;
	}

	/**
	 * Return the index of the camera who took this image
	 * 
	 * @return - the index
	 */
	public int getCameraIndex() {
		return cameraNbr;
	}

}
