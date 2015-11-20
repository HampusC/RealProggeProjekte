package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.fakecamera.AxisM3006V;

public class Server {
	private ReadThread rt;
	private WriteThread wt;
	private ServerMonitor sm;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AxisM3006V camera;
	
	public Server() throws IOException{
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy("argus-1.student.lth.se", 20541); //ändra andressen
		serverSocket = new ServerSocket(20541);
		rt = new ReadThread(sm, clientSocket.getInputStream()); // request 1
		wt = new WriteThread(camera, clientSocket.getOutputStream());
		sm = new ServerMonitor(rt, wt, serverSocket, clientSocket, camera);
	}
	
	public static void main(String[] args){
		try {
			Server s = new Server();
			s.sm.execute();
		} catch(IOException e) {
			System.out.println("Error!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public synchronized void destroy() {
		camera.destroy();
	}
	

	public synchronized void closeSocket() throws IOException{
		clientSocket.close();
	}
	
	
	
	
	
	

}
