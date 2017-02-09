// Si considerino le seguenti definizioni:

class E1 extends Exception {}
class E3 extends Exception {}
class E2 extends E3 {}

public class D {
	D m() throws E3 {
		return new D();
	}
}

public class C extends D {
	// ...m(..) .. {...}
}


/*
Per ognuna delle seguenti definizioni del metodo m della classe C, indicare se:
	1. La classe C non compila;
	2. La classe C compila e si tratta di un overloading del metodo m della classe D;
	3. La classe C compla e si tratta di un overriding del metodo m della classe D;

1. C m(int i) {return new C();}
2. D m(int i) throws E3 { return new D();}
3. C m() throws E3 {return new C();}
4. D m() { return new D();}
5. D m() throws E3, E2 { return new D();}
6. D m() throws E3, E1 { return new D();}
7. D m() throws E2 { return new D();}
8. D m(int i) throws E1 { return new D();}
9. void m(int i) {}
10. public D m( ) throws E3 { return new D();}
11. protected D m( ) throws E3 { return new D();}
12. private D m(int i) { return new D();}
13. private D m( ) { return new D();}
15. void m() {}


The overriding method must NOT throw checked exceptions that are new or broader than those declared by the overridden method. For example, a method that declares a FileNotFoundException cannot be overridden by a method that declares a SQLException, Exception, or any other non-runtime exception unless it's a subclass of FileNotFoundException.


Method overriding, in object oriented programming, is a language feature that allows a subclass or child class to provide a specific implementation of a method that is already provided by one of its superclasses or parent classes. The implementation in the subclass overrides (replaces) the implementation in the superclass by providing a method that has same name, same parameters or signature, and same return type as the method in the parent class

Method Overloading is a feature that allows a class to have two or more methods having same name, if their argument lists are different. I

*/