// Programma che conta byte e spazi contenuti in un file

import java.io.*;

public class ContaSpazi {
	public static void main(String[] args) throws IOException{
		InputStream is;
		if(args.length == 0)
			is = System.in;
		else
			is= new FileInputStream(args[0]);
		int ch, tot; int spazi = 0;
		for( tot=0; (ch = is.read()) != -1; tot++)
			if(Character.isWhitespace((char)ch))
				spazi++;

		System.out.println("il file " + args[0] + " occupa " + tot + " bytes, di questi " + spazi  + "sono uno spazio");
	}
}