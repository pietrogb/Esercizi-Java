class E1 extends Exception {}
class E2 extends Exception {}

class MyArray {
	private Object[] my_a;
	public MyArray(int k) throws E1 {
		if(k>0) 
			my_a = new Object[dim];
		else{
			my_a = new Object[0];
			throw new E1();
		}
	}
	public static int size() {
		return my_a.length;
	}
	public Number getIfNumber(int k) throws E1 {
		if((k>=0) && (k<my_a.length) && (my_a[k] instanceof Number)) {
			return my_a[k];
		}
		else
			return new E1();
	}
}

class Esercizio{
	public Integer fun1(MyArray a) throws E2 {
		try {
			if ((a.getIfNumber(a.size()-1)) instanceof Integer)
				return (a.getIfNumber(a.size()-1);
			else
				throw new E2();
		} catch (E1 e) {
			throw new E2();
		}
	}
	public static void main(String[] args) throws E2 {
		
	}
}

/*
Java class Number -> Direct Known Subclasses:
AtomicInteger, AtomicLong, BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short
*/