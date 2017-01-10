class C {
	private int numDiversiDaZero = 0;
	private int[] a = new int[10];
	public synchronized void print() {
		for (int i=0; i<10; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	public synchronized void set(int numero, int posizione) {
			a[posizione] = numero;
			numDiversiDaZero++;
		
	}

	public synchronized void m() {
		int k=0;
		while(numDiversiDaZero<5){
			try{
				wait();
			}catch(Exception e){}
		}
		notifyAll();
		print();
	}
}

class T1 extends Thread {
	private C c;
	public T1(C y) {c=y;}
	public void run() {
		for (int i=0; i<1000000; i++) {
			int k = (int) (Math.random() * 11 - 5); //k casuale in [-5, 5]
			c.set(k, i%10);
		}
	}
}

class T2 extends Thread {
	private C c;
	public T2(C y) {c=y;}
	public void run() {
		try{
			c.m(); 
		} catch(Exception e) {}
	}
}

class D {
	public static void main(String[] args) {
		C c = new C(); T1 t = new T1(c); T2 r = new T2(c), s= new T2(c);
		r.start(); t.start(); s.start();
	}
}

/*
definire i metodi set(int, int) e m() in modo che l'esecuzione del main() di D o provochi la stampa di due e due sole righe di 10 interi ciascuna con almeno 5 valori riversi da 0 oppure non termina.

Il metodo m() non deve contenere altre istruzioni di stampa oltre al print() obbligatorio.
*/

// 7.11.15