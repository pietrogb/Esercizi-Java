import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

public class IImpl implements I {
	private String s;
	IImpl(String n) {s=n;}
	public String get() {return s;}
	public void set(String n) {s=n;}
	public void stampa() {System.out.println(s);}
}