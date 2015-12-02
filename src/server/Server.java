package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.proxycamera.AxisM3006V;


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
		camera.setProxy("argus-7.student.lth.se", 5050); //ändra andressen argus-1.student.lth.se
		
		this.port = port;
		
	}
	public Server(String url ,int port) throws IOException{
		serverSocket = new ServerSocket(port);
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy(url, 5050); //ändra andressen argus-1.student.lth.se
		
		this.port = port;
		
	}
	
	public static void main(String[] args){
		try {
			Server s = new Server(args[0],Integer.parseInt(args[1]));
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
			camera.connect();
			System.out.println("server: socket accepted");
			sm = new ServerMonitor(rt, wt, serverSocket, clientSocket, camera);
			rt = new ReadThread(sm, clientSocket.getInputStream()); 
			wt = new WriteThread(camera, clientSocket.getOutputStream(), sm);
			rt.start(); // join så att samma 
			wt.start();
			try {
				rt.join();
				System.out.println("joined after rt");
				wt.join();
				System.out.println("joined after wt");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
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
