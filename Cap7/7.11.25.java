// definire il metodo m() di Z in modo che l'esecuzione di C provochi esattamente la stampa della stringa PingPong 5 volte

class Z {
	String s = new String("XXX");
	int n_stampe = 0;
	synchronized void m() {
		if(n_stampe<5) {
			s = "PingPong";
			System.out.print(s + " ");
			n_stampe++;
		}
	}
}

class T extends Thread {
	Z z;
	public T(Z z) {this.z = z;}
	public void run() {
		for (int i=0; i<5; i++) {
			z.m();
		} //invoca 5 volte m. Il problema sta nel fatto che ci sono due theads
	}
}

public class C {
	public static void main(String[] args) {
		Z z = new Z(); T t1 = new T(z), t2 = new T(z);
		t1.start(); t2.start();
	}
}