package client;

import java.util.ArrayList;

public class Client {
	public final static int IDLE_MODE = 0;
	public final static int MOVIE_MODE = 1;
	private ArrayList<CameraSocketHandler> cameraSockets;
	private CameraHandler camh;

	public Client(String address, int port, CameraHandler camh) {
		this.camh = camh;
		cameraSockets = new ArrayList<CameraSocketHandler>();
		connectCamera(address, port);
		
	}

	public boolean connectCamera(String address, int port) { // tänk på hur
																// disconnect
																// och numrering
		System.out.println("connect " + cameraSockets);														// påverkar
		return cameraSockets.add(new CameraSocketHandler(address, port, camh));
		// timme

	}

	public static void main(String[] args) {
		CameraHandler camH = new CameraHandler();
		
		if (args.length != 4) {
			System.out.println("Syntax: JPEGHTTPClient <address> <port>");
			System.exit(1);
		}
		Client c = new Client(args[0], Integer.parseInt(args[1]), camH);
		c.connectCamera(args[2], Integer.parseInt(args[3]));
//		c.setMode(Client.IDLE_MODE, 0);
//		c.setMode(Client.IDLE_MODE, 1);
		ViewThread viewThread = new ViewThread(camH, c);
		viewThread.start();
	}
	public void disconnect( int index){
		CameraSocketHandler temp =cameraSockets.get(index);		
		temp.disconnect();
		cameraSockets.remove(index);
	}
	public void setMode(int mode, int index){
		cameraSockets.get(index).setMode(mode);
	}
}
