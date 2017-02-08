/*Dire cosa stampa il seguente programma*/
class D{
	int i = 4;
	int j;
	D() {
		print("i = " + i + ", j= " + j);
		j=7;
	}
	static int x1 = print("static D.x1");
	static int print(String s){
		System.out.println(s);
		return 9;
	}
}

public class E extends D {
	int k = D.print("non static E.k");
	E() {D.print("k = " +k);}
	static int x2 = D.print("static E.x2");

	public static void main(String[] args) {
		D.print("invocazione di E()");
		E e = new E();
	}
}

/*
Il programma inizia costruendo i campi dati statici delle classi D,E: stampa:
	static D.x1 static E.x2
Esegue la prima istruzione del main, stampano qundi:
	invocazione di E()
La creazione dell'oggetto E provoca le seguenti stampe:
	i=4, j=0 non static E.k k=9
*/