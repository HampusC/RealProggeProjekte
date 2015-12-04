package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// anv: rt och lösenord: sigge  (anslut via ssh, ex "ssh rt@argus-1.student.lth.se " )
public class CameraSocketHandler {
	private Socket socket;
	private ClientWriteThread cWriteThread;
	private ClientReadThread cReadThread;
	private ClientMonitor monitor;
	private int cameraIndex;

	/**
	 * Creates a CameraSocketHandler
	 * 
	 * @param camNbr
	 *            - the index of the camera
	 * @param address
	 *            -the web-address of the server
	 * @param port
	 *            - the port number
	 * @param monitor
	 *            - the client monitor
	 * @throws UnknownHostException
	 *             - thrown when connection can't be established
	 * @throws IOException
	 *             - thrown when connection can't be established
	 */
	public CameraSocketHandler(int camNbr, String address, int port, ClientMonitor monitor)
			throws UnknownHostException, IOException {
		this.monitor = monitor;
		cameraIndex = camNbr;
		socket = new Socket(address, port);
		socket.setTcpNoDelay(true);
		cWriteThread = new ClientWriteThread(socket.getOutputStream(), monitor, cameraIndex);
		cReadThread = new ClientReadThread(socket.getInputStream(), monitor, cameraIndex);
		cWriteThread.start();
		cReadThread.start();

	}

	/**
	 * Disconnects this socket-connection
	 */
	public void disconnect() {
		cWriteThread.interrupt();
		cReadThread.interrupt();

		try {
			socket.close();
			System.out.println("socket was closed - client side");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
