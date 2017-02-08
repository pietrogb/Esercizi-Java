interface X{
	 void f();
}

interface Y{
	 void g();
}

class B implements Y{
	public void g() {System.out.println("B.g() ");}
	public void h() {System.out.println("B.h() ");}
}

class C extends B implements X {
	public void f() {System.out.println("C.f() "); }
}

class D extends B implements X {
	public void f() {System.out.println("D.f() "); }
	public void g() {System.out.println("D.g() "); }
	public void h(int n) {System.out.println("D.h(int) ");}
	public static void f(X r, Y s) {
		if(r instanceof Y ) {
			Y y = (Y)r;
			y.g();
		}
		if(s instanceof B)
			s.g();
	}
}


public class Esercizio{
	public static void main(String[] args) {

	}
}

/*
Non compila / Compila ma provoca eccezione / cosa stampa?
	1. Compila ma dà errore in esecuzione:  C e D non sono nella stessa gerarchia
	2. Non compila: C non può essere convertito a D
	3. Non compila: B non ha f()
	4. B.g() B.h()
	5. D.g() D.g() D.g() D.g()
	6. B.h() D.g() B.g()
	7. Errore in esecuzione: classCastException da D a C
	8. Non compila: non riesce a castare un C in un D
	9. C.f() D.f()

	//frammento 1
		X x = new D();
		Y y = new B();
		C c = (C)x;
		D.f(c, y);
	//frammento 2
		Y y = new D();
		Y z = new C();
		D d = (D)y;
		d.f();
		C c = (C)d;
		c.f();
	//frammento 3
		X x = new C();
		Y y = (Y)x;
		y.g();
		x.f();
		B b = (B)y;
		b.f();
	//frammento 4
		C c= new C();
		Y y = c;
		X x = (X)y;
		B b = (B)x;
		b.g();
		b.h();
	//frammento 5
		X x = new D();
		B b = (B)x;
		B b1 = new C();
		D.f(x,b);
		D.f(x, b1);
	//frammento 6
		X x = new D();
		Y y = new B();
		((B)y).h();
		D.f(x,y);
	//frammento 7
		X x = new D();
		B b = new C();
		B b1 = (B)x;
		((C)b1).f();
		X x1 = (C)b;
		x1.f();
	//frammento 8
		B b = new D();
		B b1 = new C();
		Y y = (D)b1;
		D.f((D)b,y);
	//frammento 9
		X x = new D();
		B b = new C();
		B b1 = (B)x;
		X x1 = (C)b;
		X x2 = (X)b1;
		x1.f();
		x2.f();
*/


/*4.4.12*/