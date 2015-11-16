package client;

import java.util.ArrayList;

public class CameraHandler {
	
	private ArrayList<TimeStampedImage> imageBuffer;
	private ArrayList<TimeStampedImage> imageBuffer2;
	private ArrayList<CameraSocketHandler> cameraSockets;
	
	public CameraHandler(){
		imageBuffer = new ArrayList<TimeStampedImage>();
		imageBuffer2 = new ArrayList<TimeStampedImage>();
	}
	public boolean connectCamera(String address, int port){ //tänk på hur disconnect och numrering påverkar
	return cameraSockets.add(new CameraSocketHandler(address,port));
	
		
	}

}
