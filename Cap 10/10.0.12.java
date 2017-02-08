// 10.0.12

public class Ristorante{
	private int nPrenotati;
	private final int maxPrenotazioni = 70;
	private int nOverbooked;	
	private Vector<Cliente> overbooked = new Vector<Cliente>();
	private boolean stopPrenotazioni = false;

	public synchronized Risposta prenota(Cliente c) {
		if(stopPrenotazioni==true)
			return new Risposta(0, false, false);

		if(nPrenotati < maxPrenotazioni){
			nPrenotati++;
			return new Risposta(nPrenotati, true, false);
		}
		// non c'è posto ma le prenotazioni sono ancora aperte
		nOverbooked++;
		overbooked.add(c);
		return new Risposta(0, false, true);
	}

	public synchronized void disdici() {
		nPrenotati--;
		if(nOverbooked>0) {
			Cliente c = overbooked.remove(0);
			nOverbooked--;n
			nPrenotati++;
			c.avvisa(new Risposta(nPrenotati, true, false));
		}
	}

	public synchronized stopPrenotazioni() {
		stopPrenotazioni=true;
		System.out.println("CHIUDONO LE PRENOTAZIONI, tot" + nPrenotati);
		for(int i=0; i<nOverbooked; i++) {
			Cliente c = overbooked.remove(0);
			c.avvisa(new Risposta(0, false, false));
		}
	}

	public static void main(String[] args) {
		Ristorante r = new Ristorante();
		Tempo t = new Tempo(r);
		t.start();

		Cliente[] clienti = new Cliente[50];
		for (int i=0; i<clienti.length; i++) {
			clienti[i]=new Cliente(r,i);
			clienti[i].start();
		}
		for (Cliente c : clienti){
			try {
				c.join();
			} catch(InterruptedException e) {}
		}
		try{
			t.join();
		} catch (InterruptedException e) {}
		System.out.println("Mancano due giorni a capodanno!");
	}
}

class Tempo extends Thread{
	private Ristorante r;
	Tempo(Ristorante rr) {r=rr;}
	public void run() {
		for (int i=1; i<29; i++) {
			try{
				sleep(300);
			} catch(InterruptedException e) {}
		}
		r.stopPrenotazioni();
	}
}

class Risposta{
	public int nPrenotazione;
	public boolean prenotato;
	public boolean overbooking;

	Risposta(int n, boolean p, boolean o){
		nPrenotazione=n; prenotato=p; overbooking=o;
	}
}

class Cliente extends Thread {
	private Ristorante r;
	private boolean prenotato = false;
	private boolean overbooking = false;
	private int id = 0;
	public Cliente(int n, Ristorante rr){
		id=n;
		r=rr;
	}

	public synchronized void avvisa(Risposta risp){
		prenotato=risp.prenotato();
		overbooking=risp.overbooking();
		notifyAll();
	}
	public void run(){
			Risposta risposta=r.prenota(this);
			prenotato=risposta.prenotato();
			overbooking=risposta.overbooking();

			synchronized(r){
				while(overbooking) {
					try{
						wait();
					}catch(InterruptedException e){}
				}
			}
			if(prenotato){
				System.out.println("Prenotazione effettuata, cliente: " +id);
			}

			int rand = (int) Math.random()*10;
			if(prenotato && (rand % 3 == 0)){
				r.disdici();
				System.out.println("Disdetto prenotazione, cliente " + id);
			}
		}
	}
}

/*
1. Quali metodi di Ristorante devono essere marcati Synchronized?
	Sono tutti da marcare synchronized (forse l'ultimo no)
2. Definire il metodo main di Ristorante in modo che:
	-simuli lo scorrere del mese di Dicembre con un thread di tipo Tempo;
	-crei un Ristorante e generi, durante quel mese, un certo numero di clienti
	-alla chiusura delle prenotazioni il metodo main deve stamare la stringa "mancano due giorni a capodanno". L'istruzione di stampa della stringa dev'essere contenuta nel main.
3. Definire la classe Cliente come un thread che tenta di prenotare un posto presso il ristorante. Se il cliente viene messo in overbooking, il thread si deve sospendere in attesa d'na disdetta o della chiusura delle prenotazioni.
Un cliente ogni tanto, tra quelli che sono riusciti a prenotare deve disdire la propria prenotazione. (->tirare un dado: if(((int)Math.ranodm()*10)%3==0) r.disdici)
4. Le classi vanno modificate il meno possibile


*/