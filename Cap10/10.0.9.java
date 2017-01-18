
class Risorsa {
	private String nome;
	public Risorsa(String n) {nome=n;}
	public String getNome() {return nome;}
}

class Fornitore{
	public Risorsa produci() throws InterruptedException {
		try{
			Thread.sleep((int)(Math.random()*1000));
		} catch(Exception e) {}
		return new Risorsa("mobile");
	}
}

 class Artigiano extends Thread{
	private Risorsa r;
	private Fornitore fornitore;
	private String nome;
	private boolean pronta = false;
	Artigiano(String s, Fornitore fo) {
		nome=s;
		fornitore=fo;
	}

	class Lavora extends Thread {
        public void run() {
            for (int i = 0; i < 100; ++i) {
                System.out.println("L'artigiano " + nome + " procede con il lavoro");
            }
            synchronized(fornitore) {
                while (pronta == false) {
                    try {
                        fornitore.wait();
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
                System.out.println("L'artigiano " + nome + " ha completo il lavoro iniziato");
            }
        }
    }

	class Attendi extends Thread {
        public void run() {
            try {
                Risorsa r = fornitore.produci();
                pronta = true;
                System.out.println("L'artigiano " + nome + " ha ricevuto: " + r.getNome());
                synchronized(fornitore) {
                    fornitore.notifyAll();
                }
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }

    public void run() {
        Thread lavora = new Lavora();
        Thread attendi = new Attendi();

        attendi.start(); lavora.start();
    }
}

public class NuovaCucina{
	public static void main(String[] args) {
		Fornitore f = new Fornitore();
		Artigiano[] compagnia = {new Artigiano("1", f), new Artigiano("2", f), new Artigiano("3,", f)};
		for(int i=0; i<3; i++)
			compagnia[i].start();
	}
}

// 10.0.9