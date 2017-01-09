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

/*
Per ciascna affermazione: V / F + risposta

1. È possibile che in una qualche esecuzione del programma si verigichi il seguente interleaving di stampe consecutive:
	Alice In f() s=Alice 
	Bob In f() s=Bob
	Alice Fin f() s=Alice
	Bob Fin f() s=Bob
*/