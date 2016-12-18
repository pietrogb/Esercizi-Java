import java.util.concurrent.locks.*;

public class Bank {
	Lock lock = new ReentrantLock();
	public static final int NUMBER_OF_ACCOUNT = 100;
	private double[] accounts = new double[NUMBER_OF_ACCOUNT];;
	public Bank(double total) {
		double singleAmount = total / 100D;
		for (int i=0; i<NUMBER_OF_ACCOUNT; i++) {
			accounts[i] = singleAmount;
		}
	}
	public void transfer(int from, int to, double amount) {
		lock.lock();
		try{
			accounts[from] -= amount;
			accounts[to] += amount;
		} catch(Exception e) {e.printStackTrace();}
		finally{ lock.unlock();}
	}

	public double getTotal() {
		double total = 0.0D;
		for(int i=0; i<NUMBER_OF_ACCOUNT; i++) {
			total += accounts[i];
		}
		return total;
	}

	public static void main(String[] args) {
		Bank bank = new Bank(100000D);
		for(int i=0; i<100; i++) {
			new Thread(new TransferRunnable(bank)).start();
		}
	}
}

class TransferRunnable implements Runnable {
	private Bank bank;
	private static final double MAX_TRANSFERABLE_AMOUNT = 1000D;
	public TransferRunnable(Bank bank) {
		this.bank = bank;
	}
	// @override
	public void run() {
		int to = (int) (Math.random() * Bank.NUMBER_OF_ACCOUNT);
		int from = (int)(Math.random() * Bank.NUMBER_OF_ACCOUNT);
		double amount = Math.random() * MAX_TRANSFERABLE_AMOUNT;
		bank.transfer(from, to, amount);
		System.out.println(String.format("from %s to %s amount %s total %s", from, to, amount, bank.getTotal()));
	}
}