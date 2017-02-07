import java.rmi.*;
import java.rmi.server.*;
import java.lang.Object;



public class RemoteServer{

    public static void main(String[] a) throws Exception{

        IRimpl rem = new IRimpl();
        Naming.rebind("pippo", rem);

        IR rr=rem.n();
        rr.n("AZZURRO");
        IS y=rr.k();
        rr.f(y);
        System.out.println(rem.m());
        System.out.println(rr.m());
    }

}
