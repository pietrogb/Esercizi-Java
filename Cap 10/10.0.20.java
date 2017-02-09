// Si considerino le seguenti definizioni:

class E1 extends Exception {}
class E2 extends Exception {}
class E3 extends E1 {}
class Pieno extends Exception {}

interface Lista {
	boolean isEmpty();
}

class Vettore implements Lista {
	private int[] a = new int[5];
	private int dim = 0;

	public boolean isEmpty() {return dim==0;}

	public void add() throws Pieno {
		if(dim<5) {
			a[dim] = dim;
			dim++;
		}
		else throw Pieno();
	}
}

class ListaVuota implements Lista {
	public boolean isEmpty() {
		return true;
	}
}

class C{
	void m(Lista l) throws E1, E2, E3 Pieno{
		if (l istanceof Vettore) {
			try{
				l.add();
			} catch (Exception e) {
				throw new E3();
			}
			throw new E2();
		}
		else{
			throw new E1();
		}
	}

	public static void main(String[] args) {
		Lista l = new Lista(); // o new Vettore, etc
		try{
			l.add();
		} catch(E1 e) {
			if(e istanceof E3)
				System.out.println("E3");
			else 
				System.out.println("E1");
		} catch(E2 ee) {
			System.out.println("E2");
		}
	}
}

/*
Scrivere una classe C che contiene: 
 - un metodo statico m che prende un parametro l di tipo Lista e, se l non è di tipo Vettore solleva un'eccezione di tipo E1, altrimenti gli aggiunge un elemento tramite il metodo add.
 Se la chiamata di add ha sollevato un'eccezione, m deve rilanciare un'eccezione di tipo E3, altrimenti lancia un'eccezione di tipo E2.
 - il metodo main che chiama il metodo m all'interno di un blocco try-catch CON SOLO DUE BLOCCHI CATCH  in modo tale che se m solleva un'eccezuone di tipo, rispettivamente, E1, E2, E3, venga stampata in output la corrispondente stringa "E1", "E2", "E3".

 instanceof funziona anche con le eccezioni!!
*/