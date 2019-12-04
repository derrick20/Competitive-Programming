/*
ID: d3rrickl
LANG: JAVA
PROG: lamps
 */
// USACO Training
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class lamps {
//100
//8394
//1 7 13 19 25 31 37 43 49 55 -1
//64 -1
    //
    // a
    public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("lamps.in")); //new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new FileWriter("lamps.out"));
        N = Integer.parseInt(br.readLine());
        C = Integer.parseInt(br.readLine());

        on = new int[N];
        off = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // unfortunately, I have to switch from 1 to N to 0 to N-1
        while (st.hasMoreTokens()) {
            int x = Integer.parseInt(st.nextToken());
            if (x != -1) {
                on[x-1] = 1;
            }
        }
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            int x = Integer.parseInt(st.nextToken());
            if (x != -1) {
                off[x-1] = 1;
            }
        }

        res = new TreeSet<>();
        visited = new HashSet<>();
//        visited = new HashSet[C]; // Visited stores, at a given number of moves done, what states have been seen
//        for (int i = 0; i < C; i++) {
//            visited[i] = new HashSet<>();
//        }
        // clever, accidentally, but pad the front with one extra one, which is inert.
        // Later, we'll remove it, but this preserves the 0's
        BigInteger start = one.shiftLeft(N+1).subtract(one);

        dfs(start,0);

        if (res.size() == 0) {
            out.println("IMPOSSIBLE");
        }
        else {
            TreeSet<String> sorted = new TreeSet<>();
            for (BigInteger X : res) {
                String str = X.toString(2);
                sorted.add(reverse(str));
            }
            for (String str : sorted)
                out.println(str.substring(0, N));
        }
        out.close();
    }

    public static String reverse(String str) {
        String ret = "";
        for (char c : str.toCharArray()) {
            ret = c + ret;
        }
        return ret;
    }

    static int C;
    static int N;
    static int[] on, off;
    static TreeSet<BigInteger> res;
    static BigInteger one = new BigInteger("1");
    static HashSet<BigInteger> visited;

    public static void dfs(BigInteger X, int ct) {
        if (isValid(X) && ((C-ct) % 2) == 0) {
            res.add(X);

        }
        if (ct == C) {
            return;
//            System.out.println(reverse(X.toString(2)));
        }
        // Then this exact state, having ct moves, and number X, has been crossed. We are strictly wasting time then
        if (visited.contains(X)) {return;}

        visited.add(X);
        for (int i = 1; i <= 4; i++) {
            dfs(toggle(i, X), ct+1);
        }
    }

    public static boolean isValid(BigInteger X) {
        for (int bit = 0; bit <= N-1; bit++) {
            if (on[bit] == 1) {
                if (X.testBit(bit) != true)
                    return false;
            }
            if (off[bit] == 1) {
                if (!X.testBit(bit) != true)
                    return false;
            }
        }
        return true;
    }

    public static BigInteger toggle(int type, BigInteger X) {
        switch(type) {
            case 1: // Toggle all bits
                for (int i = 0; i <= N-1; i++) {
                    X = X.flipBit(i);
                }
                break;
            case 2: // Toggle all odd-indexed bits (but we shifted it down, so it's not starting from 1)
                for (int i = 0; i <= N-1; i+= 2) {
                    X = X.flipBit(i);
                }
                break;
            case 3: // Toggle all even-indexed bits
                for (int i = 1; i <= N-1; i+= 2) {
                    X = X.flipBit(i);
                }
                break;
            case 4:
                for (int i = 0; i <= N-1; i+= 3) {
                    X = X.flipBit(i);
                }
                break;
        }
        // stupid exit case, should never be reached...
        return X;
    }
}