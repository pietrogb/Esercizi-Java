
// Modificare il codice senza aggiungere campi dati e metodi

class Investito extends Exception{
	int chi; int cosa;
	Investito(int i, int j){
		chi=i; cosa=j;
	}

	void info() {System.out.println("INCIDENTE: " + chi + " ha investito " + cosa);}
}

class Attraversamento{
	boolean[] occupato = new boolean[3];
	// occupato[0] dice se il pedone sta attraversando
	// occupato[1] dice se un'auto sta arrivando da sinistra
	// occupato[2] dice se un'auto da destra sta attraversando

	Attraversamento() {
		occupato[0] = false;
		occupato[1] = false;
		occupato[2] = false;
	}

	public synchronized void occupa(int chi) throws Investito{
		if(chi == 0){
			while(occupato[1] == true || occupato[2] == true)
				try{wait();} catch(InterruptedException e) {}
		}
		else while(occupato[0] == true)
			try{wait();} catch(InterruptedException e) {}

		occupato[chi] = true;

		System.out.println( Thread.currentThread().getName() + " è su incrocio");
		// System.out.println( chi + " è su incrocio");
		if(chi == 0 && occupato[1] == true) throw new Investito(0, 1);
		if(chi == 0 && occupato[2] == true) throw  new Investito(0,2);
		if(occupato[0] == true && chi != 0) throw new Investito(chi, 0);
	}

	public synchronized void libera(int chi) {
		occupato[chi] = false;
		this.notifyAll(); /*nuovo OK */
	}
}

class Auto extends Thread{
	Attraversamento a;
	int direzione; // 1 da sinistra, 2 da destra
	int distanza;

	Auto(Attraversamento att, int dir, int dist, String nome) {
		super(nome); a = att; direzione = dir; distanza = dist;
	}

	public void run() {
		try{
			for(int i = distanza; i>0; i--){
				Thread.sleep((int)Math.random()*20);
			}
			// synchronized(a){ /*nuovo*/
				a.occupa(direzione);
				Thread.sleep(1);
				a.libera(direzione);
			// }
			System.out.println(getName() + " è passata ");
		} catch(InterruptedException e) {}
		  catch(Investito e) {e.info();}
	}
}

class Pedone extends Thread{
	Attraversamento a;
	int distanza;

	Pedone(Attraversamento att, int dist){
		super("Pedone"); a = att; distanza = dist;
	}

	public void run() {
		try{
			for(int i=distanza; i>0; i--){
				Thread.sleep((int)Math.random()*80);
			}
			// synchronized(a){ /*nuovo*/
				a.occupa(0);
				Thread.sleep(1);
				a.libera(0);
			// }
			System.out.println("Pedone è passato ");
		} catch(InterruptedException e) {}
		  catch(Investito e) {e.info();}
	}
}

public class IncrocioPulito{
	public static void main(String[] args) {
		Attraversamento a = new Attraversamento();

		Auto a1 = new Auto(a, 1, 10, "Auto da sinistra");
		Auto a2 = new Auto(a, 2, 5, "Auto da destra");
		Pedone p = new Pedone(a, 8);

		a1.start(); a2.start(); p.start();
	}
}

/*
Inserendo la suncronizzazione sull'incrocio senza fare alcuna wait implemento una coda FCFS.
La soluzione invece fa aspettare il turno
*/