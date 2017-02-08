// Le seguenti classi compilano

class C{
	public String s = "pluto";
	public synchronized void m() {
		s= "pippo";
		for(int k=0; k<1000000; k++) ;
		s= "pluto";
	}
}

class T extends Thread {
	private C c;
	public T(C c) {this.c = c;}
	public void run() {
		for(int k=0; k<100; k++) {
			c.m();
			if((c.s).equals("pippo")) 
				System.out.println("BINGO");
		}
	}
}

public class Test{
	public static void main(String[] args) {
		C c = new C();
		T t1 = new T(c), t2 = new T(c);
		t1.start();
		t2.start();
	}
}


/*
Cosa provoca l'esecuzione del main di Test?
	1. Non provoca alcuna stampa
	2. Stampa 100 volte BINGO
	3. Potrebbe stampare BINGO
	4. Stampa almeno una volta BINGO
		~Risposta 3. 
*/