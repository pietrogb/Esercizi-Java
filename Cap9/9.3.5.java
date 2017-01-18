
public class RemoteServer{

    public static void main(String[] a) throws Exception{

        IRimpl rem = new IRimpl();
        Naming.rebind("pippo", rem); //Collega il nome "Pippo" all'oggetto remoto rem
        //rem->█->a->"giallo"
        IR rr=rem.n(); //oggetto temporaneo. Ne crea uno di nuovo
        	//rr.s="giallo"
        rr.n("AZZURRO"); //Imposta la stringa di rr ad "Azzurro"
        IS y=rr.k(); //y è un riferimento di tipo A che estende ISimpl, quindi ok
        rr.f(y); //Prova a convertire y di tipo A. Il cast ha successo. Stampa "AA"
        System.out.println(rem.m()); //Stampa "Giallo"
        System.out.println(rr.m()); //Stampa "Azzurro" o "BLU"
    }

}

public class RemoteClient extends ISimpl{

    public static void main(String[] a) throws Exception{

        IR[] v = new IR[3]; //Array di riferimenti ad IR

        v[0]=(IR) Naming.lookup("pippo"); //mette il riferimento all'oggetto remoto rem di tipo IRImpl
        v[1]=v[0].n("BLU"); //mette a "BLU" la stringa di Rem. v[1] è un altro riferimento a rem
        v[2]=v[0].n(); //oggetto locale, la cui stringa è ottenuta come copia di quella di rem, quindi uguale a BLU

        v[1].n("ROSSO"); //imposta a "Rosso" la stringa di rem

        System.out.println(v[0].m()); //Stampa Rosso
        System.out.println(v[1].m()); // Stampa Rosso
        System.out.println(v[2].m()); // Stampa BLU

        IS x=new ISimpl(); //Nuovo oggetto locale
        v[2].f(x); // prova a convertire un ISImpl in A, ma la conversione fallisce (A è sottotipo), così stampa BB sul server

        System.out.println(v[0].m()); //Stampa Rosso
        System.out.println(v[1].m()); //Stampa Rosso
        System.out.println(v[2].m()); //Stampa BLU: non è cambiato nulla
    }
}

/*
Le stampe possibili sono date dalla sequenza AA, Rosso/Giallo/Blu, Azzurro, intervallate dalla stampa provocata dal client, BB.
Di seguito le sequenze possibili.
1.	AA
	Uno dei 3 colori
	Azzurro
	BB

2. 	AA
	Uno dei 3 colori
	BB
	Azzurro

3.	AA
	BB
	Uno dei 3 colori
	Azzurro

4.	BB
	AA
	Uno dei 3 colori
	Azzurro

Stampe che appaiono sul server:
1
1
1
	2
1
1
1
		3
1
		3
		3
		3
		3
	2
		3

*/