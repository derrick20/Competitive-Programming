import java.io.*;
import java.util.*;
/*
 * CodeForces Round 346 Div 2 BicycleRace
 * @derrick20
 */

public class BicycleRace {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Pair[] points = new Pair[N+1];
        Pair[] vectors = new Pair[N];
        points[0] = new Pair(sc.nextInt(), sc.nextInt());
        int ct = 0;
        for (int i = 0; i < N; i++) {
            Pair p = new Pair(sc.nextInt(), sc.nextInt());
            points[i+1] = p;
            Pair prev = points[i];
            vectors[i] = new Pair(p.x - prev.x, p.y - prev.y);
            if (i >= 1) {
                // this means we've had at least two vectors, so begin cross products
                if (crossProduct(vectors[i-1], vectors[i]) > 0) {
                    // this means it's a counterclockwise orientation, which means we are swerving from
                    // the lake and is concave (since moving clockwise)
                    ct++;
                }
            }
        }
        System.out.println(ct);
    }

    static int crossProduct(Pair v1, Pair v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
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
