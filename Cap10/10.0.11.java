import java.util.Random;

class Distributore{
	private final Object vino = new Object();
	private final Object acqua = new Object();
	private final Object aranciata = new Object();

	int n_acqua=0, n_vino=0, n_aranciata=0;

	public synchronized void setN_acqua() {this.n_acqua++;}
	public synchronized void setN_vino() {this.n_vino++;}
	public synchronized void setN_aranciata() {this.n_aranciata++;}

	public synchronized int getN_acqua() {return n_acqua;}
	public synchronized int getN_vino() {return n_vino;}
	public synchronized int getN_aranciata() {return n_aranciata;}

	public void riempiBicchiere(Cliente c){
		System.out.println("riempio il bicchiere del cliente " + c.numero);
	}

	class Cliente extends Thread {
		private int numero;
		private String tipo_bevanda;
		Cliente(int n, String nb){numero = n; tipo_bevanda=nb;}

		public void run(){
			System.out.println("Sono il " + numero +" cliente e ");

			if(tipo_bevanda=="A"){
				synchronized (acqua){
					setN_acqua();
					riempiBicchiere(this);
				}
			}
			if(tipo_bevanda=="V"){
				synchronized (vino){
					setN_vino();
					riempiBicchiere(this);
				}
			}
			if(tipo_bevanda=="A"){
				synchronized (aranciata){
					setN_aranciata();
					riempiBicchiere(this);
				}
			}
			System.out.println("Sono il cliente numero " + this.numero + " e ho bevuto " + this.tipo_bevanda);
		}
	}

 public static void main( String[] argv){
        Distributore distributore = new Distributore();
        String[] bibite = {"A","V","C"};
        Random r = new Random();
        Cliente[] allThread = new Cliente[100];
        for (int i = 0; i < 100; i++){
            int idx = r.nextInt(bibite.length);
            String bevanda = bibite[idx];
            Cliente c = distributore.new Cliente(i, bevanda);
            allThread[i] = c;
            c.start();
        }
        for (int i = 0; i < 100; i++){
            try {
                allThread[i].join();
            } catch (InterruptedException e){}
        }
        System.out.println("TERMINATO. I clienti che hanno preso acqua sono: " + distributore.getN_acqua()
        + ", quelli che hanno preso vino sono: " + distributore.getN_vino()
        + " e quelli che hanno preso aranciata sono: " + distributore.getN_aranciata());
	}
}

// 10.0.11

/*
1. No, sembra che serva i clienti in maniera sequenziale.
2. Il cliente 24 arriva e desidera bere dell'acqua; poco più tardi arriva il cliente 25, che desidera anch'esso bere dell'acqua. Il distributore dell'acqua è libero e li serve uno dopo l'altro. Appare come se la distribuzione delle benvande non stia avvenendo in maniera concorrente.
3. Il programma precedente sempbra implementare una coda FIFO, visto che l'ordine con cui vengono serviti i clienti non sembra influenzato dal tipo di bevanda, ma solo dall'ordine di arrivo.
*/