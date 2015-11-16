package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
// anv: rt och lösenord: sigge  (anslut via ssh, ex "ssh rt@argus-1.student.lth.se " )
public class CameraSocketHandler {
private Socket socket;
private ClientWriteThread cWriteThread;
private ClientReadThread cReadThread;
	public CameraSocketHandler(String address, int port){
		try {
			socket = new Socket(address,  port);
			cWriteThread = new ClientWriteThread(socket.getOutputStream());
			cReadThread = new ClientReadThread(socket.getInputStream());
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
}
