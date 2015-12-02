package server;

import java.io.IOException;
import java.io.InputStream;

public class ReadThread extends Thread {

	private InputStream is;
	private ServerMonitor sm;

	public ReadThread(ServerMonitor sm, InputStream is) {
		this.is = is;
		this.sm = sm;
	}

	public void run() {
		System.out.println("read thread virginity");
		try {
			while (!isInterrupted()) {

				int temp = is.read();
				if (temp == 1) { // Ska förmodligen kolla efter något annat
					sm.requestRecieved();
				} else{
					System.out.println("not 1 in read thread server");
					break;
				}
			}
			sm.requestRecieved();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}