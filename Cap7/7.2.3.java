public class SimpleThread extends Thread {
	private static int threadCount = 0;
	private int threadNumber = ++threadCount;
	private int countDown = 4;

	public SimpleThread() {
		System.out.println("Costruzione del thread " + threadNumber);
	}

	public void run() {

		while(countDown > 0) {
			System.out.println("Thread " + threadNumber + "(" + countDown + ")");
			countDown--;
			if(countDown == 0)
				System.out.println("Thread " + threadNumber + "ha finito");
		}
	}

	public static void main(String[] args) {
		for(int i = 0; i < 5; i++){
			SimpleThread st = new SimpleThread();
			st.start();
		}
		System.out.println("Tutti i thread sono vivi");
	}
}

// 7.2.3 - SimpleThread

/*
1. Quali stampe compaiono sempre nello stesso ordine? 
	Nello stesso ordine compaiono le stampe che tracciano la creazione dei thread

2. Far eseguire più volte il programma ed analizzare i diversi output cercando di capire in che modo è stata sequenzializzaa l'esecuzione dei sei thread concorrenti.
	Vengono avviati i sei tread inziando dal main, che a sua volta avvia 5 SimpleThread, i quali stampano parallelamente un countdown da 4 ad 1, per poi chiudersi stampando un sessaggio di conclusione del lavoro. Una volta che i thread sono stati attivati, il main traccia quest'azione con un messaggio sullo standard output.

3. È possibile che venga stampata la stringa "Tutti i thread sono vivi" prima della stringa "Costruzione del thread 5"? Se sì, come si può modificare il codice affincè si osservi quest'output?
	Spostando fuori dal ciclo for la costruzione del 5° thread
		for(int i = 0; i < 4; i++){
			SimpleThread st = new SimpleThread();
			st.start();
		}
		new SimpleThread().start();

4. È possibile che compaia la stringa "Tutti i thread sono vivi" prima della stringa "Thread 5(4)". Se sì, come si può modificare il codice affincè si osservi quest'output?
	Inserendo una sleep prima della start del thread, in questo modo:
		for(int i = 0; i < 5; i++){
			SimpleThread st = new SimpleThread();
			try{
				sleep(30);
			}catch(InterruptedException e) {}
			st.start();
		}

5. È possibile che venga vostruito ed eseguito interamente in thread 1, poi costruito ed eseguito interamente il thread 2, ecc..? Se sì, come si può modificare il codice affincè si osservi quest'output?
	Inserendo una sleep all'interno del ciclo for
		SimpleThread st = new SimpleThread();
		st.start();
		try{
			st.sleep(30);
		}catch(InterruptedException e) {}

6. Il thread che esegue il codice del costruttore è lo stesso che esegue il codice del metodo run? Come è possibile identificare il thread che esegue il codice del costruttore di questa classe? 
	È this, che in questo caso è uguale a thread.currentThread();
	Usando thread.currentThread possiamo stampare il nome del thread.

7. Che input si ottiene aggiungendo un'istruzione di sleep prima della stampa del countDown?
	Si possono avere i thread che procedono sostanzialmente in paralleleo nell'esecuzione del countdown.
*/