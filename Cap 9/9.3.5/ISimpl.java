

import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

// public interface IR extends Remote {
//     String m();

//     IR n(String s);

//     IS k() throws RemoteException;

//     IR n() throws RemoteException;

//     void f(IS s) throws RemoteException;
// }

interface IS extends Serializable{
    String m();
    IS n(String s);
}

public class ISimpl implements IS{
    public String m(){ return "TOPOLINO"; }
    public IS n(String s){ return this; }
}