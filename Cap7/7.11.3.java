// 7.11.4
/*
Si consideri una tavola calda in cui vengono serviti i pasti nel seguente modo:
	- i clienti si mettono in coda in ordine di arrivo
	- ogni cliente prende un prim piatto, poi un secondo piatto ed infine paga alla cassa
	- i clienti che hanno già prelevato i loro piatti possono pagare in un ordine qualsiasi
	- nello stesso istante non possono essere servit due primi (o due secondi) a due clienti distinti, analogamente, nello stesso istante non possono effettuare il pagamento alla cassa due clienti distinti
	- è possibile che nello stesso istante venga servito un primo ad un cliente, un seconod ad un altro cliente, ed un terzo cliente stia pagando.
Si chiede di modellare il funzionamento di una tavola calda secondo il modello descritto sopra. Si chiede di completare il codice della classe TavolaCalda riportato in seguito in modo che rispetti le seguenti specifiche:
	- definire il metodo prendi_primo(int i) in modo che, se i corriponde al numero del prossimo cliente a cui va servito il primo piatto, venga tracciato il servizio con una stampa, altrimenti, metta il cliente in attesa del proprio turno. Definire il metodo prendi_secondo(int i) in modo analogo
	- definire il metodo paga(Cliente c) in moto tale che invochi il metodo pagamento della classe Cliente ed aggiorni il numero di clienti che hanno pagato.
	- definire il metodo void generaClienti(final int i) che crea ed avvia un thread t che genera ed attiva n clienti che vogliono mangiare nella tavola calda. Si chiede di implementare il thread t tramite una classe interna anonima. Questo metodo deve terminare senza attendere che siano stati creati ed attivati tutti i clienti.
	- definire il motodo void attendiClienti(final int n) che stampa la stringa finito di incassare solo didpo che n clienti hanno pagato alla cassa
	- è possibile aggiiungere campi dati o metodi alla classe TavolaCalda
Si chiede infine di giustificare brevemente perché:
	1. Non è possibile che siano serviti contemporaneamente due primi
	2. non è possibile che stiano pagando contemporaneamente due clienti
	3. è possibile che nello stesso estnate sia servito un prino, in secondo e che qualcuno stia pagando alla cassa.
*/


class Cliente extends Thread{
	private TavolaCalda mensa;
	private static int nC;
	private int numero;

	// l'ordine di arrivo dei clienti corrisponde al numero assegnato dal costruttore
	Cliente(TavolaCalda m) {mensa = m; numero = nC++;}

	public void run(){
		mensa.prendi_primo(numero);
		mensa.prendi_secondo(numero);
		mensa.paga(this);
	}

	// metodo invocato dal cassiere della tavola calda
	public void pagamento() {
		System.out.println(numero + " ha pagato");
	}
}

public class TavolaCalda {
	private int prox_primo_da_servire = 0;
	private int prox_secondo = 0;
	private int hanno_pagato = 0;

	private Object primo = new Object();
	private Object secondo = new Object();
	private Object cassa = new Object();


	// definire i seguenti metodi:
	void prendi_primo(int i) {
		try{
			synchronized(primo) {
				while(i != prox_primo_da_servire){
					primo.wait();
				}
				System.out.println("servito primo al numero "+ i);
				prox_primo_da_servire++;
				primo.notifyAll();
			}
		}catch(InterruptedException e) {e.printStackTrace();}
	}

	void prendi_secondo(int i) {
		try{
			synchronized(secondo) {
				while(i != prox_secondo){
					secondo.wait();
				}
				System.out.println("servito secondo al numero "+ i);
				prox_secondo++;
				secondo.notifyAll();
			}
		}catch(InterruptedException e) {e.printStackTrace();}
	}

	void paga(Cliente c) {
		synchronized(cassa) {
			c.pagamento();
			hanno_pagato++;
			cassa.notifyAll();
		}
	}
	
	private void generaClienti(final int n) {
		Thread t = new Thread()
        {
            public void run() {
                for (int i = 0; i < n; ++i) {
                    new Cliente(TavolaCalda.this).start();
                }
            }
        };
        t.start();
	}

	private void attendiClienti(final int n) {
		try{
			synchronized(cassa){
				while(hanno_pagato != n) {
					cassa.wait();
				}
				System.out.println("finito di incassare");
			}
		} catch(InterruptedException e) {e.printStackTrace();}
	}

	public static void main(String[] args) {
		TavolaCalda m = new TavolaCalda();
		m.generaClienti(100);
		m.attendiClienti(100);
	}
}

/*
Si chiede infine di giustificare brevemente perché:
	1. Non è possibile che siano serviti contemporaneamente due primi
		I primi vengono serviti prendendo possesso dell'oggetto Primo, che viene rilasciato quand'è stato servito. Poichè all'oggetto è associato un lock mutualmente esclusivo, non accadrà mai che siano serviti contemporanemente due primi.
	2. non è possibile che stiano pagando contemporaneamente due clienti:
		Per motivi analoghi, l'operazione di pagamento avviene in maniera sincronizzata sull'oggetto cassa, cui è associato un lock mutualmente esclusivo, impedendo quindi che possano effettuare il pagamento due clienti contemporaneamente.
	3. è possibile che nello stesso istnate sia servito un primo, in secondo e che qualcuno stia pagando alla cassa.
		Gli oggetti primo, secondo, cassa che governano il servizio del primo, secondo e pagamento alla cassa non dipoendono l'uno dall'altro, quindi è possibile che in contemporanea vengano eseguite le 3 operazioni di cui sopra.
*/