// le seguenti classsi compilano corretamente. Cosa produce in output l'esecuzione di E?

 class C{
	protected static String s = "2"; //statica -> disponibile alle sottoclassi
	public C() {this(2);}
	public C(int n) {s = s+n; System.out.println(s.length());}
	public int m(C ref) {
		if (ref instanceof D) {return 2;}
		else {return 1;}
	}
}
 
 class D extends C {
	public int m(C ref) {
		if (ref instanceof C) {return super.m(ref);}
		else {return 4;}
	}
}

public class E extends D {
	public E() {
		Integer i = new Integer(s);
		System.out.println(i.intValue());
	}
	public int m(D ref) {return 5;}

	public static void main(String[] args) {
		C r = new C(5); //C::s=25; stampa 2. 
		C s = new D(); //C::s=252 stampa 3
		D t = new E(); //C::s=2522 stampa 4 2522
		E u = new E(); //C::s=25222 stampa 5 25222
		System.out.println(r.m(t) + " " + t.m(s) + " " + u.m(t)); 
			//t è instanceof D -> stampa 2
			//s è istanceof C -> chiama la R::m(ref) ref è instanceof D -> stampa 2
			//E ha un metodo m -> posso usare quello, stampa così 5
}

/*
L'esecuzione di E produce in output 
	2
	3
	4 2522
	5 25222
	2 2 5
*/