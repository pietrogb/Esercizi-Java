/*
Rotatoria con quattro strade di accesso. Situazione in cui quattro auto si stanno dirigendo contemoranenamente, ma a velocità diverse, verso la rotatoria con l'intenzione di proseguire nella propria direzione di marcia. Ogni auto intende entrare nella rotatoria ed uscire alla seconda uscita a destra.
Completare il programma seguente.
Garantire che: quando un auto A arriva alla rotatoria, se la rotatoria è già stata impegnata dall'auto B proveniente dalla strada sulla sinistra, allora A prima d'entrare, attende che B sia uscita. Se la rotatoria è stata impegnata da qualsiasi altra delle due direzioni, A deve poter entrare nella rotatoria senza aspettare.
*/

class Auto extends Thread{
	private  int distanza;
	private Rotatoria r;
	private int direzione; //Nord=0; Est=1; Sud=2; Ovest=3;

	Auto(int d, Rotatoria rr, int dd){
		distanza=d; r=rr; direzione=dd;
	}

	public void run(){
		try{
			while(distanza>0){
				distanza--;
				sleep(((int)Math.random()*50));
			} 
			// ora l'auto è all'imbocco della rotatoria
			r.entraRotatoria(direzione);
			sleep(((int)Math.random())*70); //l'auto resta nella rotatoria
			r.esciRotatoria(direzione);
		} catch (InterruptedException e) {}
	}
}

class Rotatoria{
	boolean[] occupato = new boolean[4];
	public synchronized void entraRotatoria(int dir) {
		synchronized(this){
			while(occupato[(dir+3)%4] || occupato[dir]){
				try{
					wait();
				} catch(InterruptedException e) {}
			}
			occupato[dir]=true;
			occupato[(dir+1)%4]=true;
			System.out.println("Macchina " + dir + " entrata in rotatoria");
		}
	} //deve controllare se è libera l'entrata (dir+3)%4
	public synchronized void esciRotatoria(int dir) {
		synchronized (this){
			occupato[dir]=false;
			occupato[(dir+1)%4]=false;
			try {
	            notifyAll();
	        } catch (Exception ex) { ex.printStackTrace(); }
	        System.out.println("Macchina " + dir + " uscita dalla rotatoria");
		}
	} //esce su (dir+2)%4
}

public class QuattroAuto {
	public static void main(String[] args) {
		Rotatoria r = new Rotatoria();
		Auto[] a = new Auto[4];
		for (int i=0; i<4; i++) {
			a[i] = new Auto(10, r, i);
			a[i].start();
		}
		boolean autos_exited=false;
		while(autos_exited==false){
			autos_exited=true;
			for(int i=0; i<4; i++)
				autos_exited=autos_exited && !a[i].isAlive();
		}
		if(autos_exited == true)
			System.out.println("FINITO");
	}
}

/*
Completare la definzione della classe Rotatoria aggiungendo eventuali altri metodi e campi dati
Completare la definizione del metodo main in modo tale che il programma stampi la scritta FINITO quando tutte le auto hanno superato la rotatoria
giustificare brevemente perchè le auto hanno il comportamento richiesto
	~Le auto richiedono rimangono in attesa d'avere via libera a sinistra e davanti prima di partire; una volta acquisite queste due, occupa la direzione da cui s'immette e quella successiva (non quella dopo perché è quella da cui intende uscire e non c'è ragione d'impedire il traffico in ingresso da quella parte).
10.0.10
*/