/*Cosa stampa?*/

abstract class A {
	public abstract void m();
	public A() {
		System.out.println("In A() prima di m()");
		m(); //chiamata polimorfa
		System.out.println("In A() dopo m()");
	}
}

class B extends A {
	int k = 1;
	public void m() {
		System.out.println("B.m(), k= " + k);
	}
	public B(int x) {
		k=x; System.out.println("B.B(), k= " + k);
	}
}

public class C {
	public static void main(String[] args) {
		new B(3);
	}
}

/* 
Il programma stampa:
	In A() prima di m()
	B.m() k=0
	In A() dopo m()
	B.B() k=3
*/