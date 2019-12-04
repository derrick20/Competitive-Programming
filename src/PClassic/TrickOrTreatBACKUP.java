/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;
import java.math.BigInteger;
public class TrickOrTreatBACKUP {
	public static void main(String[] args) throws IOException{
//		BufferedReader br = new BufferedReader(new FileReader("/Users/derrick/IntelliJProjects/src/PClassic/TrickOrTreatIN.txt"));
		BufferedReader br = new BufferedReader(new FileReader("TrickOrTreatIN.txt"));

		//BufferedWriter bw = new BufferedWriter(new FileWriter("files/kgout.txt"));
		while (br.ready()) {
			String s = br.readLine();
			if (!s.trim().isEmpty()) {
				String[] data = s.split(" ");
				int numWizards = Integer.parseInt(data[0]);

				data = br.readLine().split(" ");
				int kL = Integer.parseInt(data[0]);
				int kR = Integer.parseInt(data[1]);
				int[][] candies = new int[numWizards][2];
				for (int i = 0; i < numWizards; i++) {
					data = br.readLine().split(" ");
					candies[i][0] = Integer.parseInt(data[0]);
					candies[i][1] = Integer.parseInt(data[1]);
				}
				System.out.println(trickOrTreat(kL,kR,candies));
			}
		}
		br.close();
		//bw.close();
	}

	public static String trickOrTreat(int kL, int kR, int[][] candies) {
		ArrayList<Pair> sorted = new ArrayList<>();
		for (int i = 0; i < candies.length; i++) {
			sorted.add(new Pair(candies[i][0], candies[i][1]));
		}
		Collections.sort(sorted);
		BigInteger prod = new BigInteger("" +  kL);
		BigInteger worst = BigInteger.ZERO;
		for (int i = 0; i < candies.length; i++) {
			BigInteger alt = prod.divide(new BigInteger("" + candies[i][1]));
			if (worst.compareTo(alt) < 0) {
				worst = alt;
			}
			prod = prod.multiply(new BigInteger("" + candies[i][0]));
		}
		return worst.toString();
	}

	static class Pair implements Comparable<Pair> {
		long a, b;
		public Pair(long myA, long myB) {
			a = myA; b = myB;
		}
		public int compareTo(Pair p2) {
			long maxA = Math.max(p2.b, a * b);
			long maxB = Math.max(b, p2.a * p2.b);
			return (maxA - maxB) < 0 ? -1 : 1;
		}
	}

	static class FastScanner {
		public int BS = 1<<16;
		public char NC = (char)0;
		byte[] buf = new byte[BS];
		int bId = 0, size = 0;
		char c = NC;
		double cnt = 1;
		BufferedInputStream in;

		public FastScanner() {
			in = new BufferedInputStream(System.in, BS);
		}

		public FastScanner(String s) {
			try {
				in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
			}
			catch (Exception e) {
				in = new BufferedInputStream(System.in, BS);
			}
		}

		private char getChar(){
			while(bId==size) {
				try {
					size = in.read(buf);
				}catch(Exception e) {
					return NC;
				}
				if(size==-1)return NC;
				bId=0;
			}
			return (char)buf[bId++];
		}

		public int nextInt() {
			return (int)nextLong();
		}

		public int[] nextInts(int N) {
			int[] res = new int[N];
			for (int i = 0; i < N; i++) {
				res[i] = (int) nextLong();
			}
			return res;
		}

		public long[] nextLongs(int N) {
			long[] res = new long[N];
			for (int i = 0; i < N; i++) {
				res[i] = nextLong();
			}
			return res;
		}

		public long nextLong() {
			cnt=1;
			boolean neg = false;
			if(c==NC)c=getChar();
			for(;(c<'0' || c>'9'); c = getChar()) {
				if(c=='-')neg=true;
			}
			long res = 0;
			for(; c>='0' && c <='9'; c=getChar()) {
				res = (res<<3)+(res<<1)+c-'0';
				cnt*=10;
			}
			return neg?-res:res;
		}

		public double nextDouble() {
			double cur = nextLong();
			return c!='.' ? cur:cur+nextLong()/cnt;
		}

		public String next() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=getChar();
			while(c>32) {
				res.append(c);
				c=getChar();
			}
			return res.toString();
		}

		public String nextLine() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=getChar();
			while(c!='\n') {
				res.append(c);
				c=getChar();
			}
			return res.toString();
		}

		public boolean hasNext() {
			if(c>32)return true;
			while(true) {
				c=getChar();
				if(c==NC)return false;
				else if(c>32)return true;
			}
		}
	}
}

