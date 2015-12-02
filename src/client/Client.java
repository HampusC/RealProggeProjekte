package client;

import java.util.ArrayList;

public class Client {
	public final static int IDLE_MODE = 0;
	public final static int MOVIE_MODE = 1;
	public final static int SYNCHRONOUS_MODE = 0;
	public final static int ASYNCHRONOUS_MODE = 1;
	public final static long MAX_DIFF = 200;
	public final static int AUTO_MODE = 1;
	public static final int MANUAL_MODE = 0;
	public static int auto = AUTO_MODE; //ändra .... flytta till camhandler?
	private int activeSyncMode = SYNCHRONOUS_MODE; //static ofinal, använda getter?
	
	
	private ArrayList<CameraSocketHandler> cameraSockets;
	private CameraHandler camh;

	public Client(String address, int port, CameraHandler camh) {
		this.camh = camh;
		cameraSockets = new ArrayList<CameraSocketHandler>(2);
		cameraSockets.add(null);
		cameraSockets.add(null);
		//connectCamera(0,address, port);
		
	}

	public void connectCamera(int camIndex, String address, int port) { // tänk på hur
																// disconnect
																// och numrering
		System.out.println("connect " + address + "  " +  port);
		cameraSockets.set(camIndex, new CameraSocketHandler(camIndex,address, port, camh));
			//camh.onlyOneCamera(cameraSockets.size()<2);
		// påverkar
		 //change to void
		// timme

	}

	public static void main(String[] args) {
		CameraHandler camH = new CameraHandler();
		
		if (args.length != 4) {
			System.out.println("Syntax: JPEGHTTPClient <address> <port>");
			System.exit(1);
		}
		Client c = new Client(args[0], Integer.parseInt(args[1]), camH);
		//c.connectCamera(1,args[2], Integer.parseInt(args[3]));
//		c.setMode(Client.IDLE_MODE, 0);
//		c.setMode(Client.IDLE_MODE, 1);
		ViewThread viewThread = new ViewThread(camH, c);
		viewThread.start();
	}
	public void disconnect( int index){
		camh.onlyOneCamera(true);
		CameraSocketHandler temp =cameraSockets.get(index);		
		temp.disconnect();
		
		
		
	}
	public void setMode(int mode){
		if(mode == IDLE_MODE){
			camh.setIdle(true);
		}else if( mode == MOVIE_MODE){
			camh.setIdle(false);
		}
		
	}
	public boolean isAutoMode(){
		return auto==AUTO_MODE;
	}
	public boolean isSynced(){
		return camh.isSyncMode();
	}
	public void setSyncMode(boolean syncMode){
	camh.setSyncMode(syncMode);
	}
	
	public void setAutoMode(int mode){ 
		//Systemet ska gå in i auto mode, alltså att den byter mellan idle/movie och sync/async automatiskt 
		System.out.println("mode = " + mode +" were auto = 1 and manual =0");
		auto = mode;
	}
	
	
}
