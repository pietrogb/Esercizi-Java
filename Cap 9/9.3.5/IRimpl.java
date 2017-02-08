import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;
import java.io.Serializable;

interface IS extends Serializable{
    String m();
    IS n(String s);
}

interface IR extends Remote {
    String m() throws RemoteException;

    IR n(String s) throws RemoteException;

    IS k() throws RemoteException;

    IR n() throws RemoteException;

    void f(IS s) throws RemoteException;
}

public class IRimpl extends UnicastRemoteObject implements IR {
    private A a;

    IRimpl() throws RemoteException {
        a = new A();
    }

    public String m() {
        return a.m();
    }

    public IR n(String s) {
        a.n(s);
        return this;
    }

    public IS k() throws RemoteException {
        return a;
    }

    public IR n() throws RemoteException {
        IR r = new IRimpl();
        return r.n(a.m());
    }

    public void f(IS s) throws RemoteException {
        try {
            a = (A) s;
            System.out.println("AA");
        } catch (ClassCastException e) {
            System.out.println("BB");
        }
    }

    private static class A extends ISimpl {
        private String s;

        A() {
            s = "GIALLO";
        }

        public String m() {
            return s;
        }

        public IS n(String s) {
            this.s = s;
            return this;
        }
    }
}