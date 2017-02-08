/*
Si considerino le seguenti classi che compilano senza provocare errori.
*/
class A{
	public void print(String s) {System.out.print(s + " ");}
	public void m1() {print("A.m1"); m2(); }
	public void m2() {print("A.m2");}
}

class B extends A{
	public void m2() {print("B.m2");}
	public void m3() {print("B.m3"); }
}

class C extends A{
	public void m1() {print("C.m1"); }
	public void m2() {print("C.m2"); }
}

class D extends C{
	public void m1() {super.m1(); print("D.m1");}
	public void m3() {print("D.m3");  }
}

class Esercizio{
	public static void main(String[] args) {
		A ref1 = new B(); // T.S.: A; T.D.= B
		A ref2 = new D(); // T.S.: A; T.D.=D
		B ref3 = new B(); //T.S.: B; T.D.=B
		C ref4 = new C(); //T.S.:C; T.D.=C
		C ref5 = new D(); //T.S.: C; T.D.=D
		Object ref6 = new C(); //T.S.: Obj; T.D.=C
		/*
			Per ognuna delle seguenti istruzioni si dica se compila, se la sua esecuzione provoca un'eccezione o se l'istruzione compila correttamente senza lanciare eccezioni
		*/
 		ref1.m1(); //stampa A.m1, B.m2
		ref2.m1(); //stampa C.m1 D.m1
		ref4.m1(); //stampa C.m1 
		ref5.m1(); //C.m1 D.m1 
		ref6.m1(); //Non compila: Object non ha il metodo m1. 
		ref1.m2(); //B.m2 
		ref2.m2(); //C.m2 
		ref3.m2(); //B.m2 
		ref4.m2(); //C.m2 
		ref6.m2(); //non compila (object non ha m2) 
		ref5.m3(); //non compila (C non ha m3)
		((B)ref1).m3(); //La conversione ha successo. b.m3 
		((D)ref4).m3(); //Non compila: non posso fare cast al sottotipo D per un'oggetto C 
		((D)ref5).m3(); //D.m3 
		((B)ref2).m3(); //Compila, ma Solleva classCastException (B non sta nella gerarchia di D) 
		((C)ref2).m2(); //C.m2 
		((D)ref6).m2(); //C.m2
	}
}

