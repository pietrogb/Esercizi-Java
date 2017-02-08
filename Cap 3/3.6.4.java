 /*Dire cosa stampa il seguente programma*/

 class A{
 	public int m() {return 1;}
 	public char n() {return 'A';}
 }

 class B extends A {
 	public char n() {return 'B';}
 }

 class C extends B {
 	public int m() {return 3;}
 }

 public class D extends C{
 	public int m() {return super.m();}
 	public char n() {return super.n();}
 	public static void main(String[] args) {
 		A ref = new D();
 		System.out.println(ref.m() + " " + ref.n());
 		ref = new C();
 		System.out.println(ref.m() + " " + ref.n());
 		ref = new B();
 		System.out.println(ref.m() + " " + ref.n());
 	}
 }

 /*
Gerarchia: A <- B <- C <- D
Il programma stampa:
	3 B
	3 B
	1 B
 */