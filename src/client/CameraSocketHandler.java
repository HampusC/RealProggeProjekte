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
	
		try {
			socket = new Socket(address,  port);
			cWriteThread = new ClientWriteThread(socket.getOutputStream(), camH);
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
		try {
	
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cWriteThread.interrupt();
		cReadThread.interrupt();
		
		
	}
	public void setMode(int mode) {
		if(mode == Client.IDLE_MODE)
			cWriteThread.idleMode(true);
		if(mode == Client.MOVIE_MODE)
			cWriteThread.idleMode(false);
		
	}
}
