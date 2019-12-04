import java.util.*;

public class SuffixArrays_NlogN {

	//usage example
	public static void main(String[] args) {
		SuffixArrays_NlogN a = new SuffixArrays_NlogN();

		String pattern = "mississippi";
		int[] suffixArray = a.suffixArray(pattern.toCharArray());
		int[] lcpArray = a.lcp(suffixArray, pattern.toCharArray());

		System.out.println(Arrays.toString(suffixArray));
		 System.out.println(Arrays.toString(lcpArray));
		
		/*
		 Suffix Array looks like this (with the strings they represent):
		 10 - "i"
		 7  - "ippi"
		 4  - "issippi"
		 1  - "ississippi"
		 0  - "mississippi"
		 9  - "pi"
		 8  - "ppi"
		 6  - "sippi"
		 3  - "sissippi"
		 5  - "ssippi"
		 2  - "ssissippi"
		 
		 LCP Array looks like:
		 [1, 1, 4, 0, 0, 1, 0, 2, 1, 3]
		 
		 Meaning that "i" shares 1 character with "ippi" (index 0 and 1 in suffix array)
		 and that "issippi" shares 4 characters with "ississippi" (index 2 and 3 in suffix Array) 
		 
		 */

		/*
		 So let's say we want to find the number of characters in common with the
		 strings "sippi" and "ssissippi" (suffixes 6 and 2)
		 
		 We know "sippi" shares 2 characters with "sissippi" so the answer is <= 2.
		 
		 But we know that "sissippi" only shares 1 character with "ssippi" so the answer
		 is <= 1. (Because the 2 characters we had matched so far "si" can no longer continue
		 to match because of the "ss" in "ssippi" but they still match the single "s"). 
		 
		 And "ssippi" shares 3 characters with "ssissippi" so we look at the only matched
		 character we have "s" and make sure that it can continue to match. Since 1 <= 3
		 we can still say that their common prefix is 1.
		 
		 What this essentially boils down to is the minimum value on the LCP array from the 2
		 suffixes we're interested in. So going from 6 to 2 in the suffix array involves the 
		 LCP values (2, 1, and 3) and the minimum of these is 1. So the amount of characters
		 they share is exactly 1.
		 
		 To quickly do this, we just need a quick way to query a minimum on a range.
		 We could use a Fenwick Tree or Segment Tree but because there are no updates
		 to this lcp array, we can use something called an RMQ table (code below) that will
		 tell us the minimum on any range in O(1) time.
		 
		 */
		
	}

	//returns an array of integers which represent
	//the suffixes of the given string in their sorted position.
	// O(n * log(n))
	public int[] suffixArray(char[] S) {
		int n = S.length;
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = n - 1 - i;

		Arrays.sort(order, (a, b) -> Character.compare(S[a], S[b]));

		int[] sa = new int[n];
		int[] classes = new int[n];
		for (int i = 0; i < n; i++) {
			sa[i] = order[i];
			classes[i] = S[i];
		}

		for (int len = 1; len < n; len *= 2) {
			int[] c = classes.clone();
			for (int i = 0; i < n; i++) {
				classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n && c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
			}

			int[] cnt = new int[n];
			for (int i = 0; i < n; i++)
				cnt[i] = i;
			int[] s = sa.clone();
			for (int i = 0; i < n; i++) {
				int s1 = s[i] - len;
				if (s1 >= 0)
					sa[cnt[classes[s1]]++] = s1;
			}

		}
		return sa;
	}

	//returns the Longest Common Prefix (lcp) array.
	//lcp[i] = the number of characters shared between the
	//ith sorted suffix and (i+1)th sorted suffix.
	int[] lcp(int[] sa, char[] s) {
		int n = sa.length;
		int[] rank = new int[n];
		for (int i = 0; i < n; i++)
			rank[sa[i]] = i;
		int[] lcp = new int[n - 1];
		for (int i = 0, h = 0; i < n; i++) {
			if (rank[i] < n - 1) {
				for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length && s[i + h] == s[j + h]; ++h)
					;
				lcp[rank[i]] = h;
				if (h > 0)
					--h;
			}
		}
		return lcp;
	}

	//Make an RMQ object and give it an array.
	//Then call the query method with your Left and Right values
	//and it'll return the minimum on the range.
	class RMQ {
		int[] vs;
		int[][] lift;

		public RMQ(int[] vs) {
			this.vs = vs;
			int n = vs.length;
			int maxlog = Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) + 2;
			lift = new int[maxlog][n];
			for (int i = 0; i < n; i++)
				lift[0][i] = vs[i];
			int lastRange = 1;
			for (int lg = 1; lg < maxlog; lg++) {
				for (int i = 0; i < n; i++) {
					lift[lg][i] = Math.min(lift[lg - 1][i], lift[lg - 1][Math.min(i + lastRange, n - 1)]);
				}
				lastRange *= 2;
			}
		}

		public int query(int low, int hi) {
			int range = hi - low + 1;
			int exp = Integer.highestOneBit(range);
			int lg = Integer.numberOfTrailingZeros(exp);
			return Math.min(lift[lg][low], lift[lg][hi - exp + 1]);
		}
	}

}
