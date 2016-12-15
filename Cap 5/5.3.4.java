// GUI che traccia il movimento del mouse: viene individuata l'entrata ed uscita del mouse nella GUI (Mouse moving) e quando viene premuto il pulsante ne viene tracciato il movimento (mouse dragging)

// Questa versione usa le classi interne anonime, ottenendo una versione più compatta e leggibile

import javax.swing.*;
import java.awt.event.*;

public class Two extends JFrame{
	private JTextField tf = new JTextField(30);

	public Two() {
		super("Esempio con due listeners");
		add(new JLabel("Clicca e trascina"), "North");
		add(tf, "South");

		addMouseMotionListener( new MouseMotionAdapter(){
			public void mouseDragged (MouseEvent e) {
				String s = "Trascinamento del mouse drag: X= " + e.getX() + "Y= " + e.getY();
				tf.setText(s);
			}
		} );

		addMouseListener(new MouseAdapter {
			public void mouseEntered (MouseEvent e) {
				String s = "Il mouse è entrato";
				tf.setText(s); 
			}
			public void mouseExited(MouseEvent e) {
				String s = "Il mouse e' uscito";
				tf.setText(s);
			}
		} );

		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		Two ref = new Two();
	}
}
// Non funziona come dovrebbe
