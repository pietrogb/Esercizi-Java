// Si considerino le seguenti definizioni: 
interface I {
	int f();
	String g();

	static class ID {
		protected String s="topolino";
		public int f() {return 3;}
		public String g() {return s+f();}
	}
}

class A extends I.ID {
	public void set(String st) {s=st;}
	public int f() {return 6;}

	class AD implements I {
		private String s = "paperino";
		public  int f() {set("minnie"); return 9;}
		public String g() {return (s+f()+"--"+A.this.g());}
	}
	public static void main(String[] args) {
		I.ID id = new A(); //ok
		I i = ((A)id).new AD(); //converte id ad A e può farlo, poi lo usa per creare un nuovo ogg.AD ed è ok, poi lo salva con l'interfaccia. Bene ancora.
		System.out.println(i.g());
	}
}

/*
Ognuno dei seguenti frammenti, racchiuso in metodo main:
A. Non compila. Sengare con una crocetta il primo statement che provoca l'errore in compilazione
B. Eccezione: il main compila ma la sua esecuzione provoca un errore in compilazione
C. Stampa correttamente: riportare la stampa. Se non stampa alcunché, scrivere NESSUNA STAMPA

---------------------------1-----------------------------
	A a = new A();
	System.out.println(a.g());
	topolino6
---------------------------2-----------------------------
	I.Id id = new I.ID();
	System.out.println(id.g());
	topolino3
---------------------------3-----------------------------
	A.AD ad = a.new AD(); //X
	System.out.println(ad.g());
	Non compila
---------------------------4-----------------------------
	A a = new A();
	I i = a; //X
	System.out.println(i.g());
	I tipi non sono compatibili: A non può essere convertito ad I
---------------------------5-----------------------------
	A a = new A();
	I i = (I)a; //X
	System.out.println(i.g()); 
	Eccezione
---------------------------6-----------------------------
	A a = new A();
	I i = a.new AD();
	System.out.println(i.g());
	paperino9--minnie6
---------------------------7-----------------------------
	A a = new A();
	I.ID i = a;
	System.out.println(i.g());
	topolino6
---------------------------8-----------------------------
	I.ID id = new I.ID();
	I i = id; //X
	System.out.println(i.g());
	Tipi incompatibili: ID non può essere convertito ad I (ID è classe interna)
	Non compila
---------------------------9-----------------------------
	A a = new A();
	I.ID id = a.new AD(); //X
	System.out.println(id.g());
	Non compila. 
---------------------------10----------------------------
	A a = new A();
	A.AD ad = a;
	System.out.println(ad.g());
	Non compila
---------------------------11----------------------------
	A a = new A();
	I.ID ii = a.new I.ID();
	System.out.println(ii.g());
	non compila
---------------------------12----------------------------
	I.ID id = new I.ID();
	A a = (A) id;
	System.out.println(a.g());
	Eccezione: un oggetto di ID non può essere convertito ad A. ClassCastException
---------------------------13----------------------------
	I.ID id = new A();
	I i = ((A)id).new AD();
	System.out.println(i.g());
	paperino9--minnie6
*/