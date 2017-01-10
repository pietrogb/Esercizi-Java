// Si considerino le seguenti definizioni:

class D {
	String x = "pippo";
	D(String s) {x=s;}
	public synchronized void set("String str") { x = str;}
	public synchronized String get() {return x;}
}

class A{
	private D s;
	A(String st) { s = new D(st);}
	A(D d){s=d}

	public synchronized void set(String str) { s.x = str;}
	public synchronized String get() { return s.x;}

	synchronized void f(int k) throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + " In f() s=" + s.x);
		for(int i=0; i<100000; i++);
		if(! get().equals(Thread.currentThread().getName()))
			set(Thread.currentThread().getName());
		for(int i=0; i<100000; i++) ;
		System.out.println(Thread.currentThread().getName() + "Fin f() s=" + s.x);
	}

	synchronized void g(int k) throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + " In g() s= " + s.x);
		for(int i=0; i<100000; i++);
		if(! s.get().equals(Thread.currentThread().getName()))
			s.set(Thread.currentThread().getName());
		for(int i=0; i<100000; i++) ;
		System.out.println(Thread.currentThread().getName() + "Fin g() s=" + s.x);
	}
}

class T1 extends Thread{
	private A a;
	T1(String nome, A a) {
		super(nome); 
		this.a=a;
	}

	public void run(){
		try{
			for(int i=0; i<10; i++) 
				a.f(i);
			C.flag++;
			while(C.flag < 2) ;
			for(int i=0; i<10; i++)
				a.g(i);
		} catch(InterruptedException e){}
		C.flag++;
	}
}

public class C{
	public static int flag = 0;

	public static void main(String[] args) {
		T1 t1 = new T1("Alice", new A(new String("Pluto")));
		T1 t2 = new T1("Bob", new A(new String("Pluto")));
		t1.start();
		t2.start();

		while(flag < 4) ;
		D d = new D("Topolino");
		T1 t3 = new T1("Carl", new A(d));
		T1 t4 = new T1("David", new A(d));
		t3.start();
		t4.start();
	}
}

/* Metodi synchronized
	First, it is not possible for two invocations of synchronized methods on the same object to interleave. When one thread is executing a synchronized method for an object, all other threads that invoke synchronized methods for the same object block (suspend execution) until the first thread is done with the object.
	Second, when a synchronized method exits, it automatically establishes a happens-before relationship with any subsequent invocation of a synchronized method for the same object. This guarantees that changes to the state of the object are visible to all threads.

	Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors: if an object is visible to more than one thread, all reads or writes to that object's variables are done through synchronized methods. (An important exception: final fields, which cannot be modified after the object is constructed, can be safely read through non-synchronized methods, once the object is constructed) This strategy is effective, but can present problems with liveness, as we'll see later in this lesson.
*/

/*
Per ciascna affermazione: V / F + risposta

1. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	Alice In f() s=Alice 
	Bob In f() s=Bob
	Alice Fin f() s=Alice
	Bob Fin f() s=Bob
		Vero: t1 e t2 lavorano su oggetti diversi

2. In ogni esecuzione del programma si verifica SEMPRE il seguente interleaving di stampe consecutive:
	Alice In f() s=Alice 
	Bob In f() s=Bob
	Alice Fin f() s=Alice
	Bob Fin f() s=Bob
		Falso: t1 potrebbe terminare prima di t2

3. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	Alice In f() s=Alice 
	Bob In g() s=Bob
	Alice Fin f() s=Alice
		Falso: non possono iniziare le g prima che siano conluse le f

4. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	Alice In g() s=Alice 
	Bob In g() s=Bob
	Bob Fin g() s=Bob
	Alice Fin g() s=Alice
	Carl In f() s=topolino
	David In f() s=topolino
		Sì, se il valore di flag è minore di 2 possono iniziare le g e se è minore di 4 possono partire t3 e t4.

5. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	Carl In f() s=topolino 
	Carl Fin f() s=Carl
	David In f() s=topolino
	David Fin f() s=David
		No, David inizierà con s=Carl

6. In ogni esecuzione del programma non compare mai la stampa della stringa:
	Alice Fin g() s=Bob
		Vero: essendo le g sincronizzate, t2 non potrà fare interferenza con t1

7. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	David In f() s=David
	Carl In f() s=Carl 
	David Fin f() s=David
	Carl Fin f() s=Carl
		No, dopo David In f() s=David, t4 non modifica s, quindi t3 non può iniziare con una s diversa

8. È possibile che in una qualche esecuzione del programma si verifichi il seguente interleaving di stampe consecutive:
	David In f() s=David
	Carl In f() s=David 
	David Fin f() s=David
	Carl Fin f() s=Carl
		Sì, è possibile

9. In ogni esecuzione del programma non compare mai la stampa della stringa:
	Carl In g() s=David
		Falso, con le g non cambia nulla

10. Se nessuno dei metodi della classe A fosse sincronizzato, il programma si comporterebbe in maniera diversa
		Falso, in ogni caso non ho mai due thread distinti che accedono allo steso oggetto di tipo A, la condivisione avviene invece su uno stesso oggetto di tipo D

11. I thread t1 e t2 terminano prima dei thread t3 e t4
		Sì, grazie ai flag della classe C

12. Il main thread è sempre l'ultimo a terminare
		Falso, in genere finisce prima di t3 e t4.
*/