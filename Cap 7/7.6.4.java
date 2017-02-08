/*Modificare la seguente classe in modo tale che definisca il tipo di un conto corrente bancario che può correttamente essere gestito da più thread concorrenti*/

import java.util.concurrent.locks.*;

class ContoBancario{
	Lock lock = new ReentrantLock();
	private int num;
	private int saldo;
	ContoBancario(int n, int s) {num = n; saldo = s;}
	public int getSaldo() {return saldo;}
	public void deposita(int n) {
		lock.lock();
		try{
			saldo = saldo + n;
		} catch(InterruptedException e) {e.printStackTrace();}
		finally{
			lock.unlock();
		}
	}
	public void preleva(int n) {
		lock.lock();
		try{
			saldo = saldo - n;
		} catch(InterruptedException e) {e.printStackTrace();}
		finally{
			lock.unlock();
		}
	}
	public void trasferisci(int n, ContoBancario dest) {
		preleva(n);
		dest.deposita(n);
	}
}

