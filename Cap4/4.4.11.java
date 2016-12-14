interface X{
	public void f();
}

interface Y{
	public int g();
}

class F implements X{
	public void f() {System.out.println("F.f() ");}
}

class C implements X {
	public void f() {System.out.println("C.f() "); }
}

class D extends C implements X {
	public int g() {System.out.println("D.g() "); return 1; }
}

class E extends C implements Y {
	public int g() {System.out.println("E.g() "); return 2; }
}

public class Esercizio{
	public static void main(String[] args) {


	}
}

/* 
Non compila / Compila ma provoca eccezione / cosa stampa?
	1. Non compia: non posso convertire X a C
	2. Non compila: X e Y sono tipi incompatibili
	3. Non compila: non posso convertire C ad Y
	4. Compila ma dà errore in esecuzione: non posso fare il cast di un E a D
	5. Non compila: D ed E non fanno parte della stessa gerarchia, così non èposso convertire D in E
	6. Non compila: D non implementa Y
	7. Non compila: X non implementa Y, così D non può essere convertito ad Y
	8. Come sopra

	//Frammento 1
		X x = new D();
		C c = x;
		x.f();
	//Frammento 2
		X x = new F();
		Y y = x;
		y.g();
	//Frammento 3
		X x = new C();
		Y y = (C)x;
		y.g();
	//Frammento 4
		Y y = new E();
		D d = (D)y;
		d.f(); 
		y.g();
	//Frammento 5
		Y y = new D();
		X x = (E)y;
		x.f();
		y.g();
	//Frammento 6
		Y y = new D();
		X x = (D)y;
		((E)x).g();
	//Frammento 7
		C c = new D();
		X x = (D)c;
		Y y = (D)x;
		y.g();
		x.f();
	//Frammento 8
		C c = new E();
		Y y = (D)c;
		((E)c).f();
		y.f();

*/