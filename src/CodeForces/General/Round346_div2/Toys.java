import java.io.*;
import java.util.*;
/*
 * CodeForces Round 346 Div 2 Toys
 * @derrick20
 */

public class Toys {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        HashSet<Integer> used = new HashSet<>();
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            used.add(x);
        }
        int money = M;
        int pos = 1;
        ArrayList<Integer> ans = new ArrayList<>();
        while (money - pos >= 0) {
            if (!used.contains(pos)) {
                money -= pos;
                ans.add(pos);
            }
            pos++;
        }
        System.out.println(ans.size());
        for (int val : ans) {
            System.out.print(val + " ");
        }
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
