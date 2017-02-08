// esercizio 10.0.14


class C1{
	private int procedere = 0;
	public  void stampa(String s){
		System.out.println(s);
	}
	public synchronized int get() {
		return procedere;
	}
	public synchronized void set() {
		++procedere;
		// System.out.println(procedere);
	}
}

class T extends Thread{
	private C1 ob;
	String ini, fin;
	public T(C1 o , String i, String f) {ob=o; ini=i; fin=f;}
	public void run() {
		// stampa A
		ob.stampa(ini);
		ob.set();
		// synchronized (ob){
			while(ob.get() <2) {
				try {
					 ob.wait();
				} catch(Exception e) {}
			}
		// }
		ob.stampa(fin);
	}
}


// thread 2: uguale a thread 1

public class DueThread{
	public static void main(String[] args) {
		C1 c= new C1();
		T t1=new T(c, "A", "B");
		T t2=new T(c, "C", "D");
		t1.start();
		t2.start();
	}
}

/*
Il programma realizza la politica di sincronizzazione richiesta perché i thread si mette in attesa che l'oggetto condiviso C1 sia stato modificato da due thread, solo allora prosegue con la stampa dell'ultima lettera.
*/

/*Modificare il programma precedente in modo da renderlo un'applicazione distribuita*/
import java.rmi.*;
public interface C extends Remote{
	public int get() throws RemoteException;
	public void stampa() throws RemoteException;
	public vod set() throws RemoteException;
}

class C1 extends UnicastRemoteObject implements C{
	private int procedere = 0;
	public  void stampa(String s){
		System.out.println(s);
	}
	public synchronized int get() {
		return procedere;
	}
	public synchronized void set() {
		++procedere;
		// System.out.println(procedere);
	}
}

import java.rmi.*;
public class Server{
	public static void main(String[] args) throws Exception {
		C c = new C1(); //uso l'interfaccia per pubblicare l'oggetto
		Naming.rebind("pippo", c); //pubblica l'oggetto C
	}
}


import java.rmi.*;
public class Client1{
	String ini="A", fin="B";
	public static void main(String[] args) {
		try{
			//ottieni l'istanza dell'oggetto remoto. Usa l'interfaccia remota per istanziarlo
			C ref = (C)Naming.lookup("pippo");
			ref.stampa(ini);
			ref.set();
		// synchronized (ob){
			while(ob.get() <2) {
				try {
					 ob.wait();
				} catch(Exception e) {}
			}
		// }
		ob.stampa(fin);

		} catch(ConnectionException e){
			System.out.println("problemi di connessione al server");
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}
}

import java.rmi.*;
public class Client2{
	String ini="C", fin="D";
	public static void main(String[] args) {
		try{
			//ottieni l'istanza dell'oggetto remoto. Usa l'interfaccia remota per istanziarlo
			C ref = (C)Naming.lookup("pippo");
			ref.stampa(ini);
			ref.set();
		// synchronized (ob){
			while(ob.get() <2) {
				try {
					 ob.wait();
				} catch(Exception e) {}
			}
		// }
		ob.stampa(fin);

		} catch(ConnectionException e){
			System.out.println("problemi di connessione al server");
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}
}

/*
La politica di sincronizzazione si basa su un oggetto remoto risiedente sul server, su cui agiscono meteodi sincronizzati. Il meccanismo di sincronizzazione prevede una sequenza d'azioni analoga all'esempio eseguito in locale, con la differenza che essendo l'oggetto creato come un'
*/