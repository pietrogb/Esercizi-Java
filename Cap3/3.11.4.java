/*
Definire una classe C ed una sua sottoclasse D tali che:
	[fatto] 1. C contiene un campo dati staico privato s ed in campo dati non statico privato x, entrambi di tipo int; 
	[fatto] 2. in C è definito un costruttore che inizializza il campo x 
	[fatto] 3. D contiene un campo dati non statico privato y di tipo int
	[fatto] 4. in D è definito un costruttore che inizializza il campo y con il valore del campo s di C
	[fatto]5. in D è definito un metodo statico static int m(C ref) che restituisce la somma dei valori dei campi x e y dell'argomento ref se ref ha tipo dinamico che è sottotipo di D, altrimenti restituisce il valore del campo dati statico s
	6. in D è definito il metodo main() contentente istruzioni che esemplificano l'uso dei costruttori in C e D, e l'uso del metodo m().
*/

class C{
	private static int s;
	private int x;
	public C(int n) {x=n;}
	protected int getS() {return s;}
	protected int getX() {return x;}
}

class D extends C{
	private int y;
	public D() { y = getS(); }
	static int m(C ref) {
		if (ref instanceof D) {return y + getX();}
		else {return getS();}
	}
}