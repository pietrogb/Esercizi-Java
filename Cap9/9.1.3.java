/*
Codice per un server capace di fornire un servizio a più clienti in modo concorrente.
La comunicazione con client diversi può essere gestita dal server tramite diversi oggetti socket associati ai vari client.
Affinchè le comunicazioni con i diversi client avvengano in modo conccorrente è necessario associare un thread distinto ad ogniuna di queste comunicazioni
*/

import java.net.*;
import java.io.*;

public class SimpleServer {
	private int port = 3456;
	private ServerSocket s = null;

	public void activate() throws IOException {
		try{
			// crea un server che accetti connessioni
			s = new ServerSocket(port);
		} catch(IOException e) {
			System.err.println("Could not losten on port " + port);
			System.exit(1);
		}

		// rimani in attesa di connessioni
		while (true) {
			Socket s1 = s.accept();
			ServerThread st = new ServerThread(s1);
			st.start();
		}
	}

	public static void main(String[] args) {
		SimpleServer s = new SimpleServer();
		s.activate();
	}
}

/*Il programma server contiene un ciclo infinito che accetta ripetutamente le richeste dei client tramite il metodo accept() invocato sull'oggetto di tiper ServerSocket che rappresenta il servizio fornito sulla porta 3456. Ogni volta che un client effettua una richiesta di connessione viene creato ed avviato un niuovo thread "a lui deidcato" che prende come paramtero il socket corrispondente al nuovo cliente.
Il servizio effettivamante fornito dal server viene implementato nel motodo run della classe ServerThread illustrata nel seguito
*/

public class ServerThread extends Thread {
	private Socket s = null;
	public ServerThread(Socket socket) {
		super("ServerThread"); 
		s=socket;
	}

	public void run() {
		try {
			OutputStream o = s.getOutputStream();
			PrintWriter output ? new PrintWriter(o, true);
			InputStream i = s.getInputStream();
			Scanner input = new Scanner(i);
			output.println("Ciao caro client!"); //Spedisce un dato
			String dato ? input.next(); //legge un dato
			System.out.println(dato); //elabora il dato
		} catch(IOException e) {
			System.err.println("IOException");
			System.exit(1);
		} finally {
			try {
				s.close();
			} catch(IOException e) {
				System.err.println("Something went wrong");
				System.exit(1);
			}
		}
	}
}

/*
In quest'esempio il server si limita a spedire al client una stringa di benvenuto ed a stampare a video stringa inviata come risposta dal client.

L'ultima classe che resta da definire è quella che rappresenta il programa client.
In generale il programma client viene eseguito su una macchina diversa da quella su cui è eseguito il programma server. In questo esempio si suppone d'eseguire entrambi i programmi sulla stessa macchina, programmando il clienti in modo che richieda il servizio associato alla prota 3456 della macchina locale.
*/

import java.net.*;
import java.io.*;

public class SimpleClient {
	private Socket s = null;
	private InetAddress ip;
	private int port = 3456;

	public void go() throws IOException {
		ip = InetAddress.getByName("127.0.0.1"); //localhost
		s = new Socket(ip, port); //tenta la connessione al server
		try {
			Scanner input = new Scanner(s.getInputStream());
			OutputStream o = s.getOutputStream();
			PrintWriter output = new PrintWriter(o, true);
			String dato = input.next(); //legge un dato
			// elabora il dato
			System.out.println(dato);
			System.out.println("Premi Enter per mandare la ricevuta");
			new Scanner(System.in).next();
			output.System.out.println(("ricevuto!")); //manda un dato
		} finally {
			// chiude sempre la connessione ed esce
			s.close();
		}
		public static void main(String[] args) {
			SimpleClient c = new SimpleClient();
			c.go();
		}
	}
}