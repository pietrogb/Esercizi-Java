// esempio base sull'uso dei lock

/*Scrivere un programma che crea ed avvia due thread concorrenti tali che:
	- entrambi i thread devono essere istanze di una stessa classe;
	- il metodo run di questi thread deve contenere, tra le altre cose, due istruzioni distinte che stampano rispettivamente la stringa pippo e la stringa pluto
	- ogni esecuzione del programma deve produrre in output la seguente stampa: 
		pippo pliuto pippo pluto
	  In altre parole, le stampe dei due thread non devono interferire fra loro.
*/

class T extends Thread{
	Object lock;
	T(Object l) {lock = l;}
	public void run() {
		synchronized(lock) {
			System.out.println("pippo");
			System.out.println("pluto");
		}
	}
}

public class C {
	public static void main(String[] args) {
		Object lock = new Object();
		T t1 = new T(lock);
		T t2 = new T(lock);
		t1.start();
		t2.start();
	}
}

// 7.6.2

/*
Grazie all'uso di un blocco synchronized, le due istruzioni di stampa si possono vedere come un'unica istruzione atomica.
L'oggetto che foverna l'accesso esclusivo al blocco syncornized è semplicmente un'ustanza della classe Object che non vinee direttamente acceduta dai due thread. L'uso delle sezioni critiche, nn serve solamnete a proteggere l'integrità dello stato degli oggetti condivisi, ma più in generale serve a rendere (virtualmente) atomica l'esecuzione di una qualsiasi sequenza di istruzioni.
*/