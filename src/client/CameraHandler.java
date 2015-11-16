package client;

import java.util.ArrayList;

public class CameraHandler {
	
	private ArrayList<TimeStampedImage> imageBuffer;
	private ArrayList<TimeStampedImage> imageBuffer2;
	public CameraHandler(){
		imageBuffer = new ArrayList<TimeStampedImage>();
		imageBuffer2 = new ArrayList<TimeStampedImage>();
	}
	public boolean connectCamera(String address, int port){
	
		return false;
	}

}
