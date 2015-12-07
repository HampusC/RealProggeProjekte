package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.realcamera.AxisM3006V;

public class Server {
	private ReadThread rt;
	private WriteThread wt;
	private ServerMonitor sm;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AxisM3006V camera;

	public Server(String url, int port) throws IOException {
		serverSocket = new ServerSocket(port);
		camera = new AxisM3006V();
		camera.init();
		camera.setProxy(url, port);
		HTTPServer https = new HTTPServer(camera, url, port);
		https.start();
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("The server needs two arguments; server address and port number!");
		} else {
			try {
				Server s = new Server(args[0], Integer.parseInt(args[1]));
				s.execute();
			} catch (IOException e) {
				System.out.println("Error!");
				e.printStackTrace();

			}
		}
	}

	/**
	 * Waits for a message from the client and then starts the ReadThread.
	 */
	public void execute() throws IOException {

		while (true) {
			System.out.println("Server waiting for connection");
			clientSocket = serverSocket.accept();
			camera.connect();
			sm = new ServerMonitor();
			rt = new ReadThread(sm, clientSocket.getInputStream());
			wt = new WriteThread(camera, clientSocket.getOutputStream(), sm);
			rt.start();
			wt.start();
			try {
				rt.join();
				wt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
