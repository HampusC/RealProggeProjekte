package server;

import java.io.IOException;
import java.io.OutputStream;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class WriteThread extends Thread {
	
	private OutputStream os;
	private Server server; //Behövs nog inte
	
	public WriteThread(Server server, OutputStream os){
		this.os = os;
		this.server = server;
	}
	
	public void run(){
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
		try {
			os.write(jpeg);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
