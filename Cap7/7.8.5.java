/*
Garbage collector thread.
È possibile tracciare l'esecuzione del garbage collector thread, che procede concorrentemente all'esecuzioone di una normale applicazione, utilizzando il metodo finalize().
Questo metodo è fornito dalla classe Object, quindi diponibile su ogni oggetto.
Quando il garbage collector determina che non ci sono più riferimenti ad un certo oggetto obj, invoca automaticamente il metodo obj.finalize() e seccessivamente dealloca la memoria occupata da obj.
Nella classe Object il metodo finalize ha una definizione vuota; tipicamente una sottoclasse ridefinisce questo metodo per rilasciare eventuali risorse di sistema o per eseguire qualche azione di clean-up prima di deallocare l'oggetto a cui queste risorse sono associate. È importante ossercare che il metodo finalize è ben diverso dai sistrutori usati nel linguaggio C++. L'output dell'esempio illustra il comportamento non-deterministico del garbage collector thread, che esegue concorrentemente al programma principale.
*/

class C {
	static boolean flag = false;
	static int creati = 0, finalizzati = 0;
	int i;
	C() {
		i= ++creati;
		if(creati == 328)
			System.out.println("Creato il 348-esimo oggetto di C");
	}

	public void finalize() {
		finalizzati++;
		System.out.println("Finalizza l'oggetto #" + i + "dopo la creazione di " + creati + "oggetti di C");
		if(i > 328)
			flag = true; //ferma tutto con la prima finalizzazione di un oggetto creato dopo i primi 328
	}
}

public class Garbage {
	public static void main(String[] args) {
		while(!C.flag) {
			new C();
			new String("Occupo memoria!");
		}
		System.out.println("Sono stati creati " + C.creati + " oggetti di C\n" + "di quelli ne sono stati finalizzati " + C.finalizzati);
	}
}

// 7.8.5