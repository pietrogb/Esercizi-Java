// creazione del client

import java.io.IOException; 
import java.io.UnsupportedEncodingException; 
import java.net.ConnectException; 
import java.text.ParseException; 


import java.rmi.*;

public class HelloClient{
	private static final String HOST ="localhost";

	public static void main(String[] args) throws Exception{
		try{
			// ottieni istanza d'oggetto remoto(di tipo giusto!!)
			Hello ref = (Hello) Naming.lookup("rmi ://"+HOST+"/Hello");
			// invoca metodo remoto
			System.out.println("msg ricevuto" + ref.sayHello());
		} /*catch(ConnectionException e) {
			System.out.println("Problemi di connessione al server");
		}*/
		catch(Exception exc){
			exc.printStackTrace();
		}
	}
}