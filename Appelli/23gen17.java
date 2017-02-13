public interface Albero{
	Albero add(String s); //ritorna l'albero con n nodo in più con s nel campo info
	void stampa(); //stampa inline dell'albero
	boolean presente (String s); //ritorna true se s è presente in un nodo
	int nNodi(); //numero di nodi, cioè stringhe, contenute nell'albero
}

public interface Iteratore {
	boolean hasNext(); //dice se c'è un prossimo elemento su cui iterare
	String next(); //restituisce la stringa contenuta nel prossimo elemento e sposta avanti l'iteratore
}

class AlberoVuoto implements Albero {
	public Albero add(String s) {
		return new AlberoImpl(s, new AlberoVuoto(), new AlberoVuoto());
	}
	public void stampa() {
		System.out.print("- ");
	}
	public boolean presente(String s) {
		return false;
	}
	public int nNodi() {
		return 0;
	}
}

class AlberoImpl implements Albero{
	private String info;
	private Albero figlioSin;
	private Albero figlioDx;

	AlberoImpl(String s) {
		info=s; figlioSin=new AlberoVuoto(); figlioDx=new AlberoVuoto();
	}
	AlberoImpl(String s, Albero fs, Albero fd) {
		info=s; figlioSin=fs; figlioDx=fd;
	}

	public Albero add(String s) {
		int ns = figlioSin.nNodi();
		int nd = figlioDx.nNodi();
		if(ns > nd) figlioDx=figlioDx.add(s);
		else figlioSin=figlioSin.add(s);
		return this;
	}

	public void stampa() {
		figlioSin.stampa(); System.out.print(info+" "); figlioDx.stampa();
	}

	public boolean presente(String s) {
		return(s.equals(info) || figlioSin.presente(s) || figlioDx.presente(s));
	}

	// class Iterator implements Iteratore{
	// 	public Object alb;
	// 	Iteratore() {alb = this;}
	// 	public boolean hasNext() {
	// 		return (figlioSin.nNodi() != 0);
	// 	}
	// 	public String getInfo() {
	// 		return info;
	// 	}
	// 	public String next() {
	// 		alb = figlioSin;
	// 		return alb.info;
	// 	}
	// }

	public Iteratore itera() { //restituisce un iteratore sull'albero non vuoto dato in modo tale per cui le successive chiamate del suo metodo next() restituiscano le stringhe contenute nel cammino più a sinistra dell'albero di invocazione.
		return new Iteratore() {
			private Albero curr = AlberoImpl.this;
			public boolean hasNext() {
				return (figlioSin.nNodi() != 0);
			}
			public String next() {
				if (curr.hasNext()){
					curr = curr.figlioSin;
					return curr.info;
				}
				else return NULL;
			}
		}
	}
}

class Main{
	public static void main(String[] args) {
		Albero a = new AlberoImpl("pippo");
		a.add("pluto").add("paperino").add("minnie").add("topolino").add("gastone").add("paperone");
		a.stampa(); // - minnie - pluto - gastone - pippo - topolino - paperino - paperone
		class T1 extends Thread{
			public void run(){
				synchronized (a){
					a.add("qui");
					Thread.sleep((int)Math.random()*100);
					a.add("quo");
				}
				a.stampa();
			}
		}
		class T2 extends Thread {
			public void run() {
				synchronized (a){
					if(a.presente("quo"))
						System.out.print("BIANCO");
					else
						System.out.print("NERO");
				Thread.sleep((int)Math.random()*100);
				System.out.print("ROSSO");
				}
			}
		}
		class T3 extends Thread {
			public void run() {
				synchronized (a){
					if(a.presente("qui"))
						System.out.print("UNO");
					else
						System.out.print("DUE");
				}
				Thread.sleep((int)Math.random()*100);
				System.out.print("TRE");
			}
		}
		T1 t1 = new T1(); T2 t2 = new T2(); T3 t3  =new  T3();
		t1.start(); t2.start(); t3.start();

		Iteratore i =a.itera();
		System.out.print(i.getInfo());
		while(i.hasNext()){
			System.out.print(" i.next() ")
		}
	}
}


/*
1.
Completare il metodo main in modo che avvii tre thread concorrenti t1, t2, t3 ripettando le seguenti specifiche:
	a. Il thread t1 aggiunge all'albero a la striga qui, fa passare un po di tempo, aggiunge all'albero la stringa quo ed infine stampa l'albero
	b. il thread t2 controlla se nell'albero a è presente la stringa quo e stampa BIANCO in caso positivo, NERO altrimenti; fa passare un po' di tempo ed infine stampa la stringa ROSSO.
	c. t3 controlla se qui è presente nell'albero e stampa UNO in caso positivo, DUE altrimenti; fa passare un po' di tempo ed infine stampa la stringa TRE.
	d. l'aggiunta di una stringa al'albero non deve interefereire con gli accessi concorrenti in lettura all'albero. Giustificare brevemente perché il codice descritto rispetta questo vincolo.
		~L'aver inserito le operazioni di lettura e scrittura all'interno di blocchi synchronized garantisce che l'operazione all'interno del blocco avrà garantito l'uso esclusivo del'albero. In questo modo non saranno presenti accessi concorrenti in lettura o scrittura, fino a quando l'operazione non sarà conclusa.
	e. le operazioni di test-and-set effettuate dai thread t2, t3 devono essere corrette: ad esempio, quando t2 stampa NERO non è possibile che nel frattempo t1 abbia inserito la stringa quo. Giustificare brevemente perchè il codice descritto rispetta questo vincolo.
		~ Perché i test fatti all'interno di t2, t3 sono inseriti all'interno di blocchi synchronized vincola l'uso dell'albero al solo metodo che ha acquisito il lock sull'albero stesso, così che se t2 è entrato nella sezione critica, t1 e t3 - se devono ancora eseguire - devo attendere che t2 abbia concluso le operazioni.
	f. le due stampe dei colori effettuate da t2 devono essere mutualmente esclusive rispetto alle due stampe dei numeri effettuate da t3 ma possono essere concorrenti rispetto alla stampa dell'albero a effettuata dal thread t1. Ad esempio l'output NERO ...stampa albero... ROSSO UNO TRE è ammissibile, mentre BIANCO UNO TRE ROSSO ..stampa albero.. non lo è. Giustificare brevement perché il codice descritto rispetta questo vincolo.
		~ La sezione critica di t2 si conclude slo al termine della stampa di tutti i colori, mentre quella di t3 si conclude subito dopo che sono state concluse le operazioni che seguono il test sull'albero. Così si garantisce la possibilità d'avere la concorrenza desiderata.
	- Non è possibile modificare la descrizione della classe AlberoImpl ma solo scrivere codice all'interno del main

2. aggiungere alla sola classe AlberoImpl il metodo public Iteratore itera() che restituisce un iteratore sull'albero non vuoto dato in modo tale per cui le successive chiamate del suo metodo next() restituiscano le stringhe contenute nel cammino più a sinistra dell'albero di invocazione. Aggiungere al main il codice di invocazione del metodo itera() sull'albero a ed il codiche che ne effettua l'iterazione completa procucendo in output la sequenza pippo pluto minnie qui, che corrisponde al cammino più a sinistra dell'albero a

3. Scrivere una versione distribuita dell'applicazione scritta nel punto (1) rispettando le seguenti specifiche:
	a. l'albero a deve essere un oggetto di tipo Remote. Una macchina Server crea l'albero a, lo riempie, ne pubblica il riferimento sul registro RMI e poi realizza quello che faceva il thread t1 del punto (1)
	b. i thread t2, t3 descritti nel punto (1) devono essere avviati su una macchina Client che prima di tutto recupera il riferimento all'albero remoto a.
	c. per quanto riguarda i vincoli di comportamento descritti nel punto (1) in questa versione è possibile modificare il codice della classse AlberoImpl. In particolare, devono essere aggiunte le seguenti specifiche:
		i.) l'aggiunta di una stringa all'albero non deve interferire con gli accessi concorretni (e remoti) in lettura all'albero. Giustificare brevemente perché il codice scritto rispetta questo vincolo
		ii.) non è necessario che le operazioni di test-and-set effettuate dai thread t2, t3 siano correte: ad esempio, quando t2 stampa NERO è possibile che nel frattempo il server abbia inserito la stringa quo. Indicare se e perché il codice scritto permette che in qualce esecuzione le operazioni di test-and-set non siano corrette.
		iii.) le due stampe dei colori effettuate da r2 devono essere mutualmente esclusive rispetto alle due stampe dei numeri effettuate da t2 ma possono essere concorrenti rispetto alla stampa dell'albero a effettuata dal thread t1. Giustificare brevemente perché il codice scritto rispetta questo vincolo.
		iv.) descrivere TUTTE  le possibili stampe prodotte dall'applicazione distribuita realizzata.
*/

public interface Albero extends Remote{
	Albero add(String s); //ritorna l'albero con n nodo in più con s nel campo info
	void stampa(); //stampa inline dell'albero
	boolean presente (String s); //ritorna true se s è presente in un nodo
	int nNodi(); //numero di nodi, cioè stringhe, contenute nell'albero
}

public interface Iteratore {
	boolean hasNext(); //dice se c'è un prossimo elemento su cui iterare
	String next(); //restituisce la stringa contenuta nel prossimo elemento e sposta avanti l'iteratore
}

class AlberoVuoto implements Albero extends UniCastRemoteObject{
	public Albero add(String s) {
		return new AlberoImpl(s, new AlberoVuoto(), new AlberoVuoto());
	}
	public void stampa() {
		System.out.print("- ");
	}
	public boolean presente(String s) {
		return false;
	}
	public int nNodi() {
		return 0;
	}
}

class AlberoImpl implements Albero extends UniCastRemoteObject {
	private String info;
	private Albero figlioSin;
	private Albero figlioDx;

	AlberoImpl(String s) {
		info=s; figlioSin=new AlberoVuoto(); figlioDx=new AlberoVuoto();
	}
	AlberoImpl(String s, Albero fs, Albero fd) {
		info=s; figlioSin=fs; figlioDx=fd;
	}

	public synchronized Albero add(String s) {
		int ns = figlioSin.nNodi();
		int nd = figlioDx.nNodi();
		if(ns > nd) figlioDx=figlioDx.add(s);
		else figlioSin=figlioSin.add(s);
		return this;
	}

	public void stampa() {
		figlioSin.stampa(); System.out.print(info+" "); figlioDx.stampa();
	}

	public boolean presente(String s) {
		return(s.equals(info) || figlioSin.presente(s) || figlioDx.presente(s));
	}
}


class Server{
	public static void main(String[] args) throws Exception {
		Albero a = new AlberoImpl("pippo");
		a.add("pluto").add("paperino").add("minnie").add("topolino").add("gastone").add("paperone");
		Naming.rebind("albero", a);
		synchronized (a){
			a.add("qui");
			Thread.sleep((int)Math.random()*100);
			a.add("quo");
		}
	}
}

class Client {
	public static void main(String[] args) throws Exception {
		try {
			Albero a = (Albero)Naming.lookup("albero");
			class T2 extends Thread {
				public void run() {
					synchronized (a){
						if(a.presente("quo"))
							System.out.print("BIANCO");
						else
							System.out.print("NERO");
					}
					Thread.sleep((int)Math.random()*100);
					System.out.print("ROSSO");
				}
			}
			class T3 extends Thread {
				public void run() {
					synchronized (a){
						if(a.presente("qui"))
							System.out.print("UNO");
						else
							System.out.print("DUE");
					}
					Thread.sleep((int)Math.random()*100);
					System.out.print("TRE");
				}
			}
			T2 t2 = new T2(); T3 t3  =new  T3();
			t2.start(); t3.start();
		} catch(ConnectionException e) {
			System.out.println("Problemi di connessione al server");
		} catch(Exception ee){
			ee.printStackTrace());
		}
	}
}