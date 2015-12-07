package server;

import java.net.*; // Provides ServerSocket, Socket
import java.io.*; // Provides InputStream, OutputStream

import se.lth.cs.eda040.proxycamera.*; // Provides AxisM3006V

/**
 * Itsy bitsy teeny weeny web server. Always returns an image, regardless of the
 * requested file name.
 */
public class HTTPServer extends Thread {

	private AxisM3006V myCamera;
	private int myPort; // TCP port for HTTP server
	private static final byte[] CRLF = { 13, 10 };
	// By convention, these bytes are always sent between lines
	// (CR = 13 = carriage return, LF = 10 = line feed)

	// ------------------------------------------------------------ CONSTRUCTOR

	/**
	 * @param port
	 *            The TCP port the server should listen to
	 */
	public HTTPServer(AxisM3006V myCamera, String url, int port) {
		myPort = 1 + port;
		this.myCamera = myCamera;
	}

	/**
	 * This method handles client requests. Runs in an eternal loop that does
	 * the following:
	 * <UL>
	 * <LI>Waits for a client to connect
	 * <LI>Reads a request from that client
	 * <LI>Sends a JPEG image from the camera (if it's a GET request)
	 * <LI>Closes the socket, i.e. disconnects from the client.
	 * </UL>
	 *
	 * Two simple help methods (getLine/putLine) are used to read/write entire
	 * text lines from/to streams. Their implementations follow below.
	 */
	public void run() {
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(myPort);
			System.out.println("HTTP server operating at port " + myPort + ".");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				InputStream is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();

				String request = getLine(is);
				String header;
				boolean cont;
				do {
					header = getLine(is);
					cont = !(header.equals(""));
				} while (cont);

				if (request.substring(0, 4).equals("GET ")) {
					putLine(os, "HTTP/1.0 200 OK");
					putLine(os, "Content-Type: image/jpeg");
					putLine(os, "Pragma: no-cache");
					putLine(os, "Cache-Control: no-cache");
					putLine(os, "");
					int len = myCamera.getJPEG(jpeg, 0);
					os.write(jpeg, 0, len);
				} else {
					// Got some other request. Respond with an error message.
					putLine(os, "HTTP/1.0 501 Method not implemented");
					putLine(os, "Content-Type: text/plain");
					putLine(os, "");
					putLine(os, "No can do. Request '" + request + "' not understood.");

					System.out.println("Unsupported HTTP request!");
				}

				os.flush();
				clientSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a line from InputStream 's', terminated by CRLF. The CRLF is not
	 * included in the returned string.
	 */
	private static String getLine(InputStream s) throws IOException {
		boolean done = false;
		String result = "";

		while (!done) {
			int ch = s.read(); // Read
			if (ch <= 0 || ch == 10) {
				// Something < 0 means end of data (closed socket)
				// ASCII 10 (line feed) means end of line
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}

		return result;
	}

	/**
	 * Send a line on OutputStream 's', terminated by CRLF. The CRLF should not
	 * be included in the string str.
	 */
	private static void putLine(OutputStream s, String str) throws IOException {
		s.write(str.getBytes());
		s.write(CRLF);
	}

	// ----------------------------------------------------- PRIVATE ATTRIBUTES

}