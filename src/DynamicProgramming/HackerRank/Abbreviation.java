import java.io.*;
import java.util.*;
/*
 * HackerRank Abbreviation
 * Longest common subsequence! let's derive from memory!
 */

public class Abbreviation {
    static HashSet<String> memo; // N states, O(1) transitions)
    static boolean possible;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        while (q-->0) {
            memo = new HashSet<>();
            possible = false;
            String a = sc.next();
            String b = sc.next();
            System.out.println(solve(a, b) ? "YES" : "NO");
        }
    }

    // State is current a and b
    // Transition: delete from a, or use it and delete from both a and b
    // Have base cases to kill recursion, storing into memoSET, not a table
    // A bit unexpected for me, not to use an array. Memo is general,
    // to store any state that we have encountered. Think when the states aren't necessarily numbers
    // Also, we only have to store false states, since if we found
    // a true state, then we just immediately return it and go up the recursive tree
    public static boolean solve(String a, String b) {
        if (possible) // CRUCIAL FOR LAST 3 TESTCASE?!?! SPED UP SOMEHOW
            return true;
        if (b.length() == 0) {
            boolean val = a.equals(a.toLowerCase()); // We can't have any other capitals in A
            if (val) {
                possible = true;
                return true;
            }
            else {
                memo.add(a + "_" + b);
                return false;
            }
        }
        else if (memo.contains(a + "_" + b)) {
            return false;
        }
        else if (a.length() < b.length()) {
            memo.add(a + "_" + b); // It is now known to be false
            return false;
        }
        // we are stuck, and have gotten rid of too many letters, now we are forced to use
        // one that won't work. BREAK
        else if (Character.isUpperCase(a.charAt(0)) && a.charAt(0) != b.charAt(0)) {
            memo.add(a + "_" + b); // It is now known to be false
            return false;
        }
        // Now for breaking to subproblems
        boolean ans = false;
        // Remember, we already pruned cases where the start was capital and NOT equal
        if (Character.isUpperCase(a.charAt(0))) {
            ans |= solve(a.substring(1), b.substring(1));
        }
        else { // Then there are two choices: Use it (if possible), or delete it
            if (a.substring(0, 1).toUpperCase().equals(b.substring(0, 1))) {
                ans |= solve(a.substring(1), b.substring(1));
            }
            ans |= solve(a.substring(1), b);
        }
        return ans;
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
