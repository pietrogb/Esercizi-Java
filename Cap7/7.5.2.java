// Il programma stampa per 100 volte il valore del contatore gestito dal thread demone

class Daemon extends Thread {
	public int i = 0;
	public Daemon() {
		setDaemon(true);
	}
	public void run() {
		// ciclo infinito
		while(true) { ++i; if(i>10000000) i=0;}
	}
}
public class D extends Thread {
	Daemon dm = new Daemon();
	private int j = 0;
	public void run() {
		dm.start(); //start() del daemon
		while(j<100) {
			++j; System.out.print(dm.i + " ");
			//servizio fornito dal daemon
		}
	}
	public static void main(String[] args) {
		D d = new D(); 
		d.start();
	}
}