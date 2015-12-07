package client;

import java.util.Comparator;

public class TimeStampedImageComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		TimeStampedImage temp1 = (TimeStampedImage) o1;
			TimeStampedImage temp2 = (TimeStampedImage) o2;
			long timestamp1 = temp1.getTimestamp() ;
			long timestamp2 = temp2.getTimestamp() ;
			if (timestamp1 - timestamp2 < 0) {
				return -1;
			} else if (timestamp1- timestamp2 > 0) {
				return 1;
			}
			return 0;
		}
	

}
