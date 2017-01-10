class C {
	public int i = 0;
}

class T1 extends Thread {
	private C c;
	public T1(C c) {this.c = c;}

	public void run() {
		for (int k=0; k<10000; k++) {
			synchronized(c) {
				System.out.print("UNO");
				(c.i)++;
				c.notify();
			}
			System.out.print("DUE");
		}
	}
}

class T2 extends Thread {
	private C c;
	private T1 t;
	public T2(C c, T1 t) {this.c = c; this.t = t;}
	public void run() {
		try {
			synchronized(c) {
				System.out.print("TRE");
				while (c.i != 1) {
					System.out.print("QUATTRO");
					c.wait();
				}
			}
			System.out.print("CINQUE");
			t.join();
			System.out.println("SEI");
		} catch(InterruptedException e) {}
	}
}

public class Test {
	public static void main(String[] args) {
		C c = new C(); T1 t1 = new T1(c); T2 t2 = new T2(c, t1);
		t1.start(); t2.start();
	}
}

/*
Quale delle seguenti aggermazioni è vera per l'esecuzione del main() di Test?
	1. può provocare la stampa TRE QUATTRO UNO DUE CINQUE SEI
	2. può provocare la stampa UNO DUE TRE QUATTRO CINQUE SEI
	3. può provocare la stampa TRE QUATTRO CINQUE SEI UNO DUE
	4. può provocare la stampa UNO TRE CINQUE SEI DUE
		Risposta: 4. 
*/