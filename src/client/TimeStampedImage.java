package client;

public class TimeStampedImage implements Comparable {
	private long timestamp;
	private boolean motionDetected;
	private byte[] image;
	private int cameraNbr;

	public TimeStampedImage(long timestamp, boolean motionDetected, byte[] image, int cameraNbr) {
		this.timestamp = timestamp;
		this.motionDetected = motionDetected;
		this.image = image;
		this.cameraNbr = cameraNbr; 
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
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
	public boolean equals(Object o){ // bilder med samma timstamp men olika bildarrays kommer vara equal varandra
		if (o instanceof TimeStampedImage) {
			if(this.compareTo(o) == 0){
				return true;
			}
		}
		return false;
	}

	public long getTimestamp() {
		// TODO Auto-generated method stub
		return timestamp;
	}

	public byte[] getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	public boolean getMotionDetected(){
		return motionDetected;
	}

	public int getCameraIndex() {
		// TODO Auto-generated method stub
		return cameraNbr;
	}
	
}
