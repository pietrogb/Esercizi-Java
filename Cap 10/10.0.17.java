public interface CI extends Remote{
	public void m() throws RemoteException;
	public void n() throws RemoteException;
}

public class C implements CI extends UnicastRemoteException{
	class T extends Thread{
		String toPrint;
		public T(String s) {
			toPrint=s;
		}
		public void run() {
			for (int i=0; i<10; i++) {
				try{
					sleep(((int)Math.random())*10);
				} catch(InterruptedException e) {}
				System.out.println(toPrint);
			}
		}
	}
	public synchronized void m() throws RemoteException {
		T t = new T("AAA");
		t.start();
	}
	public synchronized  void n() throws RemoteException {
		T t = new T("BBB");
		t.start();
	}
}

public class Server{
	public static void main(String[] args) throws Exception{
		CI c = new C();
		naming.rebind("pippo", c);
	}
}

public class Client1{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.m();
		}
	}
}

public class Client2{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.n();
		}
	}
}



/*
1a.
	class C{
		class T extends Thread{
			String toPrint;
			public T(String s) {
				toPrint=s;
			}
			public void run() {
				for (int i=0; i<10; i++) {
					System.out.println(toPrint);
				}
			}
		}
		public void m() {
			T t = new T("AAA");
			t.start();
		}
		public void n() {
			T t = new T("BBB");
			t.start();
		}
		public static void main(String[] args) {
			C c = new C();
			c.m();
			c.n();
		}
	}


	L'assenza d'interferenza tra i metodi è dovuta al fatto che ognuno mantiene l'uso esclusivo delle risorse fino a quando non ha concluso l'esecuzione, dato che non c'è un momento in cui il metodo che si occupa della stampa si mette in wait o sleep.

1b.
	class C{
		class T extends Thread{
			String toPrint;
			public T(String s) {
				toPrint=s;
			}
			public void run() {
				for (int i=0; i<10; i++) {
					try{
						sleep(((int)Math.random())*10);
					} catch(InterruptedException e) {}
					System.out.println(toPrint);
				}
			}
		}
		public void m() {
			T t = new T("AAA");
			t.start();
		}
		public void n() {
			T t = new T("BBB");
			t.start();
		}
		public static void main(String[] args) {
			C c = new C();
			c.m();
			c.n();
		}
	}

	Il programma prima d'effettuare la stampa invoca il metodo sleep, provocando il momentaneo rilascio delle risorse, e la conseguente esecuzione dell'altro thread che si occupa di stampare una sequenza differente di stringhe in uqello che altrimenti sarebbe un flusso ininterrotto di 10 stampe della stringa "AAA" ed altre 10 della stringa "BBB"

1c.
	class C{
		class T extends Thread{
			String toPrint;
			public T(String s) {
				toPrint=s;
			}
			public void run() {
				for (int i=0; i<10; i++) {
					try{
						sleep(((int)Math.random())*10);
					} catch(InterruptedException e) {}
					System.out.println(toPrint);
				}
			}
		}
		public void m() {
			T t = new T("AAA");
			t.start();
			n();
		}
		public void n() {
			T t = new T("BBB");
			t.start();
			m();
		}
		public static void main(String[] args) {
			C c = new C();
			c.m();
			c.n();
		}
	}

	No, non si tratta di deadlock, quanto piuttosto di una ricorsione infinita, dato che non c'è un'attesa di una risorsa, come era nell'esercizio n. 16.

2.
Caso in cui l'oggetto c di classe C risiede su una macchina server ed i metodi m ed n siano invocabili remotamente. Un client ha un RIFERIMENTO REMOTO all'oggetto c.
2a. Dare due esempi diversi di applicazione distribuita in cui i metodi m ed n hanno un'elevata probabilità di essere eseguiti concorrentemente.

---------------------n.1-------------------------
public interface CI extends Remote{
	public void m() throws RemoteException;
	public void n() throws RemoteException;
}

class C implements CI extends UnicastRemoteException{
	class T extends Thread{
		String toPrint;
		public T(String s) {
			toPrint=s;
		}
		public void run() {
			for (int i=0; i<10; i++) {
				try{
					sleep(((int)Math.random())*10);
				} catch(InterruptedException e) {}
				System.out.println(toPrint);
			}
		}
	}
	public void m() throws RemoteException {
		T t = new T("AAA");
		t.start();
	}
	public void n() throws RemoteException {
		T t = new T("BBB");
		t.start();
	}
}

public class Server{
	public static void main(String[] args) throws Exception{
		CI c = new C();
		naming.rebind("pippo", c);
	}
}

public class Client1{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.m();
		}
	}
}

public class Client2{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.n();
		}
	}
}

-------------------n.2---------------------------
public interface CI extends Remote{
	public void m() throws RemoteException;
	public void n() throws RemoteException;
}

public class C implements CI extends UnicastRemoteException{
	class T extends Thread{
		String toPrint;
		public T(String s) {
			toPrint=s;
		}
		public void run() {
			for (int i=0; i<10; i++) {
				try{
					sleep(((int)Math.random())*10);
				} catch(InterruptedException e) {}
				System.out.println(toPrint);
			}
		}
	}
	public synchronized void m() throws RemoteException {
		T t = new T("AAA");
		t.start();
	}
	public synchronized  void n() throws RemoteException {
		T t = new T("BBB");
		t.start();
	}
}

public class Server{
	public static void main(String[] args) throws Exception{
		CI c = new C();
		naming.rebind("pippo", c);
	}
}

public class Client1{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.m();
		}
	}
}

public class Client2{
	public static void main(String[] args) throws Exception{
		try{
			CI c = (C)Naming.lookup("pippo");
			c.n();
		}
	}
}

-------------------------------------------------------
2b. Nel primo caso non è presente alcuna sincronizzazione, di conseguenza i metodi hanno un'ampia probabilità di essere eseguiti concorrentemente e di interferire l'uno col l'altro nella sequenza delle stampe.
Nel secondo caso i metodi m() ed n() sono sincronizzati, di conseguenza il primo che è invocato procede con la sua esecuzione fino ad arrivare a concluderla, senza che l'altro possa causare interferenza. È stato realizzato un meccanismo di sincronizzazione lato server.
*/