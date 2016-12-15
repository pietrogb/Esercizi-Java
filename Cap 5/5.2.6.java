import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CL implements MouseListener {
	private JFrame f = new JFrame("Incredibile...");
	private JPanel p1 = new JPanel(),
				   p2 = new JPanel(),
				   p3 = new JPanel();
	private JPanel p = new JPanel();
	private CardLayout lm = new CardLayout();
	public CL() {
		p.setLayout(lm);
		p1.setBackground(Color.yellow);
		p2.setBackground(Color.green);
		p3.setBackground(Color.red);
		p.addMouseListener(this);
		p.add(p1, "1"); p.add(p2, "2"); p.add(p3, "3");
		f.add(p);
		lm.first(p); //all'inizio mostra il primo panel
		f.setSize(300,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	} //metodi di MouseListener
	public void mousePressed(MouseEvent e) {lm.first(p);}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {lm.next(p);}
	public void mouseExited(MouseEvent e) {lm.next(p);}

	public static void main(String args[]) {
		CL ref = new CL();
	}
}

// 5.2.6