import javax.swing.*;
import java.awt.*;

public class MyFlow {
	private JFrame f = new JFrame("FlowLayout");
	private JButton b1 = new JButton("schiaccia"),
					b2 = new JButton("premi"),
					b3 = new JButton("spingi");
	public MyFlow() {
		f.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));
		f.setSize(200, 200);
		f.add(b1); f.add(b2); f.add(b3);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		MyFlow ref = new MyFlow();
	}
}

/*Le posizioni dei tre buttoni cambiano con la dimensione del frame*/