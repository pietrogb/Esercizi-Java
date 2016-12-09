public class C {
	public static void F(Object r,Object s){
		Object t=r; r=s; s=t;
	}

	public static void main(String[] args) {
		Integer x=new Integer(2), y=new Integer(3);
		F(x,y); System.out.print(x + " " + y);
		System.out.print(" ** ");
		String s = new String("pippo");
		F(x,s); System.out.print(x + " " + y + " " + s);
	}
}

/*

La sua esecuzione provoca una classCastException?
	No, tutti gli oggetti derivano da Object, quindi l'invocazione di f eseguita passando due interi non provoca il sollevamento d'eccezioni. 
	Inoltre le modifiche avvengolo su copie dei parametri dormali. Al termine dell'esecuzione non vi è traccia di modifiche ai valori di x, y, z.
Se no, quale stampa provoca in output?
	Stampa 2 3 ** 2 3 pippo
*/