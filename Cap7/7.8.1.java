/* Esempio vasi sull'uso di wait e notify.
Scrivere un programma che crea ed avvia due thread concorrenti t1 e t2 che soddisfano le seguenti specifiche:
	- il thread t2 stampa di tanto in tanto la stringa topolino
	- il thread t1 incrementa un contatore i a partire dal valore 0 fino al valore 10000
	- quando i vale 2000 il thread t1 "sospende" il thread t2, mentre quando i vale 6000 il thread t1 "riavvia" il thread t2. In particolare è richiesto che il thread t2 non effetui stampe mentre il contatore ha un valore nell'intervallo [2000, 6000].
*/

class T1 extends Thread {
	int i = 0;
	T2 t2;
	T1(T2 t2) {this.t2 = t2;}
	public void run() {
		while(i<10000) {
			synchronized(t2) {
				i++;
				if(i==2000)
					t2.sospendi = true;
				if(i==6000) {
					t2.sospendi = false;
					t2.notify();
				}
			} //fine sync
			try{
				Thread.sleep((int)(Math.random()*5));
			} catch(InterruptedException e) {}
		}
	}
}

class T2 extends Thread {
	boolean sospendi = false;
	public void run() {
		while(true) {
			synchronized(this) {
				while(sospendi == true){
					try{
						wait();
					} catch(InterruptedException e) {}
				}
				System.out.println("topolino");
			} //fine sync
			try{
				Thread.sleep((int)(Math.random()*5));
			} catch(InterruptedException e) {}
		}
	}
}

public class Thread2{
	public static void main(String[] args) {
		T2 t2 = new T2();
		T1 t1 = new T1(t2);
		t1.start(); t2.start();
	}
}

// 7.8.1