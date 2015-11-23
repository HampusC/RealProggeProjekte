package client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class ClientReadThread extends Thread {
private InputStream input;
private CameraHandler camH;
private int cameraIndex;
	public ClientReadThread(InputStream is, CameraHandler camH, int cameraIndex) {
		this.input = is;
		this.cameraIndex=cameraIndex;
		this.camH =camH;
	}
	public void run(){
		while(true){
			
		
			int maxToRead= AxisM3006V.IMAGE_BUFFER_SIZE + 10;
		byte[] buffer = new byte[maxToRead];
		int read = 0;
		int result = 0;
		while (read<maxToRead && result!=-1) {
		 try {
			result = input.read(buffer,read,maxToRead-read);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if (result!=-1)
		 read = read+result;
		}
		//bufffer logic
		if(buffer[0] == 1){ // vilkor = är bild
			boolean motionDetected;
			if(buffer[1]==1){
				motionDetected=true;  
				
			}else{
				motionDetected=false;  
				}
			long timestamp =0; //ngra av bytsen i buffer
			int length = buffer[2];
			for (int i = 3; i < 10; i++){
			   timestamp = (timestamp << 8) + (buffer[i] & 0xff);
			}
			byte[] image = Arrays.copyOfRange(buffer, 10, length+10); //ngra av bytsen i buffer
			camH.writeToBuffer(timestamp, motionDetected, image , cameraIndex);
			System.out.println("recieved");
		}
		camH.confirmRead();
	}
	
	}
}
