import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

public interface IS extends Serializable{
    String m();
    IS n(String s);
}