// esempio pag. 134

import java.util.concurrent.*;

class WebServer {
	private static final int NTHR = 100;
	private static final Executor exec = Executor.newFixedThreadPool(NTHR);
	// newSingleThreadExecutor opp. newCachedThreadPool

	private void handleRequest(Socket client) {
		// ... gestisce un cliente
	}

	public static void main(String[] args) {
		ServerSocket socket = new ServerSocket(80);
		while(true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable() {
				public void run() {
					handleRequest(connection);
				}
			}
		}
	}
}