/*
Tracciare come cambiano i valori dei campi dati delle seguenti classi e le stampe prodotte dalla creazione di un oggetto della sottoclasse.
*/

class X{
	protected int x=3; 
	protected int y;
	X() {y=x; m();}
	public void m() {System.out.print("y=" + y);}
}

class Y extends X{
	protected int z=7;
	Y() {y=z*2; m();}
}

/*
La creazione dell'oggetto della sottoclasse provoca la variazione del calore del campo dati y:
mentre al momento dell'invocazione del costruttore della superclasse, y viene valorizzatoo allo stesso valore di x, provocando la stampa in output di 3,
con l'invocazione del cotruttore di Y, tale valore diventa pari a 14. 
*/