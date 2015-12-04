package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// anv: rt och lösenord: sigge  (anslut via ssh, ex "ssh rt@argus-1.student.lth.se " )
public class CameraSocketHandler {
	private Socket socket;
	private ClientWriteThread cWriteThread;
	private ClientReadThread cReadThread;
	private CameraHandler camH;
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
	 * @param camH
	 *            - the monitor
	 * @throws UnknownHostException
	 *             - thrown when connection can't be established
	 * @throws IOException
	 *             - thrown when connection can't be established
	 */
	public CameraSocketHandler(int camNbr, String address, int port, CameraHandler camH)
			throws UnknownHostException, IOException {
		this.camH = camH;
		cameraIndex = camNbr;
		System.out.println("camindex = " + cameraIndex);

		System.out.println("camSockHandl: beföre socket");
		socket = new Socket(address, port);
		System.out.println("camSockHandl: after socket");
		socket.setTcpNoDelay(true);
		cWriteThread = new ClientWriteThread(socket.getOutputStream(), camH, cameraIndex);
		cReadThread = new ClientReadThread(socket.getInputStream(), camH, cameraIndex);
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
