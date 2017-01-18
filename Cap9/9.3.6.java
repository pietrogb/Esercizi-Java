interface R extends Remote{
	public void m() throws RemoteException ;
	public void n() throws RemoteException ;
}

class RImpl extends UnicastRemoteObject implements R{
	boolean condizione=false;
	RImpl() throws RemoteException{}

	public void m() throws RemoteException {
		try{
			synchronized(this){
				while (condizione==false) {
					wait();
				} catch (Exception e) {}
			}
		}
	}

	public void n() throws RemoteException {
		try{
			synchronized(this){
				condizione=true;
				notify();
			}
		} catch (Exception e) {}
	}
}

/*Si consideri inoltre un'applicazione distribuita su due host, host1 ed host2, su cui dirano i seguenti programmi*/

// PROGRAMMA CHE ESEGUE NELL'HOST1
public static void main(String[] args) {
	R r= new RImpl();
	Naming.rebind("pippo", r);
}
// PROGRAMMA CHE ESEGUE NELL'HOST2
public static void main(String[] args) {
	R r= (R)Naming.lookup("pippo");
	r.m();
	r.n();
}

/*
Descrivere il comporamento dell'applicazione precendente.
	~Host1 crea un nuovo oggetto remoto e lo pubblica, Host2 salva un riferimento all'oggetto remoto ed invoca su quest'ultimo il metodo m. L'oggetto remoto per come è ora riceve la chiamata del metodo m() che lo sospende

Modificare l'applicazioni in modo che il thread sospeso con l'istruzione wait venga risvegliato da un'opportuna chiamata del metodo n. Giustificare la risposta.
*/