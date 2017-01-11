// Il programma legge dallo standard input il nome di un file e copia il contenuto del primo nel secondo
import java.io.*; import java.util.*;

public class FileTest {
	public static void main(String[] args) throws IOException{
		String file1, file2;
		Scanner input = new Scanner(System.in);
		System.out.println("Inserisci il nome del file");
		file1 = input.next(); 
		file2 = input.next(); 
		PrintWriter out = new PrintWriter(new File(file2));
		Scanner in = new Scanner(new File(file1));
		while(in.hasNext()) {
			int n = in.nextInt();
			out.println(n);
			out.flush(); //svuota il buffer
		}
		out.close(); in.close();
	}
}