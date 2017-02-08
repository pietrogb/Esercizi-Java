// 10.0.16
class RC{
	void m(RS s) {
		synchronized(this) {
			try{
				Thread.sleep(80);
				s.m(this);
			} catch (Exception e) {}
		}
	}
}

class RS{
	void m(RC c) {
		synchronized(this) {
			try{
				Thread.sleep(80);
				c.m(this);
			} catch (Exception e) {}
		}
	}
}

public class Uno {
	private static class T1 extends Thread {
		RC c;
		RS s;
		T1(RC c, RS s) {this.c=c; this.s=s;}
		public void run() {
			c.m(s);
		}
	}
	private static class T2 extends Thread{
		RC c;
		RS s;
		T2(RC c, RS s) {this.c=c; this.s=s;}
		public void run() {
			s.m(c);
		}
	}
	public static void main(String[] args) {
		RC c = new RC();
		RS s = new RS();
		new T1(c,s).start();
		new T2(c,s).start();
	}
}



/*
A. Definire tutti i possibili comportamenti del programma precedente, motivando la risposta.
	Il programma precedente invoca in maniera incrociata i metodi m rispettivamente sulle variabili s e c. Ha un'altissima probabilità di andare in deadlock o starvation
B. Si supponga di rendere distribuito il programma nel modo seguente:
 - i tipi RC ed RS sono sottotipi di Remote; gli oggetti s e c sono allocati su due host distinti, rispettivamente host2 ed host1; l'host1 recupera un riferimento remoto all'oggetto s mentre l'host2 recupera un riferimento remoto all'oggetto c; il thread new T1(c, s) viene attivato su host1 mentre il thread new T2(c,s) viene attivato su host2
	I due host hanno a disposizione un riferimento remoto a quello che in sostanza è lo stesso oggetto, di conseguenza, andranno ancora in deadlock.
C. Si supponga di rendere distribuito il programma nel modo seguente:
 - il tipo RC è serializable mentre RS è sottotipo di Remote; si coniderino due hosti distinti, rispettivamente Client e Server; il server alloca l'oggetto s pubblicizzandone il riferimento, e crea un proprio oggetto c; il client recuprea un riferimento remoto all'oggetto s e crea un proprio oggetto c;il thread new T1(c,s) viene attivato sul server mentre il thread new T2(c,s) viene attivato sul client.
 	In questo caso il server possiede una copia locale dell'oggetto, così ha modo di proseguire nell'invocazione del metodo m su oggetti locali e di terminare l'esecuzione. Il client potrà anch'egli terminare l'esecuzione, senza i problemi che si presentavano in precedenza.
*/