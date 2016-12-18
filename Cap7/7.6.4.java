/*Modificare la seguente classe in modo tale che definisca il tipo di un conto corrente bancario che può correttamente essere gestito da più thread concorrenti*/



class ContoBancario{
	private int num;
	private int saldo;
	ContoBancario(int n, int s) {num = n; saldo = s;}
	public int getSaldo() {return saldo;}
	public void deposita(int n) {saldo = saldo + n;}
	public void preleva(int n) {saldo = saldo - n;}
	public void trasferisci(int n, ContoBancario dest) {
		preleva(n); dest.deposita(n);
	}
}