// Creazione di un'applicazione distribuita

// definizione interfaccia remota
import java.rmi.*;

public interface Hello extends Remote {
	public String sayHello() throws RemoteException;
}

/*
Ogni interfaccia remota deve estendere l'interfaccia java.rmi.Remote, che non contiene alcun metodo ma è semplicemnte un marcatore. La dichiarazione di ogni metodo inoltre deve indicare il possibile sollevamento (almeno) dell'eccezione RemoteException. Ciò è indispensabile poiché le invocazioni di metodi remoti possono fallire per motivi legati a problemi di comunicazione in rete o problemni del server.
Il file di definizione dell'interfacia remota Hello deve risiedere sia nel client che nel server
*/

// definizione dell'oggetto remoto
import java.rmi.*;
import java.rmi.server.*;
public class HelloImpl() extends UnicastRemoteObject implements Hello {
	public HelloImpl() throws RemoteException { }
	public String sayHello() throws RemoteException{
		return "Ciao Mondo!";
	}

}

/*
La classe HelloImpl definisce l'oggetto remoto implementando l'interfaccia Hello ed estendendo la classe UnicastRemoteObject.
La class UnicastRemoteObject supporta la comunicazione point-to-point usando il protocollo TCP; è comunque possibile estendere una qualsiasi sottoclasse di RemoteObject.
È necessario che la classe contenga un costrutore (anche con corpo vuoto) che può sollevare un'eccezione di tipo RemoteException. È inoltre possible inserire dei metodi che non appartevano all'interfaccia Hello, ma tenendo presente che tali metodi saranno invocabili solo localmente.
*/

// Creazione del server
/*
Oltre a contenere la definizione della classe HelloImpl, il server deve contenere una classe che definisce un metodo main. Il vero e proprio programma server è dunque il seguente:
*/

import java.rmi.*;

public class HelloServer{
	private static final String HOST = "localhost";

	public static void main(String[] args) throws Exception{
		// crea istanza di oggetto remoto
		HelloImpl ref = new HelloImpl();
		// genera un nome con cui pubblicizzare l'oggetto
		String rmiObjName = "rmi ://"+HOST+"/Hello";
		// registra l'oggetto con il suo nome nell'RMI registry
		Naming.rebind(rmiObjName, refmi);
		System.out.println("Server pronto");
	}
}

/*
Questo programma semplicemnte crea un'istanza dell'oggetto remoto e ne registra il riferimento associandogli una stringa identificativa. 
La registrazione nell'RMI registru avviene invocando il metodo statico rebinff della classe java.rmi.Naming, che prende come primo argomento il nome dell'oggetto ermoto come una URL con protocollo rmi. Come default RMI usa la porta 1099 di localhost
*/

// creazione del client

import java.rmi.*;

public class HelloClient{
	private static final String HOST ="localhost";

	public static void main(String[] args) throws Exception{
		try{
			// ottieni istanza d'oggetto remoto(di tipo giusto!!)
			Hello ref = (Hello) Naming.locckup("rmi ://"+HOST+"/Hello");
			// invoca metodo remoto
			System.out.System.out.println("msg ricevuto" + ref.sayHello() );
		} catch(ConnectionException e) {
			System.out.println("Problemi di connessione al server");
		}
		 catch(Exception exc)
		 	exc.printStackTrace();
	}
}

/*
Il programma client interroga il registro RMI utilizzando il metodo statico loockup della classe Naming, il qule restituisce un rigerimento di tipo Remote all'oggetto cercato.
Per poter invocare il metodo remoto SayHello è dunque prima necessario un doewncast al tipo Hello (non a HelloImpl!).
*/

// Creazione ed avvio
/*
Una volta definite le classi tutti i file vanno compliati e vanno avviati, in sequenza, il registro RMI con il comando rmiregistry, il programmma server con il comando HelloServer ed il programma client con il momando java HelloClient.
Mentre il programma client termina dopo aver invoato il metodo remoto, il programma server ed il registro continueranno ad eseguire indefiitamente e vanno chiusi a mano con Ctrl+C
*/