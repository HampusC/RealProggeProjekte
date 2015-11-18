package client;

import java.util.ArrayList;

public class Client {
	private ArrayList<CameraSocketHandler> cameraSockets;
	private CameraHandler camh;
	public Client (String address, int port, CameraHandler camh){
		this.camh = camh;
		
	}
	public boolean connectCamera(String address, int port){ //tänk på hur disconnect och numrering påverkar
	return cameraSockets.add(new CameraSocketHandler(address,port, camh));
	//timme 
		
	}
}
