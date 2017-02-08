/*
	Definire una classe C t.c.:
		1. C compila correttamente
		2. In C sia definito il metodo main()
		3. L'esecuzione java C 2 provoca una NullPointerException;
		4. L'esecuzione java C 2 provoca una ClassCastException;
		5. In C non compaiano le keyword null e throw.
*/

class A{
	private String x=null;
	public int getLgth() {return x.length();}
}

class B extends A{ } 

class D extends B { }


public class C{
	public static void main(String[] args) {
		A a  = new A();
		B b = new B();
		A[] arr = {};
		int i=Integer.parseInt(args[0]);
		if(i == 1) {
			A n = new A();
			System.out.println(n.getLgth());
		}
		if(i == 2){
			D d = (D)a;
		}
	}
}