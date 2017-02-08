// Quali stampe provoca?

class C {
	private int i = 0;
	public synchronized void f() {
		for (int k=0; k<100000; k++) i++;
		for (int k=0; k<99999; k++) i--;
		this.notifyAll();
	} //Non stampa nulla

	public synchronized void g() throws InterruptedException {
		while (i<10) {
			this.wait(); //Attende fino a che la i diventa maggiore di 10
		}
		System.out.print("i = " + i);
	}
}

class T1 extends Thread {
	private C c;
	public T1(C y) {
		c = y;
	}
	public void run() {
		for (int i=0; i<100; i++) {
			c.f();
		}
	}
}

class T2 extends Thread {
	private C c;
	public T2(C y) {
		c = y;
	}
	public void run() {
		try{
			c.g();
		} catch(InterruptedException e) {}
	}
}

public class D {
	public static void main(String[] args) {
		C c = new C(); T1 t1 = new T1(c); T2 t2 = new T2(c);
		t1.start(); t2.start();
	}
}

/*
Risposta: 
	Stampa un valore casuale tra 10 e 100
*/