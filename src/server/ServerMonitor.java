package server;

public class ServerMonitor {
	private boolean requestRecieved;

	/**
	 * Creates a monitor
	 */
	public ServerMonitor() {
		requestRecieved = false;
	}

	/**
	 * Confirm that the message from the client was a request and sends back an
	 * image.
	 */
	public synchronized void requestRecieved() {
		requestRecieved = true;
		notifyAll();
	}

	/**
	 * A blocking method that blocks until a request to send a new picture has
	 * been recievied.
	 */
	public synchronized void shouldSend() {
		while (!requestRecieved) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		requestRecieved = false;
		notifyAll();
	}
}
