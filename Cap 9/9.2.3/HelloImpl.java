
/*
Ogni interfaccia remota deve estendere l'interfaccia java.rmi.Remote, che non contiene alcun metodo ma è semplicemnte un amrcatore. La dichiarazione di ogni metodo inoltre deve indicare il possibile sollevamento (almeno) dell'eccezione RemoteException. Ciò è indispensabile poiché le invocazioni di metodi remoti possono fallire per motivi legati a problemi di comunicazione in rete o problemni del server.
Il file di definizione dell'interfacia remota Hello deve risiedere sia nel client che nel server
*/

// definizione dell'oggetto remoto
import java.rmi.*;
import java.rmi.server.*;
public class HelloImpl extends UnicastRemoteObject implements Hello {
	public HelloImpl() throws RemoteException { }
	public String sayHello() throws RemoteException{
		return "Ciao Mondo!";
	}

}