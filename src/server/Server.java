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
	
	public Server(int port) throws IOException{
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy("argus-1.student.lth.se", port); //ändra andressen
		serverSocket = new ServerSocket(port);
		
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
			clientSocket = serverSocket.accept();
			sm = new ServerMonitor(rt, wt, serverSocket, clientSocket, camera);
			rt = new ReadThread(sm, clientSocket.getInputStream()); 
			wt = new WriteThread(camera, clientSocket.getOutputStream(), sm);
			rt.start();
			wt.start();
			while (!clientSocket.isClosed()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			disconnect();
			
		}	
	}
	
	public void disconnect() {
		rt.interrupt();
		wt.interrupt();
		try {
			serverSocket.close(); //Ska denna stängas här?
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
