// Scrivere un programma  che sicuramente va in deadlock

public class TestDeadlockExample1 {  
  public static void main(String[] args) {  
    final String resource1 = "ratan jaiswal";
    final String resource2 = "vimal jaiswal";
    // t1 tries to lock resource1 then resource2
    Thread t1 = new Thread() {
      public void run() {
          synchronized (resource1) {
           System.out.println("Thread 1: locked resource 1");
           try { Thread.sleep(100);} catch (Exception e) {}
           synchronized (resource2) {
            System.out.println("Thread 1: locked resource 2");
           }
         }
      }
    };
    // t2 tries to lock resource2 then resource1  
    Thread t2 = new Thread() {  
      public void run() {  
        synchronized (resource2) {  
          System.out.println("Thread 2: locked resource 2");
          try { Thread.sleep(100);} catch (Exception e) {}
          synchronized (resource1) {  
            System.out.println("Thread 2: locked resource 1");  
          }
        }
      }
    };
    t1.start();  
    t2.start();  
  }  
}

/*
Discutere cos'accade in una versione distribuita del programma precedente.
Se le risorse sono entrambe di tipo Serializable ed i thread vengono eseguiti entrambi dai singoli client, il programma va in deadlock sui singoli host che lo eseguono, dato che i thread non riescono ad impossessarsi degli oggetti locali. Discorso simile se gli oggetti sono di tipo Remote.
Se ognuno dei thread viene eseguito da un client differente, nel caso si abbiano oggetti che estendono Serializable, le cose funzionano perché ognuno dei thread ha un'oggetto locale. Se solo uno degli oggetti è di tipo Remote, uno dei due thread finirà per attendere che l'altro termini il lavoro e gli permetta di eseguire delle operazioni chimando synchronized sull'oggetto remoto.
Se entrambe le risorse sono di tipo Remote, è sicuro che il programma distribuito va in deadlock.
*/

public interface R extends Remote{
}

public class RI extends UniCastRemoteObjct implements R{
}

public class Server{
	public static void main(String[] args) throws Exception{
		R resource1 = new RI(), resource2 = new RI();
		Naming.rebind("resource1", resource1);
		Naming.rebind("resource2", resource2);
}

public class Client1{
	public static void main(String[] args) throws Exception {
		try{
			R resource1 = (R)Naming.lookup("resource1");
			R resource2 = (R)Naming.lookup("resource2");
			synchronized (resource1) {
				System.out.println("Client 1: locked resource 1");
				try { Thread.sleep(100);} catch (Exception e) {}
				synchronized (resource2) {
					System.out.println("Client 1: locked resource 2");
				}
        	}
		} catch(ConnectionException e){
			System.out.println("problemi di connessione al server");
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}
}

public class Client2{
	public static void main(String[] args) throws Exception {
		try{
			R resource1 = (R)Naming.lookup("resource1");
			R resource2 = (R)Naming.lookup("resource2");
			synchronized (resource2) {
				System.out.println("Client 2: locked resource 2");
				try { Thread.sleep(100);} catch (Exception e) {}
				synchronized (resource1) {
					System.out.println("Client 2: locked resource 1");
				}
        	}
		} catch(ConnectionException e){
			System.out.println("problemi di connessione al server");
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}
}