package client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
			
		
		byte[] buffer = new byte[100];
		
		int read = 0;
		int result = 0;
		while (read<100 && result!=-1) {
		 try {
			result = input.read(buffer,read,100-read);
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
			for (int i = 2; i < 10; i++){
			   timestamp = (timestamp << 8) + (buffer[i] & 0xff);
			}
			byte[] image = Arrays.copyOfRange(buffer, 10, buffer.length); //ngra av bytsen i buffer
			camH.writeToBuffer(timestamp, motionDetected, image , cameraIndex);
		}
		camH.confirmRead();
	}
	
	}
}
