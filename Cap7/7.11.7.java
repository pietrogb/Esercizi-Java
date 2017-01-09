//7.11.7
public class Ex{
	protected class Stato{
		// i=0 può partire thread 1
		// i=1 può partire thread 2
		private int i=0;
		private final int range; //valore max di i: i in [0, ... , range-1]

		Stato(int r){
			range=r;
		}

		public synchronized int get() {return i;}

		public synchronized void next() {i=(i+1)%range;}
	}
	protected class T extends Thread{
		private String toWrite;
		private int number;

		public T(String st){
			toWrite = st;
			number=Thread_counter++; //assegna numero progressivo a thread
		}

		public void run(){
			while(true){
				// parte solo quando è il proprio turno
				synchronized(s){
					try{
						while(s.get() != number)
							s.wait();
					}catch(Exception e) {}
					System.out.print(toWrite);
					s.next();
					s.notifyAll();
				}
			}
		}
	}

	private Stato s;
	private int Thread_counter;
	public Ex(int n_thread){
		s = new Stato(n_thread);
	}
	public static void main(String[] args) {
		Ex e = new Ex(3);
		Ex.T t1 = e.new T("Essere");
		Ex.T t2 = e.new T(" o ");
		Ex.T t3 = e.new T("non essere? \n");

		Ex f = new Ex(2);
		Ex.T r1 = f.new T(" poter ");
		Ex.T r2 = f.new T(" non poter ");
		
		t1.start();
		t2.start();
		t3.start();

		r2.start(); r1.start();
	}
}

/*
	- L'output può iniziare con "non essere o essere? non poter poter"? 
		No
	- L'output può iniziare con "non poter poter essere o poter non essere"?
		NO
	- L'output può conteere la sottostringa "non poter essere o poter non essere"?
		Sì
	- L'output inzierà sicuramente con "essere o non essere? poter non poter"?
		No
	- L'output può iniziare con "essere o poter non essere"?
		Sì
	- L'output inizierà sicuramente con "essere o poter non poter non essere"?
		No
	- L'output può iniziare con "poter essere o non poter non essere? essere o poter non poter non essere?"
		Sì
	- L'output può contenere la sottostringa "poter essere o non poter essere?"
		NO
*/