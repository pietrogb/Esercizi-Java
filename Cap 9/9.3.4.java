public class RemotoServer{
	public static void main(String[] args) throws Exception{
		X x = new O();
		Naming.rebind("pippo", x);

		/*1s: */ System.out.println(x.m()); //Stampa "vuoto" sul server

		//******Synch 1

		/*2s: */ C c = x.m();  //Fa una copia del campo dati privato d di x e lo stampa.
		/*3s: */ c.f(); //imposta a "Giallo" la stringa
		/*4s: */ System.out.println(c); //Stampa "Giallo" sul server
		/*5s: */ System.out.println(x.m()); //Stampa "vuoto" sul server

		//******Synch 2
		/*6s: */ System.out.println(x.n()); //Non compila: il tipo statico X non ha n()
		/*7s: */ System.out.println( ((O)x).n()); //Il cast ha successo, stampa "Rosso"
		/*8s: */ System.out.println( (X)(x.m()) ); //D non è sottotipo di X, di conseguenza il cast fallisce. ClassCastException
		/*9s: */ System.out.println( (O)(x.m() )); //D non appartiene alla stessa gerarchia di O. Non compila
		/*10s: */ System.out.println( ((X)x).n()); //Non compila, è identica a 6s

		/*11s: */ D dd=(D)(x.m); //compila
		/*12s: */ dd.h(); //dd.s="Magenta"
		/*13s: */ System.out.println( (C)dd); //compila e stampa "Magenta"
	}
}

public class RemotoClient{
	public static void main(String[] args) throws Exception{
		X x = Naming.lookup("pippo");

		/*1c: */ C c=x.m(); //Rinomina a c il riferimento a d ritornato dal metodo m() -> rinomina una copia serializzata
		/*2c: */  System.out.println(c); //stampa "vuoto" sul client
		
		//******Synch 1
		
		/*3c: */ System.out.println(x.m()); //stampa "vuoto" sul client
		/*4c: */ System.out.println(c); //stampa "vuoto" sul client
		
		//******Synch 2

		/*5c: */ C c1= new C(); //Ok, crea un oggetto locale
		/*6c: */ c1.f(); //c1.s="giallo"
		/*7c: */ System.out.println(c1); //stampa "Giallo sul client"
		/*8c: */ C.E.foo1(c1); //chiama il metodo foo1 della classe interna statica a C. c1.s="FOO"
		/*9c: */ System.out.println(c1); //Stampa FOO
		/*10c: */ C.E.foo2(c1, new  C()); //Ha come risultato che c1.s="vuoto" c1=new C().
		/*11c: */ System.out.println(c1); //Stampa "Vuoto"

		/*12c: */ x.k(c1); //Stampa Vuoto sul Server(12c1), stampa Verde sul server(12c2) e stampa 
		/*13c: */ System.out.println(c1); //Stampa Vuoto sul Client
	}
}

/*
Indicare le istruzioni che generano errori di compilazione
Indicare le istruzioni che generano errori a run-time
Quali stampe generano le istruzioni nella macchina server e nella macchina client. Indentificare per ogni stampa l'istruzione che l'ha generata.

Le stampe sul server possono essere:
1s 4s 5s 12c1 12c2  7s 13s
1s 4s 5s 7s 12c1 12c2  13s
1s 4s 5s 7s 13s 12c1 12c2 
1s 4s 5s 7s 12c1 13s 12c2 
1s 4s 5s 12c1 7s 12c2  13s
1s 4s 5s 12c1 7s 12c2 13s 

Le stampe nel client sono:
Vuoto(2c) Vuoto(3c) Vuoto(4c) Giallo(7c) FOO(9c) Vuoto(11c) Vuoto(13c)


*/