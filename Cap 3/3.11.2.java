public class Esercizio {
	public static String G(Object s) {
		if(s instanceof String) return s + "***";
		return "XXX";
	}
	public static void Fun(Object[] a) {
		if(a instanceof String[]) {a[0] = a[1]; a[1]=G(a[1]);}
		else if(a[0] instanceof String) {a[1] = a[0]; a[0]=G(a[0]);}
	}
	public static void main(String[] args) {
		// 1
		Object[] x={new String("pippo"), new Integer(5)};
		Fun(x); System.out.print(x[0] + " " + x[1]);
		// 2
		String[] y = {new String("pluto"), new String("topolino")};
		Fun(y); System.out.print(y[0] + " " + y[1]); 
		// 3
		Integer[] z = {new Integer(8), new Integer(9)};
		Fun(z); System.out.print(z[0] + " " + z[1]); 
		// 4
		Object[] w = {new Integer(3), new String("paperino")};
		Fun(w); System.out.print(w[0] + " " + w[1]);
	}
}

/*
Se ogni frammento segunete è il dice di un metodo main della seguente classe.
Il main non compila, provoca un'eccezione o compila e produce un'output?
	1. Compila; fun(pippo, 5) provoca fa fallire il primo test, il ramo else rende a[1] = pippo; a[0] visto che è una string viene trasformata in pippo ***
		stampa pippo *** pippo
	2. Compila, la variabile passata a Fun è una stringa, quindi entra nel ramo if, rendendo a={topolino, topolino}. a[1] viene reso uguale a topolino ***
	stampa topolino topolino ***
	3. Compila, non esegue modifiche sulla variabile z
		stampa 8 9
	4. Compila, non fa modifiche alla variabile w
		Stampa 3 paperino
*/