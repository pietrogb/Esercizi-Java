/*Esempio di una GUI con un solo frame*/

import javax.swing.*;

class MyFrame extends JFrame {
	public MyFrame(String s){
		super(s);
		setSize(400,400);
	}
}

public class Finestra {
	public static void main(String[] args) {
		MyFrame f = new MyFrame("Ciao!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}

/*5.2.1*/
/*
Il metodo main crea un oggetto di tipo JFrame, imposta la sua visualizzazione e l'uscita dall'applicazione con la chiusura della finestra appena creata.
Eseguendo questo programmma, il programma non termina nonostante sia terminata l'esecuzione del metodo main
*/