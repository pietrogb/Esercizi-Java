/*
Si considerino un tipo T1<Serializable> ed un tipo T2<Remote>
Si consideri la seguente classe:
*/

public class C extends UnicastRemoteObject implements Remote {
	public C() throws RemoteException {/*...*/}

	public synchronized T1 m(T1 t) throws RemoteException {/*...*/}
	public synchronized T2 n(T2 s) throws RemoteException {/*...*/}
}

/*
	T1 ha tipo che estende Serializable, di conseguenza l'oggetto riferito da o2 viene serializzato e spedito ad o1. A causa della serializzazione, il metodo remoto si trova ad operare su un parametro che è una copia dell'oggetto riferito da p2. La stessa cosa avviene per il valore restituito dall'oggetto remoto.

	T2 estende il tipo Remote, significa che p2 è un riferimento ad un oggetto che potrebbe essere allocato in una JVM remota, di cui localmente si possiede solo uno stub. Ciò che viene dunque spedito come parametro è un riferimento remoto all'oggetto riferito da o2, cioè un identificatore di quell'oggetto, o stub, completo del nome dell'host che lo mantiene. In questo modo la JVM  remota che contiene l'oggetto riferito da o1, usa il riferimento remoto ottenuto come parametro per creare localmente uno stub all'oggetto riferito da o2.

	RIASSUMENDO. Per i parametri ed i valori di ritorno di tipo primitivo o di tipo Serializable viene trasmessa una copia, mentre gli oggetti di tipo Remote vengono passati come riferimenti remoti.
	La trasmissione di oggetti che hanno un tipo che non estende nè l'interfaccia Serializable nè l'interfaccia Remote determina un errore di tipo.
	~Visto che gli oggetti di C chiamano m su oggetti di T2, sul server viene creato un nuovo oggetto remoto e quello sul client rimane in attesa del valore di ritorno; questo determina il fatto che se il client richiede il lock su un oggetto remoto, come in questo caso in un blocco di codice sincronizzato su un oggetto remoto, viene in realtà utilizzato il lock sull'oggetto locale

Rispondere alle seguenti domande
1. Discutere in quali casi le invocazioni dei metodo m e n su un oggetto di classe C non interferiscono tra loro a causa del fatto che si tratta di metodi sincronizzati.
Indicate quindi quali garanzie di non interferenza offre in questo caso la keyword synchronized
	Le garanzie di non interferenza sono standard anche se sono metodi di una classe che implementa Remote. Infatti se i due metodi agiscono sullo stesso oggetto C uno aspetta il rilascio del lock dell’altro.

2. Il tipo dei parametri/ valori di ritorno dei metodi m e n influisce sulle loro possibilità di interferire?
	No, interferisce solo l'oggetto che invoca i metodi.

3. Sarebbe possibile scambiare dati di tipo T1 mediante una comunicazione tramite socket? E dati di tipo T2?
	I dati di tipo T1 possono facilmente essere passati tramite socket, quelli di T2 devono essere serializzati prima d'essere inviati e deserializzati dopo essere stati ricevuti. 

4. È possibile che il codice di un metodo remoto contenga delle sincronizzazioni su oggetti di tipo T2? E su oggetti di tipo T1?
	Su oggetti di tipo T1 no, visto che implementando Serializable ne viena trasmessa una copia; è possibile invece implemenetarla con oggetti di T1, i quali vengono invece trasmessi come riferimenti.
*/