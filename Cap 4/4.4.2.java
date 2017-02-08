/*Cosa stampa?*/

class Outer{
	int x=1;
	public void test() {
		Inner i = new Inner();
		i.display(); showy();
	}
	public static void n() {
		Outer r = new Outer(); 
		Inner t = r.new Inner();
	}

	class Inner {
		public void display() {System.out.print(" x= " + x);}
		private int y = 3; //campo dati locale ad Inner
	}

	public void showy() {
		//System.out.println(y); //Illegale
		System.out.println("y= " + (new Inner()).y); //OK
	}
}

public class Test{
	public static void main(String[] args) {
		Outer o = new Outer(); 
		o.test(); //Stampa x=1 y=3
		Outer.Inner i = o.new Inner(); 
		i.display(); //x=1
	}
}

/*
stampa:
	x=1 y=3
	x=1
*/