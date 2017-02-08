// le seguenti classi rappresentano tipi cndivisi da un programma client ed un programma server

public interface I extends Serializable {
	String get();
	void set(String n);
	void stampa();
}

public class IImpl implements I {
	private String s;
	IImpl(String n) {s=n;}
	public String get() {return s;}
	public void set(String n) {s=n;}
	public void stampa() {System.out.println(s);}
}

public interface C extends Remote {
	I get() throws RemoteException;
	void set(I i) throws RemoteException;
	C g() throws RemoteException;
	void k() throws RemoteException;
	void stampa() throws RemoteException;
}

// si consideri inoltre la seguente definizione di classe che implementa un ogetto remoto di tipo C

public class CImpl extends UnicatRemoteException implements C {
	private I x = new IImpl("Giallo");
	CImpl() throws RemoteException {}

	public synchronized I get() throws RemoteException {return x;}

	public synchronized void set(I i) throws RemoteException{
		x.set(i.get());
	}

	public synchronized C g() throws RemoteException{
		x.set("Rosso");
		return this;
	}

	public void k() {x.set("Verde");}

	public synchronized void stampa() {System.out.println();}
}

// client
public class RemotoClient {
	public static void main(String[] args) throws Exception{
		C c=(C) Naming.lookup("pippo");
		// ...
	}
}

// server
public class RemotoServer{
	public static void main(String[] args) throws Exception{
		C c = new CImpl();
		Namin.rebind("pippo", c);
	}
}

/*
1. Vengono eseguite concorrentemente le seguenti istruzioni?
		CODICE DEL SERVER:
			c.k();
		CODICE DEL CLIENT:
			c.get();
	L'esecuzione dei metodi può interferire? [sono mutualmente esclusivi]
		~No, perchè non sono entrambe sincronizzate. k() non è synchronized

2. Se vengono eseguite le seguenti istruzioni:
		CODICE DEL CLIENT:
			c.k();
			c.k();
	Le due invocazioni del metodo remoto attivano sulla macchina server due thread che possono interferire?
		~Falso: non è che non interferiscono per qualche lock, ma perché sono sequenziati nello stesso thread

3. Se vengono eseguite concorrentemente le seguenti istruziuoni
	CODICE DEL SERVER:
		c.stampa();
	CODICE DEL CLIENT:
		c.set(new IImpl("Azzurro"));
	Non è detto che usl server venga stampata la stringa Azzurro perché I è sottotipo di Serilizable?
		~Se parte prima il codice del server stampa giallo. Il codice del client crea una nuova scatola Azzurro.
		È vero che non è detto che stampi Azzurro, ma non è per colpa di Serializable. Quindi a seconda di quello che parte
4. Si considerino le sequenti istruzioni:
	CODICE DEL SERVER:
		syncronized(c.get()){
			c.k();
			c.stampa();
			c.g();
		}
	CODICE DEL CLIENT:
		syncronized(c.get()){
			c.k();
			c.stampa();
			c.g();
		}
	Questi due blocchi sono mutualmente esclusivi?
		~No, perchè serializable e nel client crea una copia quindi non sono mutualmente esclusivi.
		c.get() nel server restituisce un oggetto serializable. 	Campo dati I di entrambi.
		c.get() nel client ritorna un tipo I extends seralizable; la chiamata ritorna un I x ma quando torna dalla funzione crea l'oggetto.

5. Si considerino le seguenti istruzioni:
	CODICE DEL SERVER:
		syncronized(c.g()){ ->c.g() ritorna C che è remoto
			c.k();
			c.stampa();
			c.g();
		}
	CODICE DEL CLIENT:
		syncronized(c.g()){ -> c.g() ritorna un riferimento; sto chiedendo il lock su un oggetto locale: uno stub
			c.k();
			c.stampa();
			c.g();
		}
	I due blocchi sono mutualmente esclusivi?
		~Non posso mai chiedere un lock tra client e server. L'oggetto è unico, ma chiedo i lock allo stub locale che è riferimento remoto. Non sono quindi mutualmente esclusivi.
		TUTTI I RITORNI DELLE FUNZIONI REMOTE DEVONO ESSERE O SERIAL O REMOTE.

6. Se vengono eseguite concorrentemente le seguenti istruzioni:
	CODICE DEL SERVER:
		syncronized(c.get()){
			c.get().stampa();
			c.g().stampa();
		}
	CODICE DEL CLIENT:
		syncronized(c.get()){
			c.get().stampa();
			c.g().stampa();
		}
	Vengono stampate sul server quattro stinghe il cui ordine è nondeterministico, vero o falso?
		~Solo tre stringhe vengono stampate sul server.

FAR GIRARE L'ESERCIZIO
*/