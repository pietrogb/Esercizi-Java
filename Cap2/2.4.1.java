public class Prova {
	float f;
	public void changeInt(int n){n=55;}
	public String changeStr(String s){s=new String ("pippo"); return s;}
	public void changeObj (Prova ref){ref.f=0.99F;}

	public static void main(String[] args) {
		String str = new String("pluto");
		int val=11;
		Prova pt=new Prova();
		pt.changeInt(val);
		System.out.println(val); //stampa 11
		System.out.println(pt.changeStr(str)); //stampa: pippo
		System.out.println(str); //stampa: pluto
		pt.f=101.0F;
		pt.changeObj(pt);
		System.out.println(pt.f); //stampa 99-0
	}
}