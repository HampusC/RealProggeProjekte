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
	
	public ServerMonitor(ReadThread rt, WriteThread wt, ServerSocket ss, Socket s, AxisM3006V camera) throws IOException{
		this.serverSocket = ss;
		this.clientSocket = s;
		this.camera = camera;
		this.rt = rt;
		this.wt = wt;
	}
	
	/**
	 * Waits for a message from the client and then starts the ReadThread.
	 */
	public synchronized void execute() throws IOException{
		while(true) {
			clientSocket = serverSocket.accept();
			rt.start();
		}
			
	}
	
	/**
	 * Confirm that the message from the client was a request and sends back an image.
	 */
	public synchronized void requestRecieved(){
		if (!camera.connect()) { //bugletande
			System.out.println("Failed to connect to camera!");
		}
		wt.start();
	}
	
}
