/*Si considerino le seguenti definizioni:*/

interface I {
	void m();
}

abstract class A implements I{
	public abstract void m();
}

class B extends A {
	private String s;
	public void m() {System.out.print(s + " ");}
	public B(String s) {this.s=s;}
}

class C extends B {
	static String t="pippo";
	void n() {System.out.print(t + " ");}
	public C() {super("paperino");}
}

class Esercizio{
	public static void Fun(I ref) {
		if(!(ref instanceof C) && (ref instanceof B))
			ref.m();
		else if(ref instanceof C)
			((C)ref).n();
	}
	public static void main(String[] args) {
		I r1 = new B("pluto"), r2 = new C();
		Fun(r1); Fun(r2);
	}
}

/*
Definire la funzione Fun(I ref) in modo che:
	- se il tipo dinamico di ref è un sottotipo di B ma non di C allora stampa il valore della stringa s campo dati (in B) dell'oggetto puntato da ref;
	- se il tipo dinamico di ref è un sottotipo di C allora stampa il valore della stringa t campo dati statico della classe C
	- altrimenti [tutti gli altri casi] non fa nulla

	->l'esecuzuone del precedente main deve provocare la stampa :
		pluto pippo
*/