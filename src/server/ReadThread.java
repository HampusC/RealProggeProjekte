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
		try {
			while (!isInterrupted()) {

				int temp = is.read();
				if (temp == 1) {
					sm.requestRecieved();
				} else {
					break;
				}
			}
			sm.requestRecieved();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}