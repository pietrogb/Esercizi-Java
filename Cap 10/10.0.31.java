class MinoreZero extends Exception {}

public class Ex {
	public static void fattoriale(Integer ref) throws MinoreZero {
		if(ref == null) throw new NullPointerException();
		int x = ref.intValue();
		if(x<0) throw new MinoreZero();
		if(x==0) {ref = new Integer(1); return;}
		else {
			ref= new Integer(x-1); 
			fattoriale(ref); 
			int y = ref.intValue(); 
			ref = new Integer(y*x); 
			return;
		}
	}
	public static void main(String[] args) {
		try{
			Integer i = new Integer(3); //crea l'integer 
			fattoriale(i); // non stampa nulla
			System.out.print(i + "UNO"); //stampa 3uno
			Integer k = new Integer(-2); //
			fattoriale(k); //lancia un MinoreZero
			System.out.print(k + "DUE"); //esce prima
		} 
		catch(RuntimeException e) {System.out.print("TRE");} //
		catch(MinoreZero e) {System.out.print("QUATTRO");} // stampa quattro
		catch(Exception e) {System.out.print("CINQUE");} //
		try {
			Integer j = null; //
			fattoriale(j); //lancia un nullpointerexception
			System.out.print(j + "SEI"); //
		}
		catch(RuntimeException e) {System.out.print("SETTE");} //stampa sette
		catch(Exception e) {System.out.print("OTTO");} //
	}
}

/*
Viene utilizzato il primo gestore delle eccezioni, e non i successivi
Le precedenti clasi compilano correttamente e l'esecuzione del main() di Ec non provoca eccexioni che vongolo rilanciate dal main() alla JVM.
Quali stampe provoca l'esecuzione di Ex?
3 UNO QUATTRO SETTE
*/