public class A{
	public static void m(Object[] a, Object[] b) {
		Object t=a[0]; a[0]=b[0]; b[0]=t;
	}
	public static void main(String[] args) {
		String[] x={new String("a"); new String("b");}
		String[] y={new String("c"); new String("d");}
		m(x,y);
		System.out.println(x[0] + " " + x[1]);
		System.out.println(y[0] + " " + y[1]);
	}
}

public class B{
	public static void n(Object[] a, Object[] b) {
		Object[] t=a; a[0]=b[0]; b[0]=t[0];
	}
	public static void main(String[] args) {
		String[] x={new String("a"); new String("b");}
		String[] y={new String("c"); new String("d");}
		n(x,y);
		System.out.println(x[0] + " " + x[1]);
		System.out.println(y[0] + " " + y[1]);
	}
}


/*La classe A scambia i primi elementi degli array x,y: al termine dell'esecuzione del main stampa cb; ad*/
/*La classe B invece crea un temporaneo che riferisce all'array A: al termine dell'esecuzione al posto del primo elemento di A sta il primo elemento dell'array B;
Per concludere, nella prima posizione di B viene messo il primo elemento di t, che riferisce A, pertanto stampa cb; cd */