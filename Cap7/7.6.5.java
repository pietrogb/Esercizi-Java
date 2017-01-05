// Il seguente codice ha una grande probabilità di sospendersi per un problema di deadlock. Usando sulla shell la combinazione di tasti Ctrl+\ s'ottendono informazioni sullo stato dei thread sospesi
class B {
	public synchronized void m(C c) {
		for(int k=0; k<1000000; k++) ;
			c.m(this);
	}
}

class C {
	public synchronized void m(B b)  {
		for(int k=0; k<1000000; k++) ;
	b.m(this);
	}
}

class T1 extends Thread {
	private B b; private C c;
	public T1(B y, C z) {b=y; c=z;}
	public void run() {
		b.m(c); c.m(b);
		System.out.println("Terminazione T1");
	}
}

class T2 extends Thread {
	private B b; private C c;
	public T2(B y, C z) {b=y; c=z;}
	public void run() {
		c.m(b); b.m(c);
		System.out.println("Terminazione T2");
	}
}

public class D {
	public static void main(String[] args) {
		C c = new C(); B b = new B();
		T1 t1 = new T1(b, c); T2 t2 = new T2(b, c);
		t1.start(); t2.start();
	}
}

// 7.6.5

/*
Risultato: 
		^\2016-12-19 11:43:27
	Full thread dump OpenJDK 64-Bit Server VM (25.111-b14 mixed mode):

	"DestroyJavaVM" #11 prio=5 os_prio=0 tid=0x00007f473c009800 nid=0xdf0 waiting on condition [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"Thread-1" #10 prio=5 os_prio=0 tid=0x00007f473c121000 nid=0xdff waiting for monitor entry [0x00007f4728cfe000]
	   java.lang.Thread.State: BLOCKED (on object monitor)
		at B.m(D.java:4)
		- waiting to lock <0x000000076d05f140> (a B)
		at C.m(D.java:12)
		- locked <0x000000076d05da70> (a C)
		at T2.run(D.java:29)

	"Thread-0" #9 prio=5 os_prio=0 tid=0x00007f473c11f800 nid=0xdfe waiting for monitor entry [0x00007f4728dff000]
	   java.lang.Thread.State: BLOCKED (on object monitor)
		at C.m(D.java:11)
		- waiting to lock <0x000000076d05da70> (a C)
		at B.m(D.java:5)
		- locked <0x000000076d05f140> (a B)
		at T1.run(D.java:20)

	"Service Thread" #8 daemon prio=9 os_prio=0 tid=0x00007f473c0b9000 nid=0xdfc runnable [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"C1 CompilerThread2" #7 daemon prio=9 os_prio=0 tid=0x00007f473c0b1800 nid=0xdfb waiting on condition [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"C2 CompilerThread1" #6 daemon prio=9 os_prio=0 tid=0x00007f473c0b0000 nid=0xdfa waiting on condition [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"C2 CompilerThread0" #5 daemon prio=9 os_prio=0 tid=0x00007f473c0ad000 nid=0xdf9 waiting on condition [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"Signal Dispatcher" #4 daemon prio=9 os_prio=0 tid=0x00007f473c0ab000 nid=0xdf8 waiting on condition [0x0000000000000000]
	   java.lang.Thread.State: RUNNABLE

	"Finalizer" #3 daemon prio=8 os_prio=0 tid=0x00007f473c083800 nid=0xdf7 in Object.wait() [0x00007f4729816000]
	   java.lang.Thread.State: WAITING (on object monitor)
		at java.lang.Object.wait(Native Method)
		- waiting on <0x000000076d008e98> (a java.lang.ref.ReferenceQueue$Lock)
		at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
		- locked <0x000000076d008e98> (a java.lang.ref.ReferenceQueue$Lock)
		at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
		at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

	"Reference Handler" #2 daemon prio=10 os_prio=0 tid=0x00007f473c07f000 nid=0xdf6 in Object.wait() [0x00007f4729917000]
	   java.lang.Thread.State: WAITING (on object monitor)
		at java.lang.Object.wait(Native Method)
		- waiting on <0x000000076d006b40> (a java.lang.ref.Reference$Lock)
		at java.lang.Object.wait(Object.java:502)
		at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
		- locked <0x000000076d006b40> (a java.lang.ref.Reference$Lock)
		at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

	"VM Thread" os_prio=0 tid=0x00007f473c077000 nid=0xdf5 runnable 

	"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00007f473c01f000 nid=0xdf1 runnable 

	"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x00007f473c020800 nid=0xdf2 runnable 

	"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x00007f473c022800 nid=0xdf3 runnable 

	"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x00007f473c024000 nid=0xdf4 runnable 

	"VM Periodic Task Thread" os_prio=0 tid=0x00007f473c0bb800 nid=0xdfd waiting on condition 

	JNI global references: 12


	Found one Java-level deadlock:
	=============================
	"Thread-1":
	  waiting to lock monitor 0x00007f4700004e28 (object 0x000000076d05f140, a B),
	  which is held by "Thread-0"
	"Thread-0":
	  waiting to lock monitor 0x00007f47000062c8 (object 0x000000076d05da70, a C),
	  which is held by "Thread-1"

	Java stack information for the threads listed above:
	===================================================
	"Thread-1":
		at B.m(D.java:4)
		- waiting to lock <0x000000076d05f140> (a B)
		at C.m(D.java:12)
		- locked <0x000000076d05da70> (a C)
		at T2.run(D.java:29)
	"Thread-0":
		at C.m(D.java:11)
		- waiting to lock <0x000000076d05da70> (a C)
		at B.m(D.java:5)
		- locked <0x000000076d05f140> (a B)
		at T1.run(D.java:20)

	Found 1 deadlock.

	Heap
	 PSYoungGen      total 74752K, used 3871K [0x000000076d000000, 0x0000000772300000, 0x00000007c0000000)
	  eden space 64512K, 6% used [0x000000076d000000,0x000000076d3c7cc8,0x0000000770f00000)
	  from space 10240K, 0% used [0x0000000771900000,0x0000000771900000,0x0000000772300000)
	  to   space 10240K, 0% used [0x0000000770f00000,0x0000000770f00000,0x0000000771900000)
	 ParOldGen       total 171008K, used 0K [0x00000006c7000000, 0x00000006d1700000, 0x000000076d000000)
	  object space 171008K, 0% used [0x00000006c7000000,0x00000006c7000000,0x00000006d1700000)
	 Metaspace       used 2505K, capacity 4492K, committed 4864K, reserved 1056768K
	  class space    used 267K, capacity 388K, committed 512K, reserved 1048576K


*/