/*
ID: changhu1
LANG: JAVA
TASK: preface
 */
import java.io.*;
import java.util.*;

public class preface1 {

	private static final char[] letters = new char[] {'I', 'V', 'X', 'L', 'C', 'D', 'M'	};
	
	public String[] solve(int n) {
		HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
		
		for (int i = 1; i <= n; i ++) {
			for (Character c: convert(i).toCharArray()) {
				if (!counts.containsKey(c)) {
					counts.put(c, 0);
				} 
				counts.put(c, counts.get(c) + 1);
			}
		}
		
		ArrayList<String> res = new ArrayList<String>();
		for (char letter : letters) {
			if (counts.containsKey(letter)) {
				res.add(String.format("%s %d", letter, counts.get(letter)));
			}
		}
		
		return res.toArray(new String[0]);
	}

	private static final int[] bases = new int[] { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
	private static final String[] romans = new String[] { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };

	String convert(int n) {
		String res = "";
		while (n > 0) {
			int idx = 0;
			for (idx = 0; idx < bases.length - 1; idx++) {
				if (n >= bases[idx] && n < bases[idx + 1]) {
					break;
				}
			}
			res += romans[idx];
			n -= bases[idx];
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String problemName = "preface";
		BufferedReader f = new BufferedReader(new FileReader(problemName + ".in"));

		StringTokenizer st = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(st.nextToken());

		String[] res = (new preface1()).solve(n);

		// output Span
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(problemName + ".out")));
		for (int i = 0; i < res.length; i++) {
			out.println(res[i]);
		}
		out.close(); // close the output file
		System.exit(0); // don't omit this!
	}

}