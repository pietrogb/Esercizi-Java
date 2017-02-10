// 10.0.22

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Semaforo extends JFrame {
	JPanel titolo = new JPanel (new FlowLayout());

	Luci luci = new Luci();
	Thread luciThread;

	JPanel bottoni = new JPanel(new FlowLayout());
	JButton start = new JButton("Start");
	JButton walk = new JButton("Avanti");
	JButton stop = new JButton("Stop");

	public Semaforo() {
		// costrisci la parte alta del frame: un'etichetta
		titolo.add(new JLabel("Animazione Semaforo"));
		add(BorderLayout.NORTH, titolo);

		// costruisci la parte centrale del frame: un pannello speciale
		add(BorderLayout.CENTER, luci);

		class WalkHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				luci.setChiamataPedone();
			}
		}

		class StartHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(luciThread == null)
					luciThread = new Thread(luci);
				else {
					luciThread.interrupt();
					luciThread = new Thread(luci);
				}
				luciThread.start();
			}
		}

		class StopHandler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				if(!luciThread.isInterrupted())
					luciThread.interrupt();
			}
		}

		// costruisci la parte bassa del frame: tre bottoni
		 start.addActionListener(new StartHandler());
		walk.addActionListener(new WalkHandler());
		stop.addActionListener(new StopHandler());

		bottoni.add(start); bottoni.add(walk); bottoni.add(stop);
		add(BorderLayout.SOUTH, bottoni);

		setSize(300,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Semaforo();
	}
}

class Luci extends JPanel implements Runnable {
	public final static int ROSSO = 0;
	public final static int VERDE = 1;
	public final static int GIALLO = 2;

	int attuale = -1;
	boolean chiamataPedone = false;
	boolean lampeggia = false;

	public void run() {
		//devono cioè essere accese le luci nella giusta sequenza rosso->verde->giallo; inoltre, quando il semaforo è rosso, se c'è stata una chiamata pedonale, oltre alla luce gialla deve lampeggiare il dischetto "avanti".
		while(true) {
			if(chiamataPedone == true){
				for(int i=0; i<10; ++i){
					lampeggia=(i%2 == 0);
					try{
						Thread.sleep(200);
					} catch (InterruptedException e) {accendi(-1); return;}
					accendi(0);
				}
				chiamataPedone=false;
			}
			else{
				accendi(0);
				try{
					Thread.sleep(2000);
				} catch (InterruptedException e) {accendi(-1); return;}
			}
			accendi(1);
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e) {accendi(-1); return;}
			accendi(2);
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e) {accendi(-1); return;}
			if(Thread.interrupted()) System.out.println("interrotto");
		}
	}

	public void setChiamataPedone() {
		chiamataPedone = true;
	}

	void accendi(int i) {
		attuale = i;
		repaint();
	}

	// disegna il contenuto del pannello
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// disegna 4 stringhe, un retangolo e 4 dischetti grigi
		g.drawRect(97,10,30,68);
		g.drawString("ROSSO", 15, 28);
		g.drawString("GIALLO", 15, 48);
		g.drawString("VERDE", 15, 68);
		g.drawString("AVANTI", 15, 98);

		g.setColor(Color.lightGray);
		g.fillOval(105,15,15,15);
		g.fillOval(105,35,15,15);
		g.fillOval(105,55,15,15);
		g.fillOval(105,85,15,15);

		// ricolora in modo opportuno il dischetto "avanti"
		if(chiamataPedone) {
			if(lampeggia) g.setColor(Color.green);
			else g.setColor(Color.lightGray);
			g.fillOval(105,85,15,15);
		}
		else {
			g.setColor(Color.lightGray);
			g.fillOval(105, 85, 15, 15);
		}

		//aggiorna il colore del Semaforo
		switch(attuale) {
			case ROSSO: {
				g.setColor(Color.red); g.fillOval(105,15,15,15);
				g.setColor(Color.lightGray);
				g.fillOval(105,35,15,15); g.fillOval(105,55,15,15); 
				break;
			}

			case VERDE: {
				g.setColor(Color.green); g.fillOval(105,55,15,15);
				g.setColor(Color.lightGray);
				g.fillOval(105,15,15,15); g.fillOval(105,35,15,15);
				break;
			}

			case GIALLO: {
				g.setColor(Color.yellow); g.fillOval(105,35,15,15);
				g.setColor(Color.lightGray);
				g.fillOval(105,15,15,15); g.fillOval(105, 55, 15, 15);
				break;
			}
		}
	}
}

/*
1. definire il comportamento del bottone avanti in modo che alla pressione del tasto venga invocato il metodo setChiamataPedone() dell'oggetto luci
2. definire il comportamento del bottone Start in modo che alla PRIMA pressione di questo tasto venga creato (assegnandolo al campo luciThread) ed avviato un nuovo thread che anima il semaforo. Alle successive pressioni del taswto Start, il thread riferito dal campo luciThread deve essere interrotto, ed un nuvoo thread dev'essere avviato
3. definire il comportamento del bottone Stop in modo che alla pressione di questo tasto il thread di animazione del semaforo venga fermato
4. definire il metodo run della classe Luci in modo da modellare il comporamento di un semaforo. Devono cioè essere accese le luci nella giusta sequenza rosso->verde->giallo; inoltre, quando il semaforo è rosso, se c'è stata una chiamata pedonale, oltre alla luce gialla deve lampeggiare il dischetto "avanti". [per gestire il colore ed il lampeggiamento del semaforo, usare chiamate del metodo accendi destendo in modo opportuno il flag LAMPEGGIA].
*/