package client;

import java.io.OutputStream;

public class ClientWriteThread extends Thread{
	private OutputStream os;

	public ClientWriteThread(OutputStream os) {
		this.os=os;
	}
	public void run(){
		
	}

}
