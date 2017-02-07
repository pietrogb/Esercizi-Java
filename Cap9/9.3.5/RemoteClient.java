import java.rmi.*;
import java.io.Serializable;


public class RemoteClient extends ISimpl{

    public static void main(String[] a) throws Exception{

        IR[] v = new IR[3];

        v[0]=(IR) Naming.lookup("pippo");
        v[1]=v[0].n("BLU");
        v[2]=v[0].n();

        v[1].n("ROSSO");

        System.out.println(v[0].m());
        System.out.println(v[1].m());
        System.out.println(v[2].m());

        IS x=new ISimpl();
        v[2].f(x);

        System.out.println(v[0].m());
        System.out.println(v[1].m());
        System.out.println(v[2].m());
    }

}
