package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ReadThread rt;
	private WriteThread wt;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public Server(){
		try {
			serverSocket = new ServerSocket(80);
			execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void execute(){
		while(true) {
			try {
				clientSocket = serverSocket.accept();
				rt = new ReadThread(this, clientSocket.getInputStream()); // request 1
				rt.start();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	public void requestRecieved(){
		try {
			wt = new WriteThread(this, clientSocket.getOutputStream());
			wt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
