/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class cards {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();

            TreeSet<Integer> set = new TreeSet<>();
            for (int i = 0; i < N; i++) {
                set.add(sc.nextInt());
            }
            int min = set.first();
            int gcd = min;
            for (int val : set) {
                gcd = GCD(gcd, val);
            }
            if (gcd == 1) {
                int max = set.last();
                boolean[] poss = new boolean[(min + 1) * max];
                for (int val : set) {
                    poss[val] = true;
                }
                for (int i = 0; i < poss.length; i++) {
                    for (int val : set) {
                        if (i + val < poss.length && poss[i]) {
                            poss[i + val] = true;
                        }
                    }
                }

                boolean found = false;
                boolean madeAll = true;
                int down = 1;
                while (down <= min) {
                    madeAll &= poss[poss.length - down];
                    down++;
                }
                // need to have made everything to find the impossible one
                if (madeAll) {
                    int pos = poss.length - 1;
                    while (!found && pos > max) {
                        found |= (!poss[pos--]);
                    }
                    if (!found) {
                        out.println(-1);
                    }
                    else {
                        out.println(pos + 1);
                    }
                }
                else {
                    out.println(-1);
                }
            }
            else {
                out.println(-1);
            }
        }
        out.close();
    }

    static int GCD(int a, int b) {
        return b == 0 ? a : GCD(b, a % b);
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
