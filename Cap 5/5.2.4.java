import javax.swing.*;
import java.awt.*;

public class BL {
	private JFrame f = new JFrame("Guarda un po'...");
	private JButton b1 = new JButton("pippo"),
					b2 = new JButton ("pluto"),
					b3 = new JButton ("paperino"),
					b4 = new JButton ("gastone"),
					b5 = new JButton ("topolino");
	public BL() { //JFrame ha BorderLayout di default
		f.add(b1, BorderLayout.NORTH);
		f.add(b2, BorderLayout.SOUTH);
		f.add(b3, "West");
		f.add(b4, "East");
		f.add(b5, "Center");
		f.pack(); //notare il pack() della classe Window
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public static void main(String[] args) {
		BL ref = new BL();
	}
}

/*
Borderlayout manager: Notare cosa succede ridimensionando il frame
*/