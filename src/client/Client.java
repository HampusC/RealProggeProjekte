package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	private CameraSocketHandler[] cameraSockets;
	private ClientMonitor monitor;

	/**
	 * Creates a client
	 * 
	 * @param monitor
	 *            - the client monitor
	 */
	public Client(ClientMonitor monitor) {
		this.monitor = monitor;
		cameraSockets = new CameraSocketHandler[2];
	
	}

	/**
	 * 
	 * @param camIndex
	 *            - the index of the camera
	 * @param address
	 *            - the address of the camera
	 * @param port
	 *            - the port number of the camera
	 * @throws Exception
	 *             - thrown when an connection couldn't be handled
	 */
	public void connectCamera(int camIndex, String address, int port) throws Exception {
		if (cameraSockets[camIndex] == null) {
			try {
				cameraSockets[camIndex] = new CameraSocketHandler(camIndex, address, port, monitor);
			} catch (IOException e) {
				throw new Exception("Error: Could not connect to the server! Check the address and port again!");
			}
		} else {
			throw new Exception("Error: This window is already connected to a camera! Disconnect first.");

		}

	}

	public static void main(String[] args) {
		ClientMonitor camH = new ClientMonitor();
		Client c = new Client(camH);
		ViewThread viewThread = new ViewThread(camH, c);
		viewThread.start();
	}

	/**
	 * Disconnect the camera with a given camera index
	 * 
	 * @param index
	 *            - the index of the camera to disconnect
	 * @return true if the camera successfully got disconnected, false otherwise
	 */
	public boolean disconnect(int index) {

		CameraSocketHandler temp = cameraSockets[index];
		if (temp != null) {
			temp.disconnect();
			cameraSockets[index]= null;
			monitor.flushBuffert(index);
			return true;
		}

		return false;

	}

}
