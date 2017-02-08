/*Definire il metodo m. Usando tale metodo completare la definizione del metodo m il modo tale che l'esecuzione del main provochi la stampa di "pluto", "paperino"*/

interface X{
	void fun(int i);
}

interface Y extends X{
	void fun();
}


public class A {
	public void g(Y y) {
		System.out.println("pluto");
		y.fun();
	}

	public X m(Y y) {System.out.println("paperino"); return y;}

	public void f() {
		class B implements Y{
			public void fun() {};
			public void fun(int i) {};
		}
		B b = new B();
		g(b);
		m(b);
	} //f deve chiamare il metodo m

	public static void main(String[] args) {
		A a = new A();
		a.f();
	}
}
// 