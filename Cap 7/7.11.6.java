class Registro_Redditi{
	private int[] redditi;
	private int n_inserimenti=0;
	private boolean bonus_utilizzato=false;
	public Registro_Redditi(int dim_famiglia){
		redditi = new int[dim_famiglia];
	}
	public int riassunto(){
		int ris=0;
		for(int i=0; i<redditi.length; i++){
			ris+=redditi[i];
		}
		return ris;
	}

	public void add(int value){
		redditi[n_inserimenti]=value;
		n_inserimenti++;
	}

	public int tot(){
		int ris=0;
		for(int i=0; i<n_inserimenti; i++)
			ris+=redditi[i];
		return ris;
	}

	public boolean bonusExpired() {
		return bonus_utilizzato;
	}

	public int getdim() {
		return n_inserimenti;
	}
}

abstract class Membro_Famiglia extends Thread{
	public abstract  void run();
	protected Registro_Redditi c;
	public Membro_Famiglia(Registro_Redditi c, String nome){
		super(nome);
		this.c=c;
	}
}

class Figlio extends Membro_Famiglia{
	Figlio(Registro_Redditi c, String nome){
		super(c, nome);
	}
	public void run(){
		int reddito=(int)(Math.random()*1000);
		synchronized(c){
			c.add(reddito);
			System.out.println(Thread.currentThread(). getName() + " Dichiara " + reddito);
			c.notifyAll();
		}
	}
}

class Genitore extends Membro_Famiglia{
	private int n_figli=0;
	public Genitore(Registro_Redditi c, int figli, String nome){
		super(c, nome);
		n_figli=figli;
	}
	public void run(){
		try{
			int reddito=(int)(Math.random()*2000);
			synchronized(c){
				while(c.getdim() < n_figli)
					c.wait(); //aspetta i redditi dei figli a carico
				// un solo genitore ha il bonus: 5% dei redditi dei figli
				if(c.getdim() == n_figli) // è il primo genitore
					c.add(reddito-(c.tot() / 100 *5));
				System.out.println(Thread.currentThread().getName() + " Dichiara " + reddito);
			}
		} catch(InterruptedException e) {}
	}
}

public class TasseAbstr{
	public static void main(String[] args) {
		Registro_Redditi c = new Registro_Redditi(6);
		Figlio f1 = new Figlio(c, "A");
		Figlio f2 = new Figlio(c, "B");
		Figlio f3 = new Figlio(c, "C");
		Figlio f4 = new Figlio(c, "D");
		Genitore g1 = new Genitore(c, 4, "Mamma");
		Genitore g2 = new Genitore(c, 4, "Papà");
		f1.start(); g1.start();
		f2.start();
		g2.start();
		f3.start();
		f4.start();
		try{
			g1.join();
			g2.join();
		} catch(InterruptedException e) {}
		System.out.println(c.riassunto());
	}
}