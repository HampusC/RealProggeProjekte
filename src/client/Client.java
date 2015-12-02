package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	private ArrayList<CameraSocketHandler> cameraSockets;
	private CameraHandler camh;

	public Client(String address, int port, CameraHandler camh) {
		this.camh = camh;
		cameraSockets = new ArrayList<CameraSocketHandler>(2);
		cameraSockets.add(null);
		cameraSockets.add(null);
		//connectCamera(0,address, port);
		
	}

	public void connectCamera(int camIndex, String address, int port) throws Exception { // tänk på hur
																// disconnect
			if(cameraSockets.get(camIndex) ==null){												// och numrering
		System.out.println("connect " + address + "  " +  port);
		try{
		cameraSockets.set(camIndex, new CameraSocketHandler(camIndex,address, port, camh));
			//camh.onlyOneCamera(cameraSockets.size()<2);
		// påverkar
		 //change to void
		// timme
		}catch(IOException e){
			throw new Exception("Error: Could not connect to the server! Check the address and port again!");
		}
			}else{
				throw new Exception("Error: This window is already connected to a camera! Disconnect first.");
				
			}

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
	public boolean disconnect( int index){
	
		CameraSocketHandler temp =cameraSockets.get(index);		
		if(temp != null){
		temp.disconnect();
		cameraSockets.set(index, null);
		camh.flushBuffert(index);
		return true;
		}
		
		return false;
		
		
	}

	
//	public void setAutoMode(int mode){ 
//		//Systemet ska gå in i auto mode, alltså att den byter mellan idle/movie och sync/async automatiskt 
//		System.out.println("mode = " + mode +" were auto = 1 and manual =0");
//		auto = mode;
//	}
	
	
}
