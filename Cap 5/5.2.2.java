/*Un frame con due bottoni*/

import javax.swing.*;
import java.awt.*;

class TwoButtonsFrame extends JFrame {
	public TwoButtonsFrame(String s) {
		super(s);
		setSize(400,200);
		setLayout(new FlowLayout());

		JButton b1 = new JButton("schiaccia");
		JButton b2 = new JButton("pigia");
		b1.setBackground(Color.yellow);
		b2.setForeground(Color.red);
		add(b1);
		add(b2);
	}
}

public class Finestra {
	public static void main(String[] args) {
		TwoButtonsFrame f = new TwoButtonsFrame("Ciao!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}

/*
L'oggetto contiene due componenti, c'è quindi bisogno di gestire il posizionamento di tali componenti all'interno del frame. Questo compito è affidato ad un layout manager, un oggetto, in questo caso, di tipo Flowlayout.
*/