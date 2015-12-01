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
		System.out.println("read thread virginity");
		while(!isInterrupted()) {
			try {
				int temp = is.read();
				if (temp == 1) { // Ska förmodligen kolla efter något annat
					sm.requestRecieved();
				}
				else if(temp == 0){
					System.out.println("OS wrote recieved");   //Kanske ha detta?
					sm.requestClose(true);
					this.interrupt();
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}	
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
