import java.io.*;
import java.util.*;
/*
 * CodeForces New Year and Hurry
 * @derrick20
 */

public class B {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int total = 0;

        for (int i = 0; i < N; i++) {
            int dist = sc.nextInt();
            String dir = sc.next();
            switch(dir) {
                case "North":
                    if (total == 0) { // You can violate the pole in two ways. going past during a trip, or going past from it
                        System.out.println("NO");
                        System.exit(0);
                    }
                    total -= dist;
                    break;
                case "South":
                    if (total == 200000) {
                        System.out.println("NO");
                        System.exit(0);
                    }
                    total += dist;
                    break;
                case "East":
                case "West":
                    // If at either pole, restricted to the opposite direction
                    if (total == 0 || total == 20000) {
                        System.out.println("NO");
                        System.exit(0);
                    }
                    break;
            }

            // Went too far
            // I was typing two hundred thousand :///
            if (total > 20000 || total < 0) {
                System.out.println("NO");
                System.exit(0);
            }
        }
        if (total != 0) {
            System.out.println("NO");
        }
        else {
            // If passed everything, then yes!
            System.out.println("YES");
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
