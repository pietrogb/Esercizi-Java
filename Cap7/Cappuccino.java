/*
preparazione di un cappuccino sequenziale e concorrente.
	-nel codice sono state eliminate le necessarie gestioni delle eccezioni
	*/

import java.util.concurrent.*;
import java.util.*;

public class Cappuccino{
	private static String macina(String s) {
		System.out.println("inizia a macinare");
		try{
			Thread.sleep((int)(Math.random()*100));
		} catch(InterruptedException e) {System.err.println("Interrupted");}
		System.out.println("macinati i grani");
		if(s.equals("caffe"))
			return "macinato";
		else return "bad coffee";
	}

	private static Integer bollire() {
		System.out.println("scaldo l'acqua");
		try{
			Thread.sleep((int)(Math.random()*160));
		} catch(InterruptedException e) {System.err.println("Interrupted");}
		System.out.println("acqua bollente");
		return 100;
	}

	private static String faiCaffe(String g, Integer a) {
		System.out.println("inizio a fare il caffe");
		try {
			Thread.sleep((int)(Math.random()*100));
		} catch(InterruptedException e) {System.err.println("Interrupted");}
		System.out.println("faccio il caffe' con " + g + " e acqua a " + a + "gradi");
		return "caffe";
	}

	private static String faiSchiuma(String l) {
		System.out.println("Inizio a fare schiuma");
		try{
			Thread.sleep((int)(Math.random()*160));
		} catch(InterruptedException e) {System.err.println("Interrupted");}
		System.out.println("fatta schiuma di " + l);
		return "schiuma";
	}

	private static String mescola(String c, String l) {
		try{
			Thread.sleep((int)(Math.random()*100));
		} catch(InterruptedException e) {System.err.println("Interrupted");}
		System.out.println("aggiunto " + l + "sopra " +c);
		return "cappuccino";
	}

	private static Callable<String> macinaC(final String s) {
		return new Callable<String>() {
			public String call() {return macina(s);}
		};
	}

	private static Callable<Integer> bollireC() {
		return new Callable<Integer>() {
			public Integer call() {return bollire();}
		};
	}

	private static Callable<String> faiCaffeC (final Future<String> g, final Future<Integer> a) {
		return new Callable<String>() {
			// errore sulle get che possono sollevare InterruptedException
			public String call() {return faiCaffe(g.get(), a.get());}
		};
	}

	private static Callable<String> faiSchiumaC(final String l) {
		return new Callable<String>() {
			public String call() {return faiSchiuma(l);}
		};
	}

	static void cappuccinoSeq() {
		String s = macina("caffe");
		int i = bollire();
		String c = faiCaffe(s, i);
		String l = faiSchiuma("latte");
		String p = mescola(c, l);
		System.out.println("pronto il " + p);
	}

	static void cappuccinoPar() {
		ExecutorService pool = Executors.newCachedThreadPool();

		Future<String> s = pool.submit(macinaC("caffe"));
		Future<Integer> i = pool.submit(bollireC());
		Future<String> c = pool.submit(faiCaffeC(s, i));
		Future<String> l = pool.submit(faiSchiumaC("latte"));
		// errore sulle get che possono sollevare InterruptedException
		String p = mescola(c.get(), l.get());
		System.out.println("pronto il " +p);
	}
}

// 7.10.1 - pagina 137