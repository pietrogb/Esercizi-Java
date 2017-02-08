// Generatore di numeri casuali

 class C{
	private int y=0;
	boolean goPrint = true;
	public synchronized void incrementa() {
		if(0<=y && y<255)
			y++;
		else y=0;
	}

	public synchronized void print() {
		System.out.println(" " + y%100 + " ");
		notifyAll();
	}
}

 class T1 extends Thread {
	private C c;
	public T1(C r) {c = r;}
	public void run() {
		while(true){
			synchronized(c) {
				c.incrementa();
			}
		}
	}
}

 class T2 extends Thread {
	private C c;
	public T2(C r) {c = r;}
	public void run() {
		while(true){
			try{
				wait();
			} catch(Exception e) {}
			c.print();
			try{
				wait();
			} catch(Exception e) {}
		}
	}

}

public class Ex {
	public static void main(String[] args) {
		C c = new C();
		T1 t1 = new T1(c); T2 t2 = new T2(c);
		t1.start(); t2.start();
	}
}

/*
Definire il metodo run() di t2 in modo che le classi compilino correttamente e l'esecuzione di Ex provochi la stampa di una sequenza infinita di numeri tutti compresi tra 0 e 99 in cui ogni numero è diverso dal successivo.
La definizione di run() di T2 deve rispettare il seguente vincolo:	
	Sono permesse solamente invocazioni di metodi della classe C esclusi i costruttori; qundi un'invocazione System.out.print(..) non è permessa
Definire gl'opportuni operatori della classe C che si ritengono necessari per la definizione della run() di T2. Non ci sono restrizioni sui metodi della classe C.
*/

// 7.11.14