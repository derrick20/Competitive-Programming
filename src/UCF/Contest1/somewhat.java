import java.io.*;
import java.util.*;
/*
Team ID: dliang
LANG: JAVA
PROG: somewhat
*/

public class somewhat {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            HashSet<Long> set = new HashSet<>();
            TreeSet<Long> lcm = new TreeSet<>();
            int X = sc.nextInt();
            for (int j = 0; j < X; j++) {
                set.add(sc.nextLong());
            }
            ArrayList<Long> num = new ArrayList<>(set);
            for (int first = 0; first < num.size(); first++) {
                Long a = num.get(first);
                for (int second = first + 1; second < num.size(); second++) {
                    Long b = num.get(second);
                    lcm.add(a * b / GCD(a, b));
                }
            }
            int size = lcm.size();
            for (int j = 0; j < size - 1; j++) {
                out.print(lcm.pollFirst() + " ");
            }
            out.println(lcm.pollFirst());
        }
        out.close();
    }

    static long GCD(long a, long b) {
        if (a < b) {
            // do a swap so a >= b
            a += b;
            b = a - b;
            a -= b;
        }

        // the lesser number will always be b in each loop
        while (b != 0) {
            a %= b;

            a += b;
            b = a - b;
            a -= b;
        }
        return a;
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
