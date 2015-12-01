package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
// anv: rt och lösenord: sigge  (anslut via ssh, ex "ssh rt@argus-1.student.lth.se " )
public class CameraSocketHandler {
private Socket socket;
private ClientWriteThread cWriteThread;
private ClientReadThread cReadThread;
private CameraHandler camH; //monjuitor med bildbuffertar

private int cameraIndex;
	public CameraSocketHandler(String address, int port, CameraHandler camH){
		this.camH = camH;
		cameraIndex=camH.cameraIndex();
		System.out.println("camindex = " + cameraIndex);
	
		try {
			socket = new Socket(address,  port);
			socket.setTcpNoDelay(true);
			cWriteThread = new ClientWriteThread(socket.getOutputStream(), camH, cameraIndex);
			cReadThread = new ClientReadThread(socket.getInputStream(), camH, cameraIndex);
			cWriteThread.start();
			cReadThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
	public void disconnect() {
		cWriteThread.interrupt();
		cReadThread.interrupt();
		try {
			socket.close();
			System.out.println("socket was closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
