/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class NumberPermutationsREDO {

    public static void main(String args[]) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();
        int[] fact = new int[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) fact[i] = mult(fact[i - 1], i);

        boolean li = true, ri = true;
        Pair[] a = new Pair[n];

        for (int i = 0; i < n; i++) {
            a[i] = new Pair(fs.nextInt(), fs.nextInt(), i);
            li &= a[i].x == a[0].x;
            ri &= a[i].y == a[0].y;
        }
        int[] p = new int[n];
        Arrays.sort(a, (x, y) -> Integer.compare(x.x, y.x) == 0 ? Integer.compare(x.id, y.id) : Integer.compare(x.x, y.x));
        for (int i = 0; i < n; i++) p[i] = a[i].id;
        Arrays.sort(a, (x, y) -> Integer.compare(x.y, y.y) == 0 ? Integer.compare(x.id, y.id) : Integer.compare(x.y, y.y));

        boolean okay = true;
        for (int i = 0; i < n; i++) {
            okay &= p[i] == a[i].id;
        }

        int bad = 1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && a[i].y == a[j].y) j++;
            bad = mult(bad, fact[j - i]);
            i = --j;
        }

        Arrays.sort(a, (x, y) -> Integer.compare(x.x, y.x) == 0 ? Integer.compare(x.id, y.id) : Integer.compare(x.x, y.x));
        int bad2 = 1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && a[i].x == a[j].x) j++;
            bad2 = mult(bad2, fact[j - i]);
            i = --j;
        }


        int total = bad;
        if (!okay) {
            total = add(total, bad2);
        }
        int res = sub(fact[n], total);

        if (ri || li) {
            res = 0;
        }

        out.println(res);
        out.close();
    }

    static int MOD = 998244353;

    static class Pair {
        int x, y, id;
        public Pair(int a, int b, int i) {
            x = a; y = b;
            id = i;
        }
    }

    static int sub(int a, int b) {
        a -= b;
        if(a < 0) a += MOD;
        return a;
    }

    static int add(int a, int b) {
        a += b;
        if(a >= MOD) a -= MOD;
        return a;
    }

    static int mult(long a, long b) {
        a *= b;
        if(a >= MOD) a %= MOD;
        return (int)a;
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}