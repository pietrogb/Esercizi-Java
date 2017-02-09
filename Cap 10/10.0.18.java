//esercizio 10.0.18

public interface S extends Serializable {
	public void StampaClient() throws RemoteException;
}

public class SI implements S{
	public synchronized void StampaClient() { //synch si potrebbe anche omettere
		for(int i=0; i<10; i++)
					System.out.println("Client");
	}
}

public interface R extends Remote{
	public void stampaServer() throws RemoteException;
	public void stampaRemoto() throws RemoteException;
	public S get() throws RemoteException;
}

public class RI extends UniCastRemoteObjct implements R{
	private S x = new SI();
	public void stampaServer(){
		while(true){
			synchronized(this){
				for(int i=0; i<10; i++)
					System.out.println(i);
			}
		}
	}
	public S get() {return x;}

	public synchronized void stampaRemoto() throws Exception {
		stampaDieci("Remoto");
	}

	public synchronized void stampaDieci(String s) throws Exception {
		System.out.println(s);
	}
}

public class Server{
	public static void main(String[] args) {
		R c = new RI();
		Naming.rebind("pippo", c);
		c.stampaServer();
	}
}

public class Client {
	public static void main(String[] args) throws Exception {
		try{
			R r = (R)Naming.lookup("pippo");
			S s = c.get();
			s.stampaClient();
			r.stampaRemoto();
		} catch(ConnectionException e){
			System.out.println("problemi di connessione al server");
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}
}

/*
Il programma client è concorrente perché possiede oltre all'oggetto serializable anche un riferimeno all'oggetto remoto presente sul server. L'oggetto locale s di tipo serializable gli permette di eseguire le stampe locali, quelle fatte sull'oggetto r vengono eseguite direttamente sul server.
2. Le stampe non interferiscono fra loro per il fatto che si trovano ad essere all'interno di blocchi sincronizzati (sul server), e quelle che vengono stampate dal client stampano solo su di esso per il fatto che - essendo s un oggetto serializable - agisce solo localmente.
*/