// 10.0.28
import java.util.*;

class Controllata extends Exception {}

class NonControllata extends Exception {}

class Esercizio {
	public static Object Fun(Collection c) throws Controllata, NonControllata {
		try{
			if (c==null)
				throws new Controllata();
			else {
				if((c instanceof Vector) && (c.size()>=3))
					return c.get(2);
				else {
					if ((c instanceof LinkedList) && (c.size()>0))
						return c.removeFirst();
					else return new NonControllata();
				}
			}
		} catch(Controllata co) {}
	}
}