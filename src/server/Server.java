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
	private int port;
	
	public Server(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy("argus-1.student.lth.se", port); //ändra andressen
		this.port = port;
		
	}
	
	public static void main(String[] args){
		try {
			Server s = new Server(Integer.parseInt(args[0]));
			s.execute();
		} catch(IOException e) {
			System.out.println("Error!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Waits for a message from the client and then starts the ReadThread.
	 */
	public void execute() throws IOException{
		
		while(true){
			System.out.println("server waiting for connection");
			clientSocket = serverSocket.accept();
			System.out.println("server: socket accepted");
			sm = new ServerMonitor(rt, wt, serverSocket, clientSocket, camera);
			rt = new ReadThread(sm, clientSocket.getInputStream()); 
			wt = new WriteThread(camera, clientSocket.getOutputStream(), sm);
			rt.start();
			wt.start();
			System.out.println("Server: threads started");
			disconnect();
		}	
	}
	
	public void disconnect() {
		sm.streamClosed();
		rt.interrupt();
		wt.interrupt();
		try {
			clientSocket.close(); //Ska denna stängas här?
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		camera.destroy();
	}
	

	public void closeSocket() throws IOException{
		clientSocket.close();
	}
	
	
	
	
	
	

}
