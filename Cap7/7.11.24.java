// uò succedere che l'esecuzione di C provochi la stampa BINGO?

class Z {
	public static void f(int[] a) {
		synchronized(a) {
			for (int i=0; i<a.length; i++) {
				a[i] = (int)(Math.random() * 10 -5);
			}
			for (int i=0; i<a.length; i++) {
				if(a[i] < 0) a[i]=0-a[i];
			}
		}
	}
	public static boolean neg(int[] a) {
		synchronized(a) {
			for (int i=0; i<a.length; i++) {
				if(a[i] < 0)
					return true;
				return false;
			}
		}
		return true;
	}
}

class T1 extends Thread {
	public int[] a;
	public T1(int[] a) {this.a=a;}
	public void run() {
		for (int i=0; i<10; i++) {
			Z.f(a);
		}
	}
}

class T2 extends Thread {
	public int[] a;
	public T2(int[] a) {this.a=a;}
	public void run() {
		for (int i=0; i<10; i++) {
			if(Z.neg(a))
				System.out.println("BINGO");
		}
	}
}

public class C {
	public static void main(String[] args) {
		int[] a = new int[1000]; //Array di tutti 0
		T1 t1 = new T1(a);
		T2 t2 = new T2(a);
		t2.start(); t1.start();
	}
}

// Non è possibile che venga stampata la stringa BINGO, visto che i cambiamenti all'array avvengono in maniera sincornizzata, il che determina che al'uscia della f() tutti gli elementi dell'array si trovano in [0..5]