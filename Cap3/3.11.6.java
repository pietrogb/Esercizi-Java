/*
Definire n metodo public static void m(Integer[], Integer[]) con il seguente comportamento: 
dati due array a1, a2 passati come parametri attuali ad m; se a1[0] e a2[0] sono due reference entrambi diversi da null che puntano a due oggetti Integer che contengono, rispettivamente, gl'interi x1, x2, allora dopo l'invocaizone m(a1,a2) i due oggetti Integer a1[0], a2[0] contengono, rispettivamente, gli interi x2, x2: è stato fatto uno swap dei due elementi.
Altrimenti, l'invocazione m(a1, a2) non provoca alcun effetto.
*/

public class C{
	public static void m(Integer[] a1, Integer[] a2) {
		if((a1!= null) && (a2!=null) && (a1[0] != null) && (a2[0]!= null)){
			Integer t = a1[0];
			a1[0] = a2[0];
			a2[0] = t;
		}
	}

	public static void main(String[] args) {
		Integer[] a1 = {new Integer(2), new Integer(9)};
		Integer[] a2 = {new Integer(4), new Integer(7)};
		Integer[] a3 = {null, new Integer(5)};
		Integer[] a4 = {new Integer(6), new Integer(1)};
		m(a1,a2);
		System.out.println(a1[0] + " " +  a2[0]); //stampa 4, 2
		m(a3,a4);
		System.out.println(a3[0] + " " +  a4[0]); //stampa null 6
		m(a4, null);
		System.out.println(a4[0]); //stampa 6
	}
} 
// 3.11.6