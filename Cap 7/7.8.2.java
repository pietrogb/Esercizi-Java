/* Quali lock vengono rilasciati tramite una sospensione con il metodo wait?

Viene rilasciato solamente il lock su cui viene invocata la wait
	Let thread t be the thread executing the wait method on object m, and let n be the number of lock actions by t on m that have not been matched by unlock actions. One of the following actions occurs.
		- [...]
		- Otherwise, the following sequence occurs:
			1.Thread t is added to the wait set of object m, and performs n unlock actions on m.
			2. [...]

Scrivere un programma che 
	class T1 extends Thread {
	int i = 0;
	T2 t2;
	T1(T2 t2) {this.t2 = t2;}
	public void run() {
		while(i<10000) {
			synchronized(t2) {
				i++;
				if(i==2000)
					t2.sospendi = true;
				if(i==6000) {
					t2.sospendi = false;
					t2.notify();
				}
			} //fine sync
			try{
				Thread.sleep((int)(Math.random()*5));
			} catch(InterruptedException e) {}
		}
	}
}
	1. contiene un thread t che acquisisce due lock o1, o2 e poi invoca il comando o2.wait();
	2. illustra in qualche modo quali dei lock o1, o2 sono disponibili dopo la sospensione di t.
*/

public class Test {
    public static void main(String[] args) throws Exception {
        testCuncurrency();
    }

    private static void testCuncurrency() throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(new WaitTester(lock));
        Thread t2 = new Thread(new WaitTester(lock));
        t1.start();
        t2.start();
        Thread.sleep(15 * 1000);
        synchronized (lock) {
            System.out.println("Time: " + new Date().toString()+ ";" + "Notifying all");
            lock.notifyAll();
        }
    }

    private static class WaitTester implements Runnable {
        private Object lock;
        public WaitTester(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println(getTimeAndThreadName() + ":only one thread can be in synchronized block");
                    Thread.sleep(5 * 1000);

                    System.out.println(getTimeAndThreadName() + ":thread goes into waiting state and releases the lock");
                    lock.wait();

                    System.out.println(getTimeAndThreadName() + ":thread is awake and have reacquired the lock");

                    System.out.println(getTimeAndThreadName() + ":syncronized block have finished");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getTimeAndThreadName() {
        return "Time: " + new Date().toString() + ";" + Thread.currentThread().getName();
    }
}