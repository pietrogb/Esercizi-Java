// 10.0.21
// Si considerino le seguenti definizioni:

class E1 extends Exception {}
class E3 extends Exception {}
class E2 extends E3 {}

class D {
	D m() throws E3 {
		return new D();
	}
}

public class C extends D {
	// ...m(..) .. {...}
	private D m( ) { return new D();}
}

/*
Per ognuna delle seguenti definizioni del metodo m della classe C, indicare se:
	1. La classe C non compila;
	2. La classe C compila e si tratta di un overloading del metodo m della classe D;
	3. La classe C compla e si tratta di un overriding del metodo m della classe D;

1. C m(int i) {return new C();}
	Cambia il tipo ed il numero dei parametri. È un overloading
2. D m(int i) throws E3 { return new D();}
	Cambia il numero ed il tipo di parametri. È un overloading.
3. C m() throws E3 {return new C();}
	Il numero di parametri è lo stesso, cambia solo il tipo di ritorno che è più piccolo del metodo originale. Overriding
4. D m() { return new D();}
	Stesso tutto. Overriding
5. D m() throws E3, E2 { return new D();}
	E2 è estensione di E3, quindi è ok. Overriding, perché mantiene la stessa segnatura.
6. D m() throws E3, E1 { return new D();}
	L'estensione lanciata è più grande. Non compila
7. D m() throws E2 { return new D();}
	come #5. Overriding
8. D m(int i) throws E1 { return new D();}
	Overloading del metodo (i parametri sono diversi)
9. void m(int i) {}
	Overloading del metodo (tipo di ritorno e parametri sono diversi)
10. public D m( ) throws E3 { return new D();}
	Overriding del metodo. Va bene perché è segnato public.
11. protected D m( ) throws E3 { return new D();}
	Overriding del metodo
12. private D m(int i) { return new D();}
	Overloading del metodo
13. private D m( ) { return new D();}
	Non compila
14. void m() {}
	Overloading del metodo

Overloading: permette ad un metodo di avere lo stesso nome di un altro, purchè il numero e tipo di parametri sia differente. Il tipo di ritorno non può essere la sola differenza tra le diverse definizioni. In caso di definizioni multiple che lasciano spazio ad ambiguità, il compilatore segnala errore.

Overriding: un metodo ridefinito deve mantenere la stessa segnatura: nome, numero e tipo degli argomenti devono coincidere; il tipo di ritorno uguale o più piccolo del metodo originale (altrimenti il compilatore segnala un errore). Un metodo ridefinito non può diventare meno accessibile del metodo originale (es. un metodo pubblico non può diventare privato). Un metodo ridefinito non può sollvare più eccezioni di quelle del metodo originale.

The overriding method must NOT throw checked exceptions that are new or broader than those declared by the overridden method. For example, a method that declares a FileNotFoundException cannot be overridden by a method that declares a SQLException, Exception, or any other non-runtime exception unless it's a subclass of FileNotFoundException.

Method overriding, allows a subclass or child class to provide a specific implementation of a method that is already provided by one of its superclasses or parent classes. The implementation in the subclass overrides (replaces) the implementation in the superclass by providing a method that has same name, same parameters or signature, and same return type as the method in the parent class

Method Overloading is a feature that allows a class to have two or more methods having same name, if their argument lists are different. I

*/