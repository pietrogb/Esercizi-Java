
public class C{
	public static void main(String[] args) {
		T t1 = new T("t1");
		T t2 = new T("t2");
		t1.setAltro(t2); t2.setAltro(t1);
		t1.start(); t2.start();
	}
}

class T extends Thread{
	String nome;
	boolean libero = true;
	T altro;
	public void run() {
		try{
			synchronized(this) {
				System.out.println(nome + " UNO");
				libero = false;
				
				synchronized(altro){
					while(altro.libero == false)
						altro.wait();
				}
				libero = true;
				System.out.println(nome + " DUE");
				altro.notify();
			}
		} catch(InterruptedException e) {}
	}

	T(String s) {nome = s;}

	void setAltro(T t) {altro = t;}
	/*public void run() {
		// ...
	}*/
	

}

/*
Vero/Falso, giustificando la risposta

1. 
	public void run() {
		try{
			synchronized(this) {
				while(altro.libero == false){
					try{
						libero = true;
						wait();
						libero = false;
					} catch(InterruptedException e) {}
				}
				libero = false;
				synchronized(altro){
					altro.libero = false;
					System.out.println("sezione critica di " + nome);
					altro.libero = true;
					altro.notify();
				}
				libero = true;
			}
		} catch(InterruptedException e) {}
	}

	a. Il progamma può andare in deadlock
		Vero: il blocco per deadlock si ha se avviene il seguente interleaving:
			t1-synch(this) t2. -synch(this)
			t1-while()	t2.-while()
			t1-libero=false	t2-libero=false
			t1-synch(t2) t2-synch(t1)
	b. Se un thread si sospende con wait, non verrò mai risvegliato
		Falso: se uno dorme su this.wait() l'altro lo risveglia

2. 
	public void run() {
		try{
			synchronized(this) {
				libero = false;
				while(altro.libero == false){
					try{
						libero = true;
						wait();
						libero = false;
					} catch(InterruptedException e) {}
				}
				synchronized(altro){
					altro.libero = false;
					System.out.println("sezione critica di " + nome);
					altro.libero = true;
					altro.notify();
				}
				libero = true;
				notify();
			}
		} catch(InterruptedException e) {}
	}

	a. Il programa termina sempre producendo in output la stampa
		"sezione ciritica di t1 sezione critica di t2" oppure "sezione critica di t2 sezione critica di t1"
			Falso, può bloccarsi per starvation, ad esempio se avviene il seguente interleaving:
				t1-synch(this) t2. -synch(this)
				t1-libero=false	t2-libero=false
				t1-while()	t2.-while()
				t1-wait() t2-wait()

	b. Il programma non produce mai in output la stampa:
		"sezione ciritica di t1 sezione critica di t2" oppure "sezione critica di t2 sezione critica di t1"
			Falso, stampa quella stringa se sono eseguiti sequenzialmente

3. 
	public void run() {
		try{
			synchronized(this) {
				System.out.println(nome + " UNO");
				libero = false;
				
				synchronized(altro){
					while(altro.libero == false)
						altro.wait();
				}
				libero = true;
				System.out.println(nome + " DUE");
				altro.notify();
			}
		} catch(InterruptedException e) {}
	}
	
	a. Detto t il thread corrente, questo codice sospende l'altro thread fino a quando t non ha stampato la stringa DUE
		Falso, è t che si mette in wait
	b. Poichè tutte le istruzioni stanno in un blocco sincronizzato, di sicuro tra la stampa di t1 UNO e la stampa di t1 DUE non si inserisce mai la stampa di t2 UNO
		Falso
*/