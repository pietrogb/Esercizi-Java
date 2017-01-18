/*// Il programma stampa per 100 volte il valore del contatore gestito dal thread demone

class Daemon extends Thread {
	public int i = 0;
	public Daemon() {
		setDaemon(true);
	}
	public void run() {
		// ciclo infinito
		while(true) { ++i; if(i>10000000) i=0;}
	}
}

public class D extends Thread {
	Daemon dm = new Daemon();
	private int j = 0;
	public void run() {
		dm.start(); //start() del daemon
		while(j<100) {
			++j; System.out.print(dm.i + " ");
			//servizio fornito dal daemon
		}
	}
	public static void main(String[] args) {
		D d = new D(); 
		d.start();
	}
}*/

// GUI multithread

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;

public class Counter2 extends JFrame {
	private class T extends Thread {
		private int count = 0;
		private boolean runFlag = true;
		T() {start();}

		void invertiFlag() {runFlag = !runFlag; }

		public void run() {
			while(count<100) {
				try { sleep(100);}
				catch(InterruptedException e) {
					System.err.println("Interrupted");
				}
				if(runFlag) tf.setText(Integer.toString(count++));
			}
			if(count==100)
				LStampa= new LStampa();
		}
	} //fine class T

	private T t = null; //Thread che gestisce il contatore
	private JTextField tf = new JTextField(10);
	private JButton start = new JButton("Start");
	private JButton ferma = new JButton("Ferma");

	class Lstart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t == null) t = new T();
		}
	}
	class Lferma implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(t != null) t.invertiFlag();
		}
	}

	class LStampa implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			label = new JLabel("Stop");
		}
	}

	public Counter2() {
		super("Multithreaded GUI");
		JLabel label = new JLabel("Hello World");
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVisible(false);
		setLayout(new FlowLayout()); add(tf);
		start.addActionListener(new Lstart()); add(start);
		ferma.addActionListener(new Lferma()); 
		ferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                  label.setVisible(true);
            }
        });
        add(ferma);
        add(label);
		setSize(300, 200); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		Counter2 r = new Counter2();
	}
}