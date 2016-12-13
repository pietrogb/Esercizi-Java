import javax.swing.*; import java.awt.event.*; import java.awt.*;

public class MyFrame extends JFrame {
	JButton myButton; JTextArea myTextArea;
	public MyFrame() {
		super("Inner Class Frame");
		myButton = new JButton("Premi");
		myTextArea = new JTextArea();
		add(myButton, BorderLayout.CENTER);
		add(myTextArea, BorderLayout.NORTH);
		ButtonListener bList = new ButtonListener();
		myButton.addActionListener(bList);
	}
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myTextArea.setText("bottone premuto");
		}
	}
	public static void main(String[] args) {
		MyFrame f = new MyFrame();
		f.setSize(300, 300);
		f.setVisible(true);
	}

}