interface X{
	void f();
}

class B {
	public void g() {
		System.out.println("B.g() ");
	}
}

class C extends B implements X {
	public void f() {System.out.println("C.f() "); }
}

abstract class A extends B implements X {
	public void f() {System.out.println("A.f() ");}
	public void g() {System.out.println("A.g() ");}
	public abstract B f(B ref);
}

class D extends A {
	public static B st = new B();
	public void f() {System.out.println("D.f() ");}
	public B f(B ref) {
		if(ref instanceof A)
			return (D)ref;
		return st;
	}
}

public class Esercizio{
	public static void main(String[] args) {
	A a = new D();
	C c = (C)(a.f(a)); 
	}
}

/* 
Non compila / Compila ma provoca eccezione / cosa stampa?
	1. Non compila. A è astratta: non può essere istanziata
	2. A.g() A.g()
	3. D.f() D.f()
	4. A.g()
	5. Errore in compilazione
	6. Non compila
	7. Non compila
	8. Compila ma provoca eccezione (non posso convertire D in C)

// frammento 1
	B b = new A();
	X x = (A)b;
	x.f();
// frammento 2
	A a = new D();
	B b = a;
	b.g(); //A estende B -> stampa A.g()
	a.g(); //stampa A.g()
	a.f(b); //non stampa nulla (va su D)
// frammento 3
	B b = new D();
	A a1 = (A)b; //ok
	A a2 = (D)b; //ok
	a1.f(); //stampa D.f() perché più specifico
	a2.f(); //stampa D.f()
// frammento 4
	D d = new D();
	B b1 = d;
	B b2 = d.f(b1); //restituisce d -> b2=d
	b2.g(); //A.g()
// frammento 5
	B b1 = new B();
	A a = new D();
	B b2 = a.f(b1); //restituisce un new B, visto che B1 non è istanza di A
	X x = (D)b2; //Compila ma dà errore, visto che non posso ffare cast a B su un D
// frammento 6
	X x = new C();
	C c = (C)x; //ok
	B b = new D(); 
	c.f(b); //C non ha un f(B) non compila
// frammento 7
	X x = new C();
	B b = new B();
	x.f(b); //come sopra
// frammento 8
	A a = new D();
	C c = (C)(a.f(a));
*/