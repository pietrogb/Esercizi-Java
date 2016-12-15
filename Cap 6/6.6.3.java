public class ServerTimeOutExcept extends Exception {
	private int port;

	public ServerTimeOutExcept(String reason, int port) {
		super(reason); //costruttore di Exception
		this.port = port;
	}
	public String getReason() { //Metodo di classe Throwable
		return this.getMessage();
	}
	public int getPort() {
		return port;
	}
}

public class Connessione {
	public static int open(String s, int porta) {
		//...
	}

	public static void connect(String serverName) throws ServerTimeOutExcept {
		int porta = 80, successo = open(serverName, porta);
		if(successo == -1) 
			throw new ServerTimeOutExcept("Could not connect", porta);
	}

	public static void findServer() {
		try {
			connect(defaultServer);
		}
		catch (ServerTimeOutExcept e) {
			System.out.println("Server time-out: try another server");
			try{
				connect(alternativeServer);
			}
			catch (ServerTimeOutExcept e1) {
				System.out.println("error: " + el.getReason() + "connecting to port " + el.getPort());
			}
		}
	}
}