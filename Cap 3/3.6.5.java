/*Date le seguenti classi, dire quali stampe provoca l'istruzione B b=new B()*/

class A{
	private int x=m();
	A(){System.out.print("A()");}
	public int m(){System.out.println("A.m()"); return 1;}
}

class B extends A{
	private int y = m();
	B() {System.out.println("B()");}

	public int m() { //overriding
		System.out.println("B.m()");
		return 2;
	}
}

public class C{
	public static void main(String[] args) {
		B b=new B();
	}
}

/*
Il programma stampa: 
	B.m() A() B.m() B()
Questo perché viene utilizzato il metodo m() più specifico al momento della chiamata di a.m(), 
per dovuto al fatto che a fa parte dell'oggetto di classe B
*/