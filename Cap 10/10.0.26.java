// La seguente classe compila correttamente

import java.awt.event.*;
import javax.swing.*;

public class Ex {
	private JButton jb = new JButton();
	private class MyFrame extends JFrame implements ActionListener {
		MyFrame() {
			super("PADOVA");
			getContentPane().add(jb);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) { }
			} );
			jb.addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) { }
	}
	private static class MyListener extends MouseAdapter {
		public void MouseEntered(MouseEvent e) { }
	}
	public static void main(String[] args) {
		Ex e = new Ex();
		Ex.MyFrame f = e.new MyFrame();
		f.addMouseListener(new MyListener());
	}
}

 class Esercizio{
	public static void main(String[] args) {
		Ex e = new Ex();
		if(e instanceof ActionListener)
			System.out.println("Ex è ActionListener");
		else
			System.out.println("prima falsa");
	}
}

/*
Quali affermazioni sono VERE riguardo la precedente classe?
	1. Ogni oggetto di Ex è un ActionListener 
		FALSO: contiene un actionListener ma non lo è
	2. La classe Ex ha tra i suoi membri una classe interna anonima 
		FALSO
	3. L'invocazione super(PADOVA); provoca la chiamata del costruttore di ActionListener
		VERO
	4. Vi è un WindowListener registrato sull'oggetto f
		vero
	5. f è un sottooggetto di e
		vero
	6. Vi è un MouseListener registrato sul bottone jb
		Vero, tramite f che è costruito su e
	7. Se la classe MyFrame è dichiarata statica allora la classe Ex non compila
		Vero, perché all'interno delle classi statiche vanno usati solo campi dati e metodi statici e jb non lo è
	8. Se la classe MyListener è dichiarata non statica allora la classe Ex non compila
		Vero, perchè il main è dichiarato statico
	9. Un oggetto MyListener è un MouseListener
		Vero
*/