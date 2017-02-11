
public class Esercizio {
	public static void Fun(JFrame f, AbstractButton b) {
		// se b è un JButton che non è un BasicAbstractButton
		if(b instaceof JButton) {
			if(b instaceof BasicAbstractButton)
				b.setDirection(SwingConstants.NORTH);
			else
				b.setText("pippo");
		}
		f.add(b);
	}
}

