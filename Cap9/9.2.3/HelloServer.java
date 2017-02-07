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
import java.rmi.server.*;
class HelloImpl extends UnicastRemoteObject implements Hello {
	public HelloImpl() throws RemoteException { }
	public String sayHello() throws RemoteException{
		return "Ciao Mondo!";
	}

}



public class HelloServer{
	private static final String HOST = "localhost";

	public static void main(String[] args) throws Exception{
		// crea istanza di oggetto remoto
		HelloImpl ref = new HelloImpl();
		// genera un nome con cui pubblicizzare l'oggetto
		String rmiObjName = "rmi ://"+HOST+"/Hello";
		// registra l'oggetto con il suo nome nell'RMI registry
		Naming.rebind(rmiObjName, ref);
		System.out.println("Server pronto");
	}
}