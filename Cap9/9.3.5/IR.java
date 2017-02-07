import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

public interface IR extends Remote {
    String m() throws RemoteException;

    IR n(String s) throws RemoteException;

    IS k() throws RemoteException;

    IR n() throws RemoteException;

    void f(IS s) throws RemoteException;
}