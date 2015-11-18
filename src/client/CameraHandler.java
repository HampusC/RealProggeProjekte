package client;

import java.util.ArrayList;

public class CameraHandler {
	private int index;
	
	private ArrayList<ArrayList<TimeStampedImage>> imageBuffers;

	
	
	
	public CameraHandler(){
		imageBuffers = new ArrayList<ArrayList<TimeStampedImage>>(2);
		
		imageBuffers.set(0, new ArrayList<TimeStampedImage>());
		imageBuffers.set(1, new ArrayList<TimeStampedImage>());
		index =0;
	}


	public int cameraIndex() {
		// TODO Auto-generated method stub
		int temp = index;
		index= (index+1)%2; // modolu division med 2 ifall vi anropar fler gånger än 2?
		return temp;
	}


}
