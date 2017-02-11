// le seguenti classi compilano correttaente
class Lista {
	private static class Nodo {
		Integer info;
		Nodo next;
		Nodo(Integer i, Nodo n) {
			if(i!=null) info = i;
			else info = new Integer(0);
			next = n;
		}
	}

	private Nodo first = null; //puntatore alla testa della lista
	public void insert(int x) {
		if(first == null) first = new Nodo(new Integer(x), null);
		else first = new Nodo(new Integer(x), first);
	}

	public void remove(int y) {
		remove(first,y);
	}

	private static void remove(Nodo n, int y) {
		if(n!=null)
			if(n.info.intValue() == y) n = n.next;
		else remove(n.next, y);
	}

	public void stampa() {
		stampa(first);
	}

	private static void stampa(Nodo n){
		if(n!=null) {
			System.out.print(n.info);
			stampa(n.next);
		}
	}
}

public class Ex{
	public static void main(String[] args) {
		Lista l = new Lista();
		l.insert(3); l.insert(5); l.insert(3); l.insert(6); l.remove(3); l.stampa(); // stampa: 6 3 5 3
		System.out.println(" UNO"); //stampa: UNO
		Lista l2 = l;
		l2.insert(0); l2.stampa(); // stampa: 0 6 3 5 3 
		System.out.println(" DUE"); // stampa: DUE
		l.stampa(); // stampa: 0 6 3 5 3
		System.out.print(" TRE"); // stampa: TRE

	}
}

/*
L'esecuzione di Ex non provoca eccezioni. Quali stampe provoca?
6 3 5 3 UNO
0 6 3 5 3 DUE
0 6 3 5 3 TRE
*/