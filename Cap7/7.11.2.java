/*Un tour operator propone dei viaggi in giro per il mondo che per partire richiedono un numero minimo di partecipanti.
Il seguente programma crea un viaggio in Mamibia che richiede un monimo di 4 partecipanti.
Il programma inoltre crea ed avvia 6 percone che voglino iscriversi al viaggio in Namibia e dopo aver atteso un po' di tempo chiude le iscrizioni al viaggio*/

public class Agenzia{
	public static void main(String[] args) {
		
		Viaggio v = new Viaggio("Namibia", 4);

		Persona p1 = new Persona("Alice", v);
		Persona p2 = new Persona("Bob", v);
		Persona p3 = new Persona("Carl", v);
		Persona p4 = new Persona("Doug", v);
		Persona p5 = new Persona("Eric", v);
		Persona p6 = new Persona("Frank", v);

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		
		try{
			Thread.sleep(200);
		} catch(Exception e) {}

		v.chiudi();
	}
}

class Viaggio{
	private bool open = true;
	private String dest;
	private int nMin;
	private Persona[] list;
	public Viaggio(String dest, int n) {
		this.dest=dest;
		nMin = n;
	}

	public boolean prenota(Persona p) {
		if(open){
			list.add(p);
			if(list.length >= nMin) {
				// sveglia le persone
				for(Persona i: list)
					p.notify();
			}
		}
	}

	public void chiudi() {
		open = false;
		if(list.length >= nMin){
			System.out.println("Partecipano al viaggio: ")
			for(Persona i: list){
				System.out.println("i.getName()");
				i.willPartecipate();
				i.notify();
			}
		}
		else{
			System.out.println("Il viaggio in " + dest + " è stato annullato");
		}
	}

	public String getDest() {return dest;}
	public boolean isOpen() {return open;}
}

class Persone extends Thread {
	private String name;
	private Viaggio viaggio;
	private boolean partecipa=false;
	public Persona(String n, Viaggio v){
		name=n;
		viaggio = v;
	}
	public void run(){
		partecipa = v.prenota(this);
		if(partecipa) {
			System.out.println(name + "prenotazione per " v.getDest() + " confermata");
		}
		else{
			while(v.isOpen())
				wait();
			if (partecipa) {
				System.out.println(name + "prenotazione per " v.getDest() + " confermata");
			}
		}
	}
	public String getName() {return name;}
	public willPartecipate() {partecipa = true;}
}

/*
Classe Viaggio:
	- Contiene metodo boolean prenota(Persona p) che aggiunge una persona all'elenco di iscritti al viaggio e restituisce un valore booleano che dice se il viaggio è confermato o meno.
	Quando la prenotazione della persona p permette il numero minimo di viaggiatori il metodo prenota deve avvisare i viaggiatori precendemente prenotati che il viaggio è ora confermato.
		In questo caso il metodo prenota deve terminare (restituendo true alla persona p) senza attendere che tutti gli altri viaggiatori siano stati effettivamente avvisati
	- Contiene il metodo void chiudi() che viene invocato dal main per chiudere le prenotazioni del viaggio. Se al momento della chiusura il viaggio risulta confermato, stampa l'elenco dei partecipanti al viaggio, altrimenti deve avvisare le persone iscritte che ilviaggio è annullato.
Classe Persona:
	- Thread il cui metodo run inizia invocando il metodo prenota sul viaffio desiderato. Se questo metodo restituisce true, il metodo termina con una stampa che indica la conferma della partenza. Se la prenotazione restituisce false, la persona si mette in attesa che il viaggio venga confermato o che venga annullato per mancanza di iscritti
È possibile aggiungere campi e metodi alle classi Viaggio e Persona.
*/