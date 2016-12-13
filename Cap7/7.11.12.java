public class Torre{
	private int persone_piano_terra;
	private int persone_piano_uno;
	private final int max_in_scala=10;
	private Object lock_entrate=new Object();
	private class Guardia_piano_uno extends Thread{
		public void run() {
			while (true) { 
				try {
					synchronized(Torre.this) {
						while (persone_piano_uno == 0) { //qualcuno vuole scendere
							Torre.this.wait();
						}
						int p = min(persone_piano_uno, max_in_scala);
						persone_piano_uno -= p;
					}
				}catch(InterruptedException e) {}
			}
		}
	}
	private class Guardia_entrata extends Thread{
		public void run(){
			int p=0;
			while(true) {
				try{
					syncronized(lock_entrate){
						while(persone_piano_terra == 0) {
							lock_entrate.wait();
						}
						//qualcuno vuole salire -> conto quanti
						p = min(persone_piano_terra, max_in_scala);
						persone_piano_terra -= p;
					}
					syncronized(Torre.this) { //l'uso della scala richiede un lock diverso della coda d'entrata
						persone_piano_uno += p;
						Torre.this.notifyAll(); //sveglio la guardia all'ingresso
					}
					catch(InterruptedException e) {}
				}
			}
		}
	}
	public void entrate(int n) {
		syncronized(lock_entrate){
			persone_piano_uno += n;
			lock_entrate.notifyAll(); //produce e poi sveglia la guardia d'entrata
			//L'arrivo di un nuovo gruppo di visitatori non deve interferire con il gruppo che si stacca
		}
	}
	public static void main(String[] args) {
		Torre t=new Torre();
		Guardia_entrata g_in = new Guardia_entrata();
		Guardia_piano_uno gPu = new Guardia_piano_uno();
		g_in.start(); //concorrenza: i nuovi visistatori arrivano mentre 
		gPu.start();
		int visistatori=0;
		while(visitatori<500) {
			int nuovi = (int)(Math.random()*15);
			visistatori += nuovi;
			t.entrate(nuovi);
			//sleep;
		}
	}
}

/*
Lock impliciti: associati solo al tipo Oggetto -> int no, Integer sì
	-> Integer però è immutabile: facendo l'operazione ++ viene buttato via e se ne crea uno di nuovo
	-> Come lock devo usare un'istanza di Object che non sia immutabile, altrimenti ho problemi di concorrenza.
*/