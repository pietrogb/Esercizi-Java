/*Cosa stampa il seguente programma?*/
class Z{
	int z;
	Z(int i) {z=i; System.out.print(" Z:" + i);}
}

class C{
	Z i = new Z(2), j= new Z(3);
	C() {i = new Z(4);}
}

public class D extends C{
	Z x = new Z(5), y= new Z(6);
	D() {super(); x = new Z(7);}
	public static void main(String[] args) {
		D d = new D();
		System.out.println("\n" + d.i.z + " " + d.j.z + " " + d.x.z + "  " + d.y.z);
	}
}

/*
Esegue il corpo del costuttore di D, invocando esplicitamente il costruttore della superclasse C, stampando Z(2) Z(3) Z(4)
Costruisce i campi dati propri della classe D, stampando Z(5) Z(6)
Finisce d'eseguire il corpo del costruttore della classe D, stampando Z(7)

Prodede infine con le stampe: d.i.z=4; d.j.z=3; d.x.z=7; d.y.z=6 : viene perciò stampato 4 3 7 6.
*/