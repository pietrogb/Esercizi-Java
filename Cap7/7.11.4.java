/*7.11.3.java
In un aereoporto una pista viene utilizzata sia dagli aerei in partenza che da quelli in arrivo.
L'ordine è regolato da un controllore di volo.
Gli aerei arrivano e partono secondo una tabella di orari giornalieri, ma si verificano frequentemente dei ritardi che devono esere opportunamente gestiti.
Si chiede di modellare il traffico di un aereoporto completando il codice della classe Controllore riportato in modo tale che:
	1. Siano definiti i metodi add_arrivi(Aereo a) e add_partenze(Aereo a) che aggiungono, ripettivamente alla coda di aerei in arrivo ed in partenza, un aereo che necessita di usare la pista
	2. Deve essere possibile che nello stesso istante un aereo stia transitando in pista, un altro si accodi in partenza ed un terzo si accodi in arrivo
	3. Definire il metodo void gestisci_arrivo() in modo che
		- se è presente un aereo nella coda.arrivi, allora viene fatto atterrare, cioè
			~viene prelevato dalla coda
			~gli viene assegnata la pista per l'atterraggio
			~viene invocato il suo metodo stampa
	4. Definire il metodo void gestisci_partenza() in modo analogo a gestisci_arrivo()
	5. Definire il metodo run() della classe TS in modo che attenda un aereo in ritardo o in partenza, lo faccia atterrare correttamente, senza provocare scontri in pista
	6. Sia possibile aggiungere campi dati o metodi nella classe Controllore
	7. Si chiede di giustificare perché:
		~ non si verifichino scontri di aerei (sia in ritardo che in orario)
		~ è possibile che nello stesso momento un aereo stia tranistando in pista, un altro s'accodi in partenza ed un terzo s'accodi in arrivo
*/

import java.util.Vector;

 class Aereo {
	private static int n;
	private int num;
	private String direzione;
	Aereo(String d) {num = n++; direzione=d;}

	// ATTENZIONE: invocare questo metodo quando l'aereo è già in pista.
	public void stampa() {
		System.out.println("aereo num " + num + " " + direzione);
	}
}

class GeneraArrivi extends Thread {
	private Controllore contr;
	GeneraArrivi(Controllore c) {contr = c;}
	public void run() {
		try{
			while(true) {
				contr.add_arrivi(new Aereo("in arrivo")); sleep(200);
			}
		} catch(InterruptedException e) {}
	}
}

class GeneraPartenze extends Thread {
	private Controllore contr;
	GeneraPartenze(Controllore c) {contr = c;}
	public void run() {
		try{
			while(true) {
				contr.add_arrivi(new Aereo("in partenza")); sleep(200);
			}
		} catch(InterruptedException e) {}
	}
}

public class Controllore extends Thread {
	private Vector<Aereo> coda_arrivi = new Vector<Aereo>();
	private Vector<Aereo> coda_partenze = new Vector<Aereo>();

	Object pista = new Object();

	// definire: aggiunge  alla coda di aerei in arrivo un aereo che necessita di usare la pista
	public void add_arrivi(Aereo a) {
		synchronized(coda_arrivi) {
			coda_arrivi.add(a);
			coda_arrivi.notifyAll();
		}
	}

	// definire: aggiunge alla coda di aerei in partenza, un aereo che necessita di usare la pista
	public void add_partenze(Aereo a) {
		synchronized(coda_partenze) {
			coda_partenze.add(a);
			coda_partenze.notifyAll();
		}
	}

	private char prox_transito() {
		// Non occorre definire questo metodo: consulta la tabella oraria e restituisce 'A' o 'P' indicando se assegnare la pista ad un aereo in arrivo o in partenza
		return 'A';
	}

	public void run() {
		while(true) {
			char c = prox_transito();
			if(c == 'A') gestisci_arrivo();
			else gestisci_partenza();
		}
	}

	// definire
	private void gestisci_arrivo() {
		Aereo a = null;
		synchronized(coda_arrivi){
			if(coda_arrivi.isEmpty() == true){
				new TS(coda_arrivi).start();
				return;
			}
			else
				a = coda_arrivi.remove(0);
		}
		synchronized(pista) { //ok anche con lock su this
			a.stampa();
		}
	}

	/*~viene prelevato dalla coda; gli viene assegnata la pista per l'atterraggio; viene invocato il suo metodo stampa*/

	// definire
	private void gestisci_partenza() {
		Aereo a = null;
		synchronized(coda_partenze){
			if(coda_partenze.isEmpty() == true){
				new TS(coda_partenze).start();
				return;
			}
			else
				a = coda_partenze.remove(0);
		}
		synchronized(pista) { //ok anche con lock su this
			a.stampa();
		}
	}

	private class TS extends Thread {
		Vector<Aereo> coda;
		TS(Vector<Aereo> s) {coda = s;}

		public void run() {
			try {
				Aereo a = null;
				synchronized(coda) {
					while(coda.isEmpty() == true) 
						coda.wait();
					a = coda.remove(0);
				}
				synchronized(pista) {//opp. Controllore.this
					a.stampa();
				}
			} catch(InterruptedException e) {}
		}
	}

	public static void main(String[] args) {
		Controllore contr = new Controllore();
		GeneraArrivi gA = new GeneraArrivi(contr);
		GeneraPartenze gP = new GeneraPartenze(contr);
		gA.start();
		gP.start();
		contr.start();
	}
}

/*
 Si chiede di giustificare perché:
	~ non si verifichino scontri di aerei (sia in ritardo che in orario)
	~ è possibile che nello stesso momento un aereo stia tranistando in pista, un altro s'accodi in partenza ed un terzo s'accodi in arrivo

Il motivo per cui nonsi verificano scontri è perché 
*/