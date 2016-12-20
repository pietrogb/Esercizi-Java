// GUI multithread
//  Codice con attesa attiva. Obiettivo: togliere l'attesa attiva e testo stop per interruzione
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Counter2 extends JFrame {
	private class Tl extends Thread {
		private int count = 0;
		private boolean runFlag = true;
		Tl() {start();}

		
		void ferma() {runFlag=false;}
		void riavvia() {runFlag = true;}

		public void run() {
			try{
				while(!isInterrupted()) sleep(100);
				synchronized(this) {
					while(!runFlag) wait();
					tf.setText(Integer.toString(count++));
				}
			} catch(InterruptedException e) {System.err.println("Interrupted");}
		}
	} //fine class T

	private Tl t = null; //Thread che gestisce il contatore
	private JTextField tf = new JTextField(10);
	private JButton start = new JButton("Start");
	private JButton ferma = new JButton("Ferma");
	private JButton stop = new JButton("Stop");

	class Lstart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t == null) 
				t = new Tl(); //Avvia il contatore
			else {
				synchronized(t) {
					t.riavvia();
					t.notify();
				}
			}
		}
	}
	class Lferma implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t != null)
				synchronized(t) { t.ferma(); } //runFlag è soggetto a data Race -> tutti gli accessi vanno protetti lato client
		}
	}

	class Lstop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t != null)
				synchronized(t) { t.interrupt(); }
		}
	}

	public Counter2() {
		super("Multithreaded GUI");
		setLayout(new FlowLayout()); add(tf);
		start.addActionListener(new Lstart()); add(start);
		ferma.addActionListener(new Lferma()); add(ferma);
		stop.addActionListener(new Lstop()); add(stop);
		setSize(300, 100); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		Counter2 r = new Counter2();
	}
}

// 7.8.4