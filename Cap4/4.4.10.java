/*
La dichiarazione della classe String della libreria API è la seguente:
public final class String extends Object implements Serializable, Comparable

Si consideri il seguente metodo statico
*/

public static boolean F(Object r) {
	if(r istanceof String) return true;
	return false;
}

/*
Sia ref un qualsiasi oggetto. Quale affermazione è vera?
	1. F(ref) ritorna true sse sia il tipo statico che il tipo dinamico di ref sono la classe String
	2. F(ref) ritorna true sse il tipo dinamico di ref è o Object oppure String
	3. F(ref) ritorna true sse il tipo dinamico di ref è String oppure è una classe che implementa sia Serializable che Comprarable
La terza risposta è vera
*/