// Un frame con due bottoni che - se pigiati - cambianpo il colore dello sfondo. 
// Uno dei due bottoni, cambia colore se il mouse vi passa sopra
//5.3.2
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Colors{
	public static void main(String[] args) {
		ButtonFrame frame = new ButtonFrame("Due bottoni diversi");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//Il frame grafico contiene solo un pannello di bottoni
class ButtonFrame extends JFrame {
	public ButtonFrame(String s) {
		super(s);
		setSize(400, 400);
		ButtonPanel panel = new ButtonPanel();
		add(panel);
	}
}


// il pannello contiene i bottoni e le azioni legate alla loro pressione

class ButtonPanel extends JPanel {
	public ButtonPanel() {
		JButton button1 = new JButton("giallo");
		JButton button2 = new JButton("verde");
		add(button1);
		add(button2);

		ActionListener yellowAction = new ColorAction(Color.YELLOW);
		button1.addActionListener(yellowAction);

		ActionListener greenAction = new ColorAction(Color.GREEN);
		MouseMotionListener h1Enter = new HighlightMouseEnter();
		MouseListener h1Exit = new HighlightMouseExit();
		button2.addActionListener(greenAction);
		button2.addMouseMotionListener(h1Enter);
		button2.addMouseListener(h1Exit);
	}

	// Classe che gestisce la pressione del bottone. È una classe interna privata
	private class ColorAction implements ActionListener {
		Color backColor;
		ColorAction(Color c) {backColor = c;}
		public void actionPerformed(ActionEvent e) {
			setBackground(backColor);
		}
	}

	// classe che gestisce l'uscita del mouse fuori dal bottone
	private class HighlightMouseExit extends MouseAdapter {
		public void MouseExited(MouseEvent e) {
			JButton button = (JButton) e.getComponent();
			button.setBackground(Color.BLUE);
		}
	}

	// classe che gestisce il moviemento del mouse sopra il bottone
	private class HighlightMouseEnter extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			JComponent c = (JComponent) e.getComponent();
			c.setBackground(Color.RED);
		}
	}
} 
// Chiude BottonPanel


// Non funziona il ritorno al colore blue quando il mouse si sposta fuori dal bottone.