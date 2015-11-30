package client;

import java.util.ArrayList;

public class Client {
	public final static int IDLE_MODE = 0;
	public final static int MOVIE_MODE = 1;
	public final static int SYNCHRONOUS_MODE = 0;
	public final static int ASYNCHRONOUS_MODE = 1;
	public final static long MAX_DIFF = 200;
	public final static int AUTO_MODE = 1;
	private int auto = AUTO_MODE;
	private int activeSyncMode = SYNCHRONOUS_MODE; //static ofinal, använda getter?
	
	
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
	public void setMode(int mode){
		for(CameraSocketHandler s:cameraSockets) {
			s.setMode(mode);
		}
	}
	public boolean isAutoMode(){
		return auto==AUTO_MODE;
	}
	public boolean isSyncMode(){
		return activeSyncMode == SYNCHRONOUS_MODE;
	}
	public void setSyncType(int syncType){
		activeSyncMode = syncType;
	}
	
	public void setAutoMode(int mode){ 
		//Systemet ska gå in i auto mode, alltså att den byter mellan idle/movie och sync/async automatiskt 
		auto = mode;
	}
	
	
}
