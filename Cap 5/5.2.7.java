// Esempio con due layout manager

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
	private JPanel p = new JPanel();
	private JButton b1 = new JButton("West"),
					b2 = new JButton("Conter"),
					b3 = new JButton("File"),
					b4 = new JButton("Help");
	public GUI() {
		super ("Esempietto"); //BorderLayout default LM per JFrame

		add(b1, BorderLayout.WEST);
		add(b2, BorderLayout.CENTER);

		p.add(b3);
		p.add(b4); //FlowLayout default LM per JPanel

		add(p, BorderLayout.NORTH);
		setSize(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	} 
	public static void main(String args[]) {
		GUI ref = new GUI();
	}
}

/*
I pulsanti "File", "Help" hanno dimensione fissa e sono su un pannello gestito con FlowLayout
I bottoni "West", "Center" hanno dimensione variabile e sono nel frame gestito con BorderLayout
*/

// 5.2.7
