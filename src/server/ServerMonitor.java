package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.proxycamera.AxisM3006V;

public class ServerMonitor {
	private ReadThread rt;
	private WriteThread wt;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AxisM3006V camera;
	private boolean requestRecieved;
	private boolean closeRequested;
	private boolean closed;
	
	public ServerMonitor(ReadThread rt, WriteThread wt, ServerSocket ss, Socket s, AxisM3006V camera) throws IOException{
		this.serverSocket = ss;
		this.clientSocket = s;
		this.camera = camera;
		this.rt = rt;
		this.wt = wt;
		requestRecieved=false;
		closeRequested=false;
		closed = false;
	}
	

	/**
	 * Confirm that the message from the client was a request and sends back an image.
	 */
	public synchronized void requestRecieved(){
		
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


	public synchronized void requestClose(boolean closeRequested) {
	this.closeRequested=closeRequested;
	notifyAll();
		
		
	}


	public synchronized boolean closedRequested() {
		// TODO Auto-generated method stub
		return closeRequested;
	}


	public synchronized void closeConfirmed() {
		closed = true;
		notifyAll();
		System.out.println("server monitor: closed = true");
		
	}


	public synchronized void streamClosed() {
		while(!closed){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
