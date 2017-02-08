
/*La classe AtomicInteger incapsula un intero ed offre i metodi set e get per accedere a questo intero in maniera atomica.
In altri termini, questa classe è implementata in modo tale da garantire che le chiamate concorrenti dei metodi set e get su un oggetto di tipo AtomicInteger non interferiscano tra loro.
	la classe NumberRange definisce un intervallo di interi[lowe, ..., upper], assumento l'invariante lowe<=upper. Data la defininzione seguente, nonostante l'uso del tipo AtomicInteger, gl'oggetti di tipo NumberRange non sono thread-safe, cioè il loro stato non è protetto in caso d'accessi concorrenti
*/
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

class NumberRange { //invariant: lower <= upper
	Lock lock = new ReentrantLock();
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);

	public void setLower(int i) throws Exception {
		lock.lock();
		try{ 
			if(i<upper.get()) throw new Exception();
			lower.set(i);
		} catch(InterruptedException e) {}
		finally {lock.unlock();}
	}

	public void setUpper(int i) throws Exception{
		try{ 
			if(i<lower.get()) throw new Exception();
			upper.set(i);
		} catch(InterruptedException e) {}
		finally {lock.unlock();}
	}
	public boolean inInRange(int i) {
		return (i >= lower.get() && i <= upper.get());
	}
}

class MyThread extends Thread{
	private int num;
	private NumberRange nr;
	MyThread(NumberRange nur, int i){
		nr = nur;
		num = i;
	}
	public void run() {
		try{
			nr.setUpper(num);
		} catch(InterruptedException e) {}
		catch(Exception e) {System.err.println("Eccezione!!");}
		System.out.println(num + " in in range? " + nr.inInRange(num));
	}
}

public class Test{
	public static void main(String[] args) {
		NumberRange nr = new NumberRange();
		for(int i = 0; i < 100; i++) {
			MyThread t = new MyThread(nr, i);
			t.start();
			System.out.println("MAIN" + i + "in in range? " + nr.inInRange(i));
		}
	}
}


// 7.11.1



/*
A. Scrivere un programma che usa un oggetto di tipo NumberRange ed illustrare un'esecuzione di questo programma che:
	1. Non solleva alcuna eccezione
	2. Lascia l'oggetto in uno stato inconsistente

B. Modificare la classe NumberRange in modo che il programma precedente risulti corretto
*/