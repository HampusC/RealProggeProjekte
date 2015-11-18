package client;

import java.io.IOException;
import java.io.InputStream;

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
		if(true){ // vilkor = är bild
			long timestamp =0; //ngra av bytsen i buffer
			boolean motionDetected=false;  //ngra av bytsen i buffer
			byte[] image = buffer; //ngra av bytsen i buffer
			camH.writeToBuffer(timestamp, motionDetected, image , cameraIndex);
		}
		camH.confirmRead();
	}
	
	}
}
