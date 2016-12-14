interface Msg{
	String getDestinatario();
	String getDataPartenza();
	String getOraPartenza();
	String getMittente();
}

interface MsgWtText extends Msg{
	bool aggiungi();
	String toString();
}

class genericMsg implements Msg{
	private String destinatario;
	private String dataPartenza;
	private String oraPartenza;
	private String mittente;
	genericMsg((String dest, String dataP, String oraP, String mitt) {
		destinatario=new String(dest);
		dataPartenza=new String(dataP);
		oraPartenza = new String(oraP);
		mittente = new String(mitt);
	}
	public String getDestinatario() {return destinatario;}
	public String getDataPartenza() {return dataPartenza;}
	public String getOraPartenza() {return oraPartenza;}
	public String getMittente() {return  mittente;}
}

public class SMSNormale extends genericMsg implements MsgWtText{
	private String contenuto;
	public SMSNormale(String dest, String dataP, String oraP, String mitt, String text) {
		genericMsg(dest, dataP, oraP, mitt);
		if(text.length() <= 160)
			contenuto = new String(text);
	}
	bool aggiungi(char text) {
		if(contenuto.length < 160)
			{
				contenuto+=text;
				return true;
			}
		else return false;
	}
	String toString() {
		String ris();
		for(int i=0; i<text.length; i++) {
			ris+=text[i];
			if((i+1) % 25)
				ris+="\n";
		}
		return ris;
	}
}

class SMSLungo extends genericMsg{
	private String contenuto;
	public SMSLungo(String dest, String dataP, String oraP, String mitt, String text) {
		genericMsg(dest, dataP, oraP, mitt);
		contenuto = new String(text);
	}
	bool aggiungi(char text) {
		contenuto+=text;
		return true;
	}
	String toString() {
		String ris();
		for(int i=0; i<text.length; i++) {
			ris+=text[i];
			if((i+1) % 25)
				ris+="\n";
		}
		return ris;
	}
	public String[] spezza() {
		String[] res;
		for(int i=0; i<text.length; i++) {
			ris[(int)(i/160)]+=text[i];
			}
		}
		return ris;
	}
}

class msgVocale extends genericMsg{
	private byte[] audio;
	public msgVocale(String dest, String dataP, String oraP, String mitt, byte[] ad) {
		genericMsg(dest, dataP, oraP, mitt);
		audio=au;
	}
}