import javax.swing.*;
import java.awt.*;

public class GL {
	private JFrame f = new JFrame("Che roba...");
	private JButton b1 = new JButton("pippo"),
					b2 = new JButton("pluto"),
					b3 = new JButton("paperino"),
					b4 = new JButton("gastone"),
					b5 = new JButton("topolino");
	public GL() {
		f.setLayout(new GridLayout(3,2,10,5));
		f.add(b1); f.add(b2); f.add(b3); f.add(b4); f.add(b5);
		f.pack(); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	public static void main(String[] args) {
		GL ref = new GL();
	}
}