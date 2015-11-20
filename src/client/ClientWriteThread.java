package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientWriteThread extends Thread{
	private OutputStream output;
	private CameraHandler camH;

	public ClientWriteThread(OutputStream os, CameraHandler camH) {
		this.output=os;
		this.camH = camH;
	}
	public void run(){
		while(true){
			camH.request();
		
		try {
			output.write(1);
			//flush
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}

}
