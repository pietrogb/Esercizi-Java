class C {
	private int i = 0;
	public synchronized void set(int n) { i = n; }
	public synchronized int get() { return i; }
}

public class Ex {
	private C c = new C();
	private class T1 extends Thread {
		public void run() {
			synchronized(c){
				for(int i=0; i<100; ++i){
					System.out.print("UNO( " + c.get() + ")");
					c.notify(); c.set(2);
					try{
						while(c.get() != 1)
							c.wait();
					} catch(Exception e) {}
				}
			}
		}
	}

	private class T2 extends Thread {
		public void run() {
			synchronized(c){
				for(int i=0; i<100; ++i){
					System.out.print("DUE( " + c.get() + ")");
					try{
						while(c.get() != 2)
							c.wait();
					} catch(Exception e) {}
					c.set(1); c.notify();					
				}
			}
		}
	}

	public static void main(String[] args) {
		Ex e = new Ex(); Thread[] array = {e.new T1(), e.new T2()};
		int k = (int)(Math.random() * 2); //intero casuale in [0,1]
		array[k].start(); array[(k+1)%2].start();
	}
}

/*
	Le stampe possibili sono:
		QUALCHE_STAMPA
		QUALCHE_STAMPA WAIT
*/