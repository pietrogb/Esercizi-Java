// Esercizio pagg. 126-127-128
// Il metodo wait sospende il thread corrente mentre il metodo notify() risveglia il thread sospeso.

class BoundedBuffer{
	final Object[] items = new Object[100];
	int count, putptr, takeptr;

	public synchronized void put(Object o) throws InterruptedException{
		while(count == items.length) {
			System.out.println("attendo: buffer PIENO ");
			wait();
		}
		items[putptr] = o; putptr++;
		if(putptr == items.length) putptr = 0;
		count++;
		notify();
	}

	public synchronized Object take() throws InterruptedException {
		while(count==0) {
			System.out.print("attendo: buffer VUOTO ");
			wait();
		}
		Object o = items[takeptr]; takeptr++;
		if(takeptr==items.length) takeptr=0;
		count--;
		notify();
		return o;
	}
}

class Produttore extends Thread {
	BoundedBuffer b;
	Produttore(BoundedBuffer buff) {b=buff;}
	public void run() {int i=0;
		while(i<200)
			synchronized(b) {
				try{
					b.put("Pippo");
					System.out.println("messo numero "+i);
					i++;
				} catch(InterruptedException e) { }
				// catch(Pieno p) {System.out.println("Buffer Pieno")}
			} //aggiungi una sleep
	}
}

class Consumatore extends Thread {
	BoundedBuffer b;
	Consumatore(BoundedBuffer buff) {b=buff;}
	public void run() {int i=0;
		while(i<200)
			synchronized(b) {
				try{
					System.out.println("preso "+ b.take() + " numero "+i);
					i++;
				} catch(InterruptedException e) { }
				// catch(Vuoto p) {System.out.println("Buffer Vuoto")}
			} //aggiungi una sleep
	}
}

public class Buffer {
	public static void main(String[] args) {
		BoundedBuffer buff = new BoundedBuffer();
		Produttore prod = new Produttore(buff);
		Consumatore cons = new Consumatore(buff);
		prod.start(); cons.start();
	}
}

/*
Risultato:
	messo numero 0
	messo numero 1
	messo numero 2
	messo numero 3
	messo numero 4
	messo numero 5
	messo numero 6
	messo numero 7
	messo numero 8
	messo numero 9
	messo numero 10
	messo numero 11
	messo numero 12
	messo numero 13
	messo numero 14
	messo numero 15
	messo numero 16
	messo numero 17
	messo numero 18
	messo numero 19
	messo numero 20
	messo numero 21
	messo numero 22
	messo numero 23
	messo numero 24
	messo numero 25
	messo numero 26
	messo numero 27
	messo numero 28
	messo numero 29
	messo numero 30
	messo numero 31
	messo numero 32
	messo numero 33
	messo numero 34
	messo numero 35
	messo numero 36
	messo numero 37
	messo numero 38
	messo numero 39
	messo numero 40
	messo numero 41
	messo numero 42
	messo numero 43
	messo numero 44
	messo numero 45
	messo numero 46
	messo numero 47
	messo numero 48
	messo numero 49
	messo numero 50
	messo numero 51
	messo numero 52
	messo numero 53
	messo numero 54
	messo numero 55
	messo numero 56
	messo numero 57
	messo numero 58
	messo numero 59
	messo numero 60
	messo numero 61
	messo numero 62
	messo numero 63
	messo numero 64
	messo numero 65
	messo numero 66
	messo numero 67
	messo numero 68
	messo numero 69
	messo numero 70
	messo numero 71
	messo numero 72
	messo numero 73
	messo numero 74
	messo numero 75
	messo numero 76
	messo numero 77
	messo numero 78
	messo numero 79
	messo numero 80
	messo numero 81
	messo numero 82
	messo numero 83
	messo numero 84
	messo numero 85
	messo numero 86
	messo numero 87
	messo numero 88
	messo numero 89
	messo numero 90
	messo numero 91
	messo numero 92
	messo numero 93
	messo numero 94
	messo numero 95
	messo numero 96
	messo numero 97
	messo numero 98
	messo numero 99
	attendo: buffer PIENO 
	preso Pippo numero 0
	preso Pippo numero 1
	preso Pippo numero 2
	preso Pippo numero 3
	preso Pippo numero 4
	preso Pippo numero 5
	preso Pippo numero 6
	preso Pippo numero 7
	preso Pippo numero 8
	preso Pippo numero 9
	preso Pippo numero 10
	preso Pippo numero 11
	preso Pippo numero 12
	preso Pippo numero 13
	preso Pippo numero 14
	preso Pippo numero 15
	preso Pippo numero 16
	preso Pippo numero 17
	preso Pippo numero 18
	preso Pippo numero 19
	preso Pippo numero 20
	preso Pippo numero 21
	preso Pippo numero 22
	preso Pippo numero 23
	preso Pippo numero 24
	preso Pippo numero 25
	preso Pippo numero 26
	preso Pippo numero 27
	preso Pippo numero 28
	preso Pippo numero 29
	preso Pippo numero 30
	preso Pippo numero 31
	preso Pippo numero 32
	preso Pippo numero 33
	preso Pippo numero 34
	preso Pippo numero 35
	preso Pippo numero 36
	preso Pippo numero 37
	preso Pippo numero 38
	preso Pippo numero 39
	preso Pippo numero 40
	preso Pippo numero 41
	preso Pippo numero 42
	preso Pippo numero 43
	preso Pippo numero 44
	preso Pippo numero 45
	preso Pippo numero 46
	preso Pippo numero 47
	preso Pippo numero 48
	preso Pippo numero 49
	preso Pippo numero 50
	preso Pippo numero 51
	preso Pippo numero 52
	preso Pippo numero 53
	preso Pippo numero 54
	preso Pippo numero 55
	preso Pippo numero 56
	preso Pippo numero 57
	preso Pippo numero 58
	preso Pippo numero 59
	preso Pippo numero 60
	preso Pippo numero 61
	preso Pippo numero 62
	preso Pippo numero 63
	preso Pippo numero 64
	preso Pippo numero 65
	preso Pippo numero 66
	preso Pippo numero 67
	preso Pippo numero 68
	preso Pippo numero 69
	preso Pippo numero 70
	preso Pippo numero 71
	preso Pippo numero 72
	preso Pippo numero 73
	preso Pippo numero 74
	preso Pippo numero 75
	preso Pippo numero 76
	preso Pippo numero 77
	preso Pippo numero 78
	preso Pippo numero 79
	preso Pippo numero 80
	preso Pippo numero 81
	preso Pippo numero 82
	preso Pippo numero 83
	preso Pippo numero 84
	preso Pippo numero 85
	preso Pippo numero 86
	preso Pippo numero 87
	preso Pippo numero 88
	preso Pippo numero 89
	preso Pippo numero 90
	preso Pippo numero 91
	preso Pippo numero 92
	preso Pippo numero 93
	preso Pippo numero 94
	preso Pippo numero 95
	preso Pippo numero 96
	preso Pippo numero 97
	preso Pippo numero 98
	preso Pippo numero 99
	attendo: buffer VUOTO messo numero 100
	messo numero 101
	messo numero 102
	messo numero 103
	messo numero 104
	messo numero 105
	messo numero 106
	messo numero 107
	messo numero 108
	messo numero 109
	messo numero 110
	messo numero 111
	messo numero 112
	messo numero 113
	messo numero 114
	messo numero 115
	messo numero 116
	messo numero 117
	messo numero 118
	messo numero 119
	messo numero 120
	messo numero 121
	messo numero 122
	messo numero 123
	messo numero 124
	messo numero 125
	messo numero 126
	messo numero 127
	messo numero 128
	messo numero 129
	messo numero 130
	messo numero 131
	messo numero 132
	messo numero 133
	messo numero 134
	messo numero 135
	messo numero 136
	messo numero 137
	messo numero 138
	messo numero 139
	messo numero 140
	messo numero 141
	messo numero 142
	messo numero 143
	messo numero 144
	messo numero 145
	messo numero 146
	messo numero 147
	messo numero 148
	messo numero 149
	messo numero 150
	messo numero 151
	messo numero 152
	messo numero 153
	messo numero 154
	messo numero 155
	messo numero 156
	messo numero 157
	messo numero 158
	messo numero 159
	messo numero 160
	messo numero 161
	messo numero 162
	messo numero 163
	messo numero 164
	messo numero 165
	messo numero 166
	messo numero 167
	messo numero 168
	messo numero 169
	messo numero 170
	messo numero 171
	messo numero 172
	messo numero 173
	messo numero 174
	messo numero 175
	messo numero 176
	messo numero 177
	messo numero 178
	messo numero 179
	messo numero 180
	messo numero 181
	messo numero 182
	messo numero 183
	messo numero 184
	messo numero 185
	messo numero 186
	messo numero 187
	messo numero 188
	messo numero 189
	messo numero 190
	messo numero 191
	messo numero 192
	messo numero 193
	messo numero 194
	messo numero 195
	messo numero 196
	messo numero 197
	messo numero 198
	messo numero 199
	preso Pippo numero 100
	preso Pippo numero 101
	preso Pippo numero 102
	preso Pippo numero 103
	preso Pippo numero 104
	preso Pippo numero 105
	preso Pippo numero 106
	preso Pippo numero 107
	preso Pippo numero 108
	preso Pippo numero 109
	preso Pippo numero 110
	preso Pippo numero 111
	preso Pippo numero 112
	preso Pippo numero 113
	preso Pippo numero 114
	preso Pippo numero 115
	preso Pippo numero 116
	preso Pippo numero 117
	preso Pippo numero 118
	preso Pippo numero 119
	preso Pippo numero 120
	preso Pippo numero 121
	preso Pippo numero 122
	preso Pippo numero 123
	preso Pippo numero 124
	preso Pippo numero 125
	preso Pippo numero 126
	preso Pippo numero 127
	preso Pippo numero 128
	preso Pippo numero 129
	preso Pippo numero 130
	preso Pippo numero 131
	preso Pippo numero 132
	preso Pippo numero 133
	preso Pippo numero 134
	preso Pippo numero 135
	preso Pippo numero 136
	preso Pippo numero 137
	preso Pippo numero 138
	preso Pippo numero 139
	preso Pippo numero 140
	preso Pippo numero 141
	preso Pippo numero 142
	preso Pippo numero 143
	preso Pippo numero 144
	preso Pippo numero 145
	preso Pippo numero 146
	preso Pippo numero 147
	preso Pippo numero 148
	preso Pippo numero 149
	preso Pippo numero 150
	preso Pippo numero 151
	preso Pippo numero 152
	preso Pippo numero 153
	preso Pippo numero 154
	preso Pippo numero 155
	preso Pippo numero 156
	preso Pippo numero 157
	preso Pippo numero 158
	preso Pippo numero 159
	preso Pippo numero 160
	preso Pippo numero 161
	preso Pippo numero 162
	preso Pippo numero 163
	preso Pippo numero 164
	preso Pippo numero 165
	preso Pippo numero 166
	preso Pippo numero 167
	preso Pippo numero 168
	preso Pippo numero 169
	preso Pippo numero 170
	preso Pippo numero 171
	preso Pippo numero 172
	preso Pippo numero 173
	preso Pippo numero 174
	preso Pippo numero 175
	preso Pippo numero 176
	preso Pippo numero 177
	preso Pippo numero 178
	preso Pippo numero 179
	preso Pippo numero 180
	preso Pippo numero 181
	preso Pippo numero 182
	preso Pippo numero 183
	preso Pippo numero 184
	preso Pippo numero 185
	preso Pippo numero 186
	preso Pippo numero 187
	preso Pippo numero 188
	preso Pippo numero 189
	preso Pippo numero 190
	preso Pippo numero 191
	preso Pippo numero 192
	preso Pippo numero 193
	preso Pippo numero 194
	preso Pippo numero 195
	preso Pippo numero 196
	preso Pippo numero 197
	preso Pippo numero 198
	preso Pippo numero 199
*/