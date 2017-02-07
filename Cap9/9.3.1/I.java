import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

public interface I extends Serializable {
	String get();
	void set(String n);
	void stampa();
}