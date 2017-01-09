import java.util.Vector;


class Passaggio{}

class Stop extends Thread{
	public String id;
	private Passaggio p;
	private Vector<Object> v = new Vector<Object>();

	Stop(String s, Passaggio p){
		id=s;
		this.p = p;
	}
	public synchronized boolean isEmpty() {return v.isEmpty();}
	public synchronized void add(Object o) {v.add(o); notify();}

	private synchronized void svuota() {
		while(!v.isEmpty()){
			Integer i = (Integer) v.remove(0);
			System.out.println("passa num " + i + " da " + id);
			try{Thread.sleep((int) Math.random() * 2);} catch(Exception e) {}
		}
	}

	public void run() {
		try{
			while(true){
				synchronized(this) {
					while(isEmpty())
						wait();
				}
				synchronized(p) {svuota();}
			}
		} catch(InterruptedException e) {}
	}
}

public class Avvio{
	public static void main(String[] args) {
		Passaggio p = new Passaggio();
		Stop c1 = new Stop("Coda di destra", p);
		Stop c2 = new Stop("Coda di sinistra", p);
		Stop[] ac = new Stop[2];
		ac[0] = c1; ac[1] = c2;

		c1.start();
		c2.start();

		for(int i=0; i<1000; i++){
			ac[i%2].add(new Integer(i));
			try{Thread.sleep((int)Math.random()*2);} catch(InterruptedException e) {}
		}
	}
}

/* 

	Gli oggetti Stop avranno: 
		-un vettore su cui avranno memorizzato tutte le auto in coda
		-se il passaggio è libero, se ne appropriano e fanno passare tutte le auto in coda
		-un passaggio condiviso da tutti gli oggetti
	Gli oggetti Passaggio avranno	
*/