// Un frame con un bottone che provoca una stampa su System.out

import java.awt.event.*;
import javax.swing.*;

public class Test extends JFrame {
	private JButton b = new JButton("Premi");
	// premere un bottone genera un ActionEvent
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// unico metodo da implementare
			System.out.println("Bottone premuto!");
			System.out.println("La label del bottone e' :" + e.getActionCommand());
		}
	}

	public Test() {
		super("Test");
		b.addActionListener(new ButtonHandler());
		add(b, "Center");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		Test ref = new Test();
	}
}
// 5.3.3