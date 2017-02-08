/*Cosa stampa il seguente programma?*/

abstract class A{
	public abstract void m();
}

class B extends A {
	public void m() { System.out.println("B.m()");}
}

class C extends A{
	public void m() { System.out.println("C.m()");}
}

class D extends A{
	public void m() { System.out.println("D.m()");}
}

public class E {
	//per f() e g() nolla cambia se la gerarchia viene estesa con nuove sottoclassi concrete di A
	static void f(A ref) { ref.m();}
	static void g(A[] a) {
		for(int i = 0; i<a.length; i++) 
			f(a[i]);
	}

	public static void main(String[] args) {
		A[] a = new A[4];
		a[0] = new B();
		a[1] = new C();
		a[2] = new D();
		a[3] = new C();
		g(a);
	}
}

/*
A classe astratta che possiede un metodo m che viene ridefinito nelle varie classi.
stampa: 
	B.m() C.m() D.m() C.m()
*/