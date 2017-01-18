/*
Rotatoria con quattro strade di accesso. Situazione in cui quattro aut osi stanno dirigendo contemoranenamente, ma a velocità diverse, verso la rotatoria con l'intenzione di proseguire nella propria direzione di marcia. Ogni auto intende entrare nella rotatoria ed uscire alla seconda uscita a destra.
Completare il programma seguente.
Garantire che: quando un auto A arriva alla rotatoria, se la rotatoria è già stata impegnata dall'auto B proveniente dalla strada sulla sinistra, allora A prima d'entrare, attende che B sia uscita. Se la rotatoria è stata impegnata da qualsiasi altra delle due direzioni, A deve poter entrare nella rotatoria senza aspettare.
*/

class Auto extends Thread{
	private  int distanza;
	private Rotatoria r;
	private int direzione; //Nord=0; Est=1; Sud=2; Ovest=3;

	Auto(int d, rotatoria rr, int dd){
		distanza=d; r=rr; direzione=dd;
	}

	public void run(){
		try{while(distanza>0)
			distanza--;
			sleep(((int)Math.random()*50));
		}
		// ora l'auto è all'imbocco della rotatoria
		r.entraRotatoria
	}
}