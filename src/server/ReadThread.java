package server;

import java.io.IOException;
import java.io.InputStream;

public class ReadThread extends Thread {
	
	private InputStream is;
	private Server server;
	
	public ReadThread(Server server, InputStream is){
		this.is = is;
		this.server = server;
	}
	
	public void run(){
		try {
			int temp = is.read();
			if(temp == 1) {     //Ska förmodligen kolla efter något annat
				server.requestRecieved();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
