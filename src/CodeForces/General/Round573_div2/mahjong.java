/*
 * @author derrick20
 * Argh got it wrong in system tests because of not going all the way
 * up to 7 inclusive to check for the case where 7 _ 9 so it failed ://
 * that was the only weird case basically. Logic was ok i guess
 */

import java.io.*;
import java.util.*;

public class mahjong {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(sc.nextLine());
        Integer[] m = new Integer[10];
        Integer[] s = new Integer[10];
        Integer[] p = new Integer[10];
        for (int i = 0; i < 10; i++) {
            m[i] = 0;
            s[i] = 0;
            p[i] = 0;
        }
        while (st.hasMoreTokens()) {
            String x = st.nextToken();
            int rank = Integer.parseInt(x.substring(0, 1));
            if (x.charAt(1) == 's') {
                s[rank]++;
            }
            if (x.charAt(1) == 'p') {
                p[rank]++;
            }
            if (x.charAt(1) == 'm') {
                m[rank]++;
            }
        }
        int needed = 3;
        int most = Collections.max(Arrays.asList(s));
        most = Math.max(most, Collections.max(Arrays.asList(p)));
        most = Math.max(most, Collections.max(Arrays.asList(m)));
        needed = Math.max(0, needed - most);
        int longest = 0;
        int run = 0;
        for (int i = 1; i < 8; i++) {
            boolean a = m[i] > 0;
            boolean b = m[i + 1] > 0;
            boolean c = m[i + 2] > 0;
            if (a && b && c) {
                longest = Math.max(longest, 3);
            }
            else if (a && b || b && c || a && c)  {
                longest = Math.max(longest, 2);
            }
            else if (a || b || c) {
                longest = Math.max(longest, 1);
            }
        }
        for (int i = 1; i < 8; i++) {
            boolean a = s[i] > 0;
            boolean b = s[i + 1] > 0;
            boolean c = s[i + 2] > 0;
            if (a && b && c) {
                longest = Math.max(longest, 3);
            }
            else if (a && b || b && c || a && c)  {
                longest = Math.max(longest, 2);
            }
            else if (a || b || c) {
                longest = Math.max(longest, 1);
            }
        }
        for (int i = 1; i < 8; i++) {
            boolean a = p[i] > 0;
            boolean b = p[i + 1] > 0;
            boolean c = p[i + 2] > 0;
            if (a && b && c) {
                longest = Math.max(longest, 3);
            }
            else if (a && b || b && c || a && c)  {
                longest = Math.max(longest, 2);
            }
            else if (a || b || c) {
                longest = Math.max(longest, 1);
            }
        }
        int secondNeed = Math.max(0, 3 - longest);
        int ans = Math.min(needed, secondNeed);
        out.println(ans);
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
        String nextLine() throws IOException {
            return br.readLine();
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
