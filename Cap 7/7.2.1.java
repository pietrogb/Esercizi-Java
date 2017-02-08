// Il programma crea ed avvia due thread concorrenti che stampano un numero infinito di volte rispettivamente la stringa ping e la stringa PONG

public class PingPong extends Thread {
	private String parola;
	private int delay;

	public PingPong(String s, int d){
		parola = s;
		delay = d;
	}

	public void run() {
		try{
			for(;;) {
				System.out.print(parola + " ");
				Thread.sleep(delay);
			}
		}catch(InterruptedException e) {return;}
	}

	public static void main(String[] args) {
		Thread t1 = new PingPong("ping", 33);
		t1.start();
		Thread t2 = new PingPong("PONG", 33);
		t2.start();
	}
}
// 7.2.1