 class A{
	private float a=m(1);
	float m(float x) {System.out.println("A"); return x;}
}

 class B extends A {
	float m(int x) {System.out.println("B"); return x;}
}

 class C extends A{
	float m(float x) {System.out.println("C"); return x;}
}

public class D {
	public static void main(String[] args) {
		A x = new A(); //1
		A y = new B(); //2
		A z = new C(); //3
	}
}

/* 
	Le istruzioni contenute nel main compilano correttamente? Eseguono senza lanciare eccezioni? Quali stampe vengono prodotte in output?
		Il programma compila senza lanciare eccezioni, visto che il tipo statico delle variabili x,y,z è sempre compatibile con il tipo dinamico
		Le stampe prodotte in output sono le seguenti. Nel 2° caso viene preferito il metodo definito in A in quanto maggiormente specifico.
	1. stampa A;
	2. stampa A;
	3. stampa C;
*/
