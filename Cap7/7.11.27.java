class Nodo {
	private String info;
	private Nodo next;
	public Nodo(String s) {info = s;}
	public void setNext(Nodo n) {next = n;}
	public static void stampaInfo(Nodo n) {
		synchronized (n) {
			if(n!=null)
				System.out.print(n.info + " ");
		}
	}

	public synchronized void stampaAncheNext() {
		System.out.print(info + " ");
		for (int i=0; i<9999999; ++i) ;
		stampaInfo(next);
		System.out.print("FINESTAMPA");
	}
}

class T extends Thread  {
	private Nodo n;
	public T(Nodo n) {this.n = n;}
	public void run() {
		n.stampaAncheNext();
	}
}

public class Esercizio {
	public static void main(String[] args) {
		Nodo uno = new Nodo("UNO"), due = new Nodo("DUE");
		uno.setNext(due); due.setNext(uno);
		T[] array = {new T(uno), new T(due)};
		int k = (int)(Math.random() * 2); //intero casuale in [0,1]
		array[k].start(); array[(k+1)%2].start();
	}
}

// 7.11.27
/*
	L'esecuzione andrà certamente in deadlock
	QUALCHE_STAMPA DEADLOCK
*/
