/*
Buffer limitato con due diverse condizioni di attesa
Nell'esempio precedente, il thread produttore si mette in attesa finchè il butter è pieno, metre il thread consumatore si mette in attesa finchè il buffer è vuoto. I due thread si mettono cioè in attesa sullo stesso oggetto monitor (il buffer) in attesa di due condizioni diverse.
Ciò non rappresenta un problema perchè in ogni momento c'è sempre un thread attivo in grado di risvegliare l'altro thread eventualmente sospeso.

Per chiarezza piò essere utile associare ad uno stesso oggetto condiviso più liste di attesa, una per ogni condizione logica.
A partire dalla distribuzione Java5.0 sono disponibili nel pacchetto java.util.concurrent.locks degli oggetti condizione.
Ogni oggetto cond di tipo interfaccia Condition deve essere sccociato ad un oggetto di tipo Lock, e va usato mediante le ustruzioni: 
	- il comando cond.await() mette il thread corrente nella lista di attesa associata alla condizione cond;
	- il comando cond.signalAll() e cond.signal() risvegliano i (un) thread in  attesa sulla condizione cond.
L'uso di due distinti oggetti condizione associati allo stesso lock è illustrato dal seguente codice, che riprende l'esempio del buffer limitato.
*/

include java.util.concurrent.locks.*;

class BoundedBuffer {
	final Object[] items = new Object[100];
	int count, putptr, takeptr;

	final Lock l = new ReentrantLock(); //Un lock esplicito
	final Condition notFull = l.newCondition();
	final Condition notEmpty = l.newCondition();

	public void put(Object o) throws InterruptedException {
		l.lock();
		try {
			while(count==items.length){
				System.out.print("attendo: buffer Pieno ");
				notFull.await();
			}

			items[putptr]=o;  putptr++;
			if(putptr == items.length) putptr=0;
			count++;
			notEmpty.signalAll();
		}
		finally {
			l.unlock();
		}
	}

	public Object take() throws InterruptedException {
		l.lock();
		try {
			while(count == 0) {
				System.out.print("attendo: buffer Vuoto ");
				notEmpty.await();
			}

			Object o = items[takeptr];
			takeptr++;
			if(takeptr == items.length) takeptr=0;
			count--;
			notFull.signalAll();
			return o;
		}
		finally
			l.unlock();
	}
}

class Produttore extends Thread{
	BoundedBuffer b;
	Produttore(BoundedBuffer buff, String s) {super(s); b=buff;}
	public void run() {
		int i=0;
		while (i<200) {
			b.l.lock();
			try {
				b.put("Pippo");
				System.out.println("messo numero " + i);
				i++;
			} catch(InterruptedException e) {}
			finally
				b.l.unlock();
		}
	}
}

class Consumatore extends Thread {
	BoundedBuffer b;
	Consumatore(BoundedBuffer b, String s) {super(s); b=buff;}
	public void run() {
		int i=0;
		while(i<200){
			b.l.lock();
			try{
				System.out.println("preso "+b.take()+" numero "+i);
				i++;
			} catch(InterruptedException e) {}
			finally
				b.l.unlock();
		}
	}
}

public class Buffer {
	public static void main(String[] args) {
		BoundedBuffer buff = new BoundedBuffer();
		Produttore prod = new Produttore(buff);
		Consumatore cons = new Consumatore(buff);
		prod.start(); cons.start();
	}
}
// 7.8.3