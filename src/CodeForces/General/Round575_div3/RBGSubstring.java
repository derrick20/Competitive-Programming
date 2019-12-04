/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class RBGSubstring {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int Q = sc.nextInt();
        while (Q-->0) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            StringBuilder s = new StringBuilder(sc.next());

            StringBuilder roll = new StringBuilder();
            // 3 counters: RGB, GBR, or BRG
            int ct1 = 0;
            int ct2 = 0;
            int ct3 = 0;
            int best = 0;
            int pos = 0;
            for (int i = 0; i < N; i++) {
                char add = s.charAt(i);
                if (roll.length() == K) {
                    char subtract = roll.charAt(0);
                    if ((pos - K) % 3 == 0) {
                        if (subtract == 'R') ct1--;
                        if (subtract == 'G') ct2--;
                        if (subtract == 'B') ct3--;
                    }
                    if ((pos - K) % 3 == 1) {
                        if (subtract == 'G') ct1--;
                        if (subtract == 'B') ct2--;
                        if (subtract == 'R') ct3--;
                    }
                    if ((pos - K) % 3 == 2) {
                        if (subtract == 'B') ct1--;
                        if (subtract == 'R') ct2--;
                        if (subtract == 'G') ct3--;
                    }
                    roll.deleteCharAt(0);
                }
                roll.append(add);
                if (pos % 3 == 0) {
                    if (add == 'R') ct1++;
                    if (add == 'G') ct2++;
                    if (add == 'B') ct3++;
                }
                if (pos % 3 == 1) {
                    if (add == 'G') ct1++;
                    if (add == 'B') ct2++;
                    if (add == 'R') ct3++;
                }
                if (pos % 3 == 2) {
                    if (add == 'B') ct1++;
                    if (add == 'R') ct2++;
                    if (add == 'G') ct3++;
                }
                best = Math.max(best, Math.max(ct1, Math.max(ct2, ct3)));
                pos++;
//                System.out.println(roll + " " + best);

            }

            if (best >= K) {
                out.println(0);
            }
            else {
                out.println((K - best));
            }
        }
        out.close();
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
