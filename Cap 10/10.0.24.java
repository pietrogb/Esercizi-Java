// 10.0.24
interface Iteratore {
	boolean end(); //ritorna true sse il è l'iteratore past-the-end
	Object current(); //ritorna l'oggetto puntato
	void next(); //sposta l'iteratore all'elemento successivo
}

class SequenzaNulla extends Exception{}

class Sequenza {
	private Object[] a; //array di object
	private int next = 0; //indice dell'array per il prossimo inserimento
	public Sequenza(int dim) {a = new Object[dim];}
	public void add(Object x) {
		if(next < a.length) {
			a[next] = x; next++;
		}
		else {
			Object[] t = new Object[a.length + 1];
			for(int i=0; i<a.length; i++)
				t[i] = a[i];
			a=t;
			a[next] = x; next++;
		}
	}

	class Iterator implements Iteratore{
		private int pos=0;
		public boolean end() {
			if(pos<a.length)
				return true;
			else return false;
		}
		public Object current() {
			return a[pos];
		}
		public void next(){
			pos++;
		}
		public boolean hasNext() {
			return (pos<a.length);
		}
	}
}

public class Esercizio {
	public static int numero(Sequenza s) throws SequenzaNulla {
		if(s==null){
			throw new SequenzaNulla();
		}
		else{
			int tot=0;
			for(Sequenza.Iterator i = s.new Iterator(); i.hasNext(); i.next()) {
				if(i.current() instanceof String)
					tot++;
			}
			return tot;
		}
	}
	public static void main(String[] args) throws Exception{
		Sequenza s = new Sequenza(5);
		String str = new String("pippo");
		s.add(new Integer(0));
		s.add(str);
		s.add(new Integer(0));
		s.add(str);
		System.out.println(numero(s));
	}
}