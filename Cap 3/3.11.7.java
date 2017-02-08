/*
La classe compula correttamente ed esegue senza lanciare eccezioni. QUali stampe provoca la sua esecuzione?
*/

public class Ex{
	private Object[] a = new Object[3];
	public void insert(Object ref, int i) {a[i] = ref;}
	public void F() { Object t = a[0]; a[0] = a[2]; a[2] = t;} //scambia il primo col terzo
	public void G() {Object[] t = a; t[0] = new String("pluto");} //mette sul primo la scritta pluto
	public void print() {
		for(int i = 0; i<3; i++) System.out.print(a[i] + " ");
	}
	public static void main(String[] args) {
		Ex e = new Ex(); //e=[ , , ]
		e.insert(new Integer(3), 2);  //e=[ , , 3]
		e.insert(new String("pippo"),0); //e=[pippo, , 3]
		e.print(); System.out.println(" **1");
		e.F(); e.print(); System.out.println(" **2");  //e=[3 , , pippo]
		Ex f = e; f.insert(new Boolean(true), 1); f.F();  //e=f=[3 ,true , pippo] -> f.F() -> e=f=[pippo, true, 3]
		f.print(); System.out.println(" **3"); 
		e.print(); System.out.println(" **4");
		e.G(); e.print(); System.out.println(" **5");
	}
}

/*
stampa:
	pippo null 3 **1
	3  null pippo **2
	pippo true 3 **3
	pippo true 3 **4
	pluto true 3 **5
*/