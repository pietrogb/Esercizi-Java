/*
Il seguente codice calcola il massimo tra i valori contenuti in due array calcolando in modo asincrono i due valori massimi degli array e facendo il confronto finale.
Il codice utilizza il tipo FutureTask<T> che implementa sia l'interfaccia Future<T> che l'interfaccia Runnable, e può quindi essere usato sia per costruire un Future che per mandarlo in esecuzione in un thread
Esempio pagg. 135-136
*/

import java.util.concurrent.*;

public class Test{
	static int max(int[] a) { //calcola il massimo in un array di int
		int i=a[0];
		for(int j=1; j<a.length; j++)
			if(a[j] < i)
				i=a[j];
		return i;
	}
	static class Max implements Callable<Integer> {
		private int[] a;
		Max(int[] ar) {a=ar;}
		public Integer call() {
			return max(a);
		}
	}
	public static void main(String[] args) {
		int[] a1={20, 34, 123, 84, 94, 300};
		int[] a2={48, 87, 294, 76, 9};
		try{
			FutureTask<Integer> fm = new FutureTask<Integer>(new Max(a1));
			Thread t = new Thread(fm);
			t.start(); //in parallelo sta calcolando il max di a1
			int m = max(a2); //ed intanto calcola il max di a2
			if(m < fm.get()) //se il max di a1 non è pronto, attende
				m = fm.get();
			System.out.println("max is " + m);
		} catch(Exception e) {}
	}
}


// Future e callable.java
// Risultato: max is 20
