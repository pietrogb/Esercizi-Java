/*
Definire una Gui composta da un pannello e tre vottoni etichettati rispettivamente Avvia, Pausa e Stop, la cui pressione soddisfi le sewguenti specifiche.
	- La pressione del tasto Avvia deve avviare un thread che ogni 30 millisecondi cambia il colore del pannello di sfondo. Se l'esecuzione del thread era stata sospesa, la pressione del tasto Avvia deve riattivare l'esecuzione del thread.
	- la pressione del tasto Pausa deve causare la sospensione dell'esecuzione del thread che fa cambiare il colore al pannello di sfondo. La pressione di questo tasto non deve causare attesa attiva.
	- la pressione del tasto Stop deve impostare lo stato di interruzione del thread che fa cambiare il colore al pannello di sfondo. Una volta interrotto, il thread deve terminare la sua esecuizone e non deve essere riattivabile con una successiva pressione del tasto Avvia
	- si ricorda che il metodo public void setBackbround(Color c)  della classe Component imposta a c il colore delllo sfondo del componente d'invocazione
	- si ricorda che il metodo public void interrupt() imposta a true lo stato di interruzione del thread di invocazione. Inoltre, se al momento d'invocazione t.interrupt() il tread t è bloccato in una chiamata di un metodo sleep o wait, allora t riceve una InterruptException ed il suo stato di interruzione viene reimpostato a false.
	- si ricorda che il metodo public boolead isInterrupted() testa se lo stato di interruzione di un thread è true
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorsFrame{
	public static void main(String[] args) {
		ButtonFrame frame = new ButtonFrame("Esercizio 7.11.5");
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
	T t = null;
	private JButton avvia = new JButton("Avvia");
	private JButton pausa = new JButton("Pausa");
	private JButton stop = new JButton("Stop");

	public Color randomColor(){
		return new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
	}

	class T extends Thread {
		private boolean runFlag = true;
		T() {start();}

		void invertiFlag() {runFlag = !runFlag; }

		public void run() {
			while(true) {
				try { sleep(299);}
				catch(InterruptedException e) {
					System.err.println("Interrupted");
				}
				if(runFlag && !isInterrupted()) setBackground(randomColor());
			}
		}
	}

	class Lstart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t == null) t = new T();
		}
	}

	class Lwait implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if((t != null) && !(t.isInterrupted())) 
				t.invertiFlag();
		}
	}

	class Lstop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t != null && !(t.isInterrupted())) {
				t.invertiFlag();
				t.notifyAll();
				t.interrupt();
			}
		}
	}

	public ButtonPanel() {
		add(avvia);
		add(pausa);
		add(stop);

		avvia.addActionListener(new Lstart());

		pausa.addActionListener(new Lwait());

		stop.addActionListener(new Lstop());
	}

	// Classe che gestisce la pressione del bottone. È una classe interna privata
	private class ColorAction implements ActionListener {
		Color backColor;
		ColorAction(Color c) {backColor = c;}
		public void actionPerformed(ActionEvent e) {
			setBackground(backColor);
		}
	}
}