// GUI multithread

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Counter2 extends JFrame {
	private class T extends Thread {
		private int count = 0;
		private boolean runFlag = true;
		T() {start();}

		void invertiFlag() {runFlag = !runFlag; }

		public void run() {
			while(true) {
				try { sleep(100);}
				catch(InterruptedException e) {
					System.err.println("Interrupted");
				}
				if(runFlag) tf.setText(Integer.toString(count++));
			}
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

	public Counter2() {
		super("Multithreaded GUI");
		setLayout(new FlowLayout()); add(tf);
		start.addActionListener(new Lstart()); add(start);
		ferma.addActionListener(new Lferma()); add(ferma);
		setSize(300, 100); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		Counter2 r = new Counter2();
	}
}

// 7.8.4