/*
ID: d3rrickl
LANG: JAVA
PROG: preface
 */
import java.io.*;
		import java.util.*;

public class preface {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("preface.in")); //new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new FileWriter("preface.out"));
		int N = Integer.parseInt(br.readLine());
		counts = new HashMap<>();
		for (String numeral : ones)
			counts.put(numeral, 0);
		for (String numeral : fives)
			counts.put(numeral, 0);

		String[] numerals = {"I", "V", "X", "L", "C", "D", "M"};
		for (int i = 1; i <= N; i++) {
			for (char c : makeNumeral(i).toCharArray()) {
				String s = c + "";
				counts.put(s, counts.get(s)+1);
			}
		}
		for (String numeral : numerals) {
			if (counts.get(numeral) != 0)
				out.println(numeral + " " + counts.get(numeral));
		}
		out.close();
//		ArrayList<Pair> list = new ArrayList<>();
//		for (Map.Entry<String, Integer> e : counts.entrySet()) {
//			list.add(new Pair(e.getKey(), e.getValue()));
//		}
//		Collections.sort(list, new Comparator<Pair>() {
//			@Override
//			// smaller is higher priority by convention
//			// But we do the opposite since we need biggest at front
//			public int compare(Pair p1, Pair p2) {
//				return -1*(p2.count - p1.count);
//			}
//		});
//
//		for ()
	}
	static HashMap<String, Integer> counts;
	static String[] ones = {"I", "X", "C", "M"};
	static String[] fives = {"V", "L", "D"};

	static class Pair {
		String numeral = "";
		int count = 0;
		public Pair(String n, int c) {
			numeral = n;
			count = c;
		}
	}

	public static String makeNumeral(int N) {
		String ret = "";

		int digit = 0;
		while (N > 0) {
			int curr = N % 10;
			String add = "";
			String o = ones[digit];
			String f = "-";
			if (digit != 3)
				f = fives[digit];
			switch(curr) {
				case 1:
					add = o;
					break;
				case 2:
					add = o+o;
					break;
				case 3:
					add = o+o+o;
					break;
				case 4:
					add = o+f;
					break;
				case 5:
					add = f;
					break;
				case 6:
					add = f+o;
					break;
				case 7:
					add = f+o+o;
					break;
				case 8:
					add = f+o+o+o;
					break;
				case 9:
					add = o+ones[digit+1];
					break;
			}
			ret = add + ret; // pre-pend
			N /= 10;
			digit += 1;
		}
		return ret;
	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		Scanner(FileReader s) {
			br = new BufferedReader(s);
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}