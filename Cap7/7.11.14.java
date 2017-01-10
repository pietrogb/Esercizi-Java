// Generatore di numeri casuali

public class C{
	private int y=0;
	public synchronized void incrementa() {
		if(0<=y && y<255)
			y++;
		else y=0;
	}
	// ...
}

public class T1 extends Thread {
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

public class T2 extends Thread {
	private C c;
	public T2(C r) {c = r;}
	public void run() {
		// ...
	}

}

public class Ex {
	public static void main(String[] args) {
		C c = new C();
		T1 t1 = new T1(c); T2 t2 = new T2(c);
		t1.start(); t2.start();
	}
}