// esercizio 10.0.14


class C1{
	private boolean procedere = new boolean;
	public void stampa(String s){
		System.out.println(s);
	}
	public getBool() return procedere;
	public synchronized void 
}

class T1 extends Thread{
	private C1 ob;
	T1(C1 o) {ob=o;}
	public void run() {
		// stampa A
		ob.print("A");
		while(ob.getBool()==false)
			wait();
		// aspetta che l'oggetto condiviso sia stato messo a 1
		// stampa B
	}
}

// thread 2: uguale a thread 1

public class DueThread{
	public static void main(String[] args) {
		C1 obj= new C1();
		T1 t1=new T1(obj);
		T2 t2=new T2(obj);
	}
}