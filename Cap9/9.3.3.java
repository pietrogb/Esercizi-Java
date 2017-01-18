// considerare le seguenti definizioni di classi, che rappresentano dei tipi condivisi da un programma client ed un programma server

public interface I extends Serializable {
	int get();
}

public class IImpl implements I {
	private int n;
	IImpl(int n) {this.n = n;}
	public int get() {return n;}
	public void set(int n){this.n=n;}
}

public interface C extends Remote{
	void aggiungi(I i) throws RemoteException;
	I get(int i) throws RemoteException;
	String stampa() throws RemoteException;
}

// Si consideri inoltre la seguente definizione di classe che implementa un oggetto remoto di tipo C

public class CImpl extends UniCastRemoteObjec implements C{
	private I[] v = new I[10];
	private int top=0;

	CImpl()  throws RemoteException;

	public synchronized void aggiungi(I i)  throws RemoteException{
		v[top++]=i;
		if(top == v.length){
			I[] temp = new I[v.length*2];
			for(int j=0; j<top; j++)
				temp[j]=v[j];
			v=temp;
		}
	}

	public synchronized I get(int i) throws RemoteException{
		for(int j=0; j<top; j++)
			if(v[j].get()==i)
				return v[j];
		return null;
	}

	public synchronized void incrementa(int n){
		for(int j=0; j<)
	}
	
	public synchronized String stampa() {
		String s="";
		for (int j=0; j<top; j++) {
			s+=v[j].get()+ " ";
		return s;
		}
	}
}

// programma server
public class RemoteServer{
	public static void main(String[] a) throws Exception{
		CImpl ci=new CImpl();
		Naming.rebind("pippo", ci);
		ci.aggiungi(new IImpl(0));
		ci.aggiungi(new IImpl(1));

		/***PUNTO DI SINCRONIZZAZIONE 1***/

		System.out.println("1:"+ci.stampa());
		I i = ci.get(2);
		i.set(-3);
		System.out.println("2:"+ci.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 2***/

		System.out.println("3:"+ci.stampa());
		ci.aggiungi(i);
		System.out.println("4:"+ci.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 3***/

		ci.incrementa(10);
		System.out.println("5:"+ci.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 4***/

		System.out.println("6:"+ci.stampa());
		I i = ci.set(55);
		System.out.println("7:"+ci.stampa());
	}
}

// programma client
public class RemoteClient{
	public static void main(String[] a) throws Exception{
		C c = Naming.lookup("pippo");
		ci.aggiungi(new IImpl(2));

		/***PUNTO DI SINCRONIZZAZIONE 1***/

		System.out.println("1:"+c.stampa());
		I i = ci.get(0);
		i.set(-4);
		System.out.println("2:"+c.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 2***/

		System.out.println("3:"+c.stampa();
		ci.aggiungi(i);
		System.out.println("4:"+c.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 3***/

		System.out.println("5:"+c.stampa());

		/***PUNTO DI SINCRONIZZAZIONE 4***/

		System.out.println("6:"+c.stampa());
		I i = ci.set(99);
		System.out.println("7:"+c.stampa());
	}
}