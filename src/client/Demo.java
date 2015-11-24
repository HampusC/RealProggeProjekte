package client;

import client.Client;

import server.Server;

public class Demo {
	public static void main(String[] args) {
		Server1 s1 = new Server1();
		s1.start();
		Server2 s2 = new Server2();
		s2.start();
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Client1 c = new Client1();
		c.start();
	
	
	}
	
	private static class Server1 extends Thread {
		public void run() {
			Server.main(new String[] {"6077"});
		}
	}
	
	private static class Server2 extends Thread {
		public void run() {
			Server.main(new String[] {"6078"});
		}
	}
	
	private static class Client1 extends Thread {
		public void run() {
			Client.main(new String[] {"localhost", "6077", "localhost", "6078"});
		}
	}

}
