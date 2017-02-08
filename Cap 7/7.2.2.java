// Il seguente programma crea ed avvia quattro thread concorrenti, ognuno dei quali stampa per cinque volte il proprio nome

public class ThreadTest extends Thread {
	public ThreadTest(String x) {super(x);}
	public void run() {
		for(int k=0; k<=5; k++){
			System.out.println("Thread " + getName() + "\tCiclo " + k);
		}
	}
	public static void main(String[] args) {
		new ThreadTest("Antonio").start();
		new ThreadTest(" Berto").start();
		new ThreadTest("  Carlo").start();
		new ThreadTest("   Diego").start();
	}
	
}

// 7.2.2