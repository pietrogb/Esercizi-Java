public class A{
	static int a;
	A(int v) {a=v;}
	public void show(){System.out.print(a);}
}

public class Test {
	public static void main(String[] args) {
		A a1=new A(1);
		A a2=new A(2);
		a1.show();
		a2.show();
	}
}
//Stampa 2 2 
/*Le variabili a1,a2 condividono il campo dati statico a, il che determina le stampe 2 2 eseguite dal metodo show() su di esse invocato, che va a stampare il
valore della variabile a, ch'esse condividono, in quanto statica*/