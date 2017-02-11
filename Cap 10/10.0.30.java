
class Chiamata {
	private int n_chiamato;
	private int durata;
	private int costo;

	public int getDurata() {return durata;}

	public int getN_chiamato() {return n_chiamato;}

	public Chiamata(int n, int d) {
		n_chiamato = n; durata = d;
	}

	public void printInfo() {
		System.out.println("Numero chiamato " + n_chiamato);
		System.out.println("durata " + d);
		System.out.println("costo "+costo);
	}
}

class SchedaEsaurita extends Exception {}

abstract class Scheda {
	private valoreResiduo;

	abstract public int costo(Chiamata c);

	public double disponibile() {
		return valoreResiduo;
	}

	public double addebita(Chiamata c) throws SchedaEsaurita {
		double valore = c.costo() / 100;
		if (valore > valoreResiduo)
			throw new SchedaEsaurita();
		else
			valoreResiduo -= valore;
	}
}

class Flat extends Scheda {
	public int costo(Chiamata c) {
		return c.getDurata() * 10;
	}
}

class EvenOdd extends Scheda {
	public int costo(Chiamata c) {
		if(c.getN_chiamato() % 2 == 0)
			return c.getDurata() * 15;
		else
			return c.getDurata() * 5;
	}
}