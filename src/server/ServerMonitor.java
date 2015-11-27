package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class ServerMonitor {
	private ReadThread rt;
	private WriteThread wt;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AxisM3006V camera;
	private boolean requestRecieved;
	
	public ServerMonitor(ReadThread rt, WriteThread wt, ServerSocket ss, Socket s, AxisM3006V camera) throws IOException{
		this.serverSocket = ss;
		this.clientSocket = s;
		this.camera = camera;
		this.rt = rt;
		this.wt = wt;
		requestRecieved=false;
	}
	

	/**
	 * Confirm that the message from the client was a request and sends back an image.
	 */
	public synchronized void requestRecieved(){
		if (!camera.connect()) { //bugletande
			System.out.println("Failed to connect to camera!");
		}
		requestRecieved=true;
		notifyAll();
//		wt.start();
	}


	public synchronized boolean shouldSend() {
		while(!requestRecieved){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		requestRecieved=false;
		notifyAll();
		return true;
	}
	
	public synchronized void waitForDisconnect(){
		while (!clientSocket.isClosed()){
			try {
			wait(); //kanske måste cvara wait(1000)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
