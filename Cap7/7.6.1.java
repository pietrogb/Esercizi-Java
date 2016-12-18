/*Il seguente programma avvia due thread concorrenti, ognuno dei quali invoca 10 volte il metodo m sullo stesso oggetto (riferito da) c*/

/*
class C {
	public int i = 0;
	public void m() {
		for(int k = 0; k<1000000; k++) i++;
		for(int k = 0; k<1000000; k++) i--;
	}
}

public class T extends Thread {
	private int num;
	private C c;
	public T(int x, C y) {num = x; c=y;}
	public void run() {
		for(int i=0; i<10; i++) {
			c.m();
			System.out.println("Thread " + num + ": c.u= " + c.i);
		}
	}

	public static void main(String[] args) {
		C c = new C();
		T t1 = new T(1,c), t2 = new T(2, c);
		t1.start(); t2.start();
	}
}
*/

// 7.6.1

/*L'esecuzione del codice mostra una grande probabilità che le stampe siano degli interi diversi da 0. Una prima possibilità di correggere le interferenze tra i due thread è quella di rendere sincornizzato il metodo m, impedendo che sia eseguito /sullo stesso oggetto) contemproaneamente dai due thread. Questa modifica però riduce soltanto la probabilità che alcune stampe producano valori diversi da 0. Per essere certi che il programma stampi solamente valori uguali a 0 bisogna sincoronizzare tutti gli accessi al contatore i, sia quelli in scirttura - effettuati dal metodo m - che quelli in lettura contenuti nel metodo run.*/

//Programma aggiornato

class C {
	private int i = 0;
	public synchronized void m() {
		for(int k=0; k<1000000; k++) i++;
		for(int k=0; k<1000000; k++) i--;
	}
	public synchronized int getI() {return i;}
}

public class T extends Thread {
	private int num;
	private C c;
	public T(int x, C y) {num = x; c = y;}
	public void run() {
		for(int i = 0; i<10; i++) {
			c.m();
			System.out.println("Thread num " + num + ": c.i= " + c.getI());
		}
	}
	public static void main(String[] args) {
		C c = new C(); T t1 = new T(1, c), t2 = new T(2,c);
		t1.start(); t2.start();
	}
}