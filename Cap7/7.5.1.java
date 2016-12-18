class T extends Thread{
	private int num;
	private	boolean runFlag = false;
	public boolean isRunning() {return runFlag;}
	public T(int x) {num=x;}
	public void run() {
		runFlag = true;
		for(int i=0; i< 100000; i++);
			System.out.println("Thread num " + num + " terminato");
	}
}

public class C {
	public static void main(String[] args) {
		T t1 = new T(1);
		T t2 = new T(2);
		t2.setPriority(Thread.MAX_PRIORITY);
		t1.start();
		try{ t1.join(); } catch(InterruptedException e) {}
		t2.start();
	}
}

/* 
SICURAMENTE stampa sempre: 
Thread num 1 terminato
Thread num 2 terminato
*/