import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 KeanuReeves
 * @derrick20
 */

public class KeanuReeves {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        int o = 0;
        int z = 0;
        for (char c : s.toCharArray()) {
            if (c == '0')
                z++;
            else
                o++;
        }

        if (o != z) {
            System.out.println(1);
            System.out.println(s);
        }
        else{
            System.out.println(2);
            System.out.println(s.charAt(0) + " " + s.substring(1));
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

