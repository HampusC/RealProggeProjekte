package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class Server {
	private AxisM3006V camera;
	private ReadThread rt;
	private WriteThread wt;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public Server() throws IOException{
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy("argus-1.student.lth.se", 80); //ändra andressen
		serverSocket = new ServerSocket(80);
	}
	
	public static void main(String[] args){
		try {
			Server s = new Server();
			s.execute();
		} catch(IOException e) {
			System.out.println("Error!");
			System.exit(1);
		}
	}
	
	/**
	 * Waits for a message from the client and then starts the ReadThread.
	 */
	public void execute() throws IOException{
		while(true) {
			clientSocket = serverSocket.accept();
			rt = new ReadThread(this, clientSocket.getInputStream()); // request 1
			rt.start();
			//clientSocket.close(); skall flyttas
		}
			
	}
	
	/**
	 * Confirm that the message from the client was a request and sends back an image.
	 */
	public void requestRecieved(){
		try {
			if (!camera.connect()) {
				System.out.println("Failed to connect to camera!");
			}
			wt = new WriteThread(camera, clientSocket.getOutputStream());
			wt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
