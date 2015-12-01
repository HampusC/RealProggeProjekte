package server;

import java.io.IOException;
import java.io.InputStream;

public class ReadThread extends Thread {
	
	private InputStream is;
	private ServerMonitor sm;
	
	public ReadThread(ServerMonitor sm, InputStream is){
		this.is = is;
		this.sm = sm;
	}
	
	public void run(){
		while(true) {
			try {
				int temp = is.read();
				if (temp == 1) { // Ska förmodligen kolla efter något annat
					sm.requestRecieved();
				}
//				else if(temp == 2){
//					System.out.println("OS wrote recieved");   //Kanske ha detta?
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
