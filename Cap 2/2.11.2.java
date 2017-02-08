public class Z{
	public Z(int x) {System.out.print("Z(" + x + ") ");}
}

public class C{
	static {System.out. print("BloccoC");}
	static Z z = new Z(1);
}

public class D{
	static  Z z = new Z(2);
	static {System.out. print("BloccoD");}
}

public class E{
	public static void main(String[] args) {
		System.out. print("UNO");
		C c = new C();
		System.out. print("DUE");
		D d = new D;
		System.out. print("TRE");
	}
}

/* L'esecuzione di E provoca la stampa di UNO, stampato BloccoC (il metodo è dichiarato statico, sicché viene eseguito una sola volta
nel programma); Z(1) viene stampato DUE Z(2) BloccoD, TRE*/