/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;
/*
19
56
22
91
16
41
41
74
139
101
84
56
63
29
32
109
88
74
124
61
137
113
56
103
91
76
37
64
182
101
61
52
106
112
104
128
22
48
38
74
64
99
27
59
38
103
103
29
85
38
57
55
38
18
60
38
57
99
117
118
79
61
72
118
91
18
61
18
15
50
111
111
55
84
18
37
16
22
38
198
60
15
38
37
91
40
41
60
106
19
72
27
32
18
74
76
37
41
50
22
 */

public class groupsBruteForce {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = 1;
        out.println(1);
        out.println(100);
        for (int c = 0; c < C; c++) {
            int N = 100;
            int[] X = new int[N];
            int[] Y = new int[N];
            int[][] dist = new int[N][N];
            for (int i = 0; i < N; i++) {
                Random random = new Random();
                X[i] = random.nextBoolean() ? 1000 : 0;
                Y[i] = random.nextBoolean() ? 1000 : 0;
                out.println(X[i] + " " + Y[i]);
            }
            out.println();
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    dist[i][j] = Math.abs(X[i] - X[j]) + Math.abs(Y[i] - Y[j]);
                }
            }
            int[] best = new int[N];
            for (int i = 0; i < N; i++) {
                best[i] = (int) 1e9;
            }
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (dist[i][j] < best[i]) {
                        best[i] = dist[i][j];
                    }
                    if (dist[i][j] < best[j]) {
                        best[j] = dist[i][j];
                    }
                }
            }
            for (int i = 0; i < N; i++) {
                out.println(best[i]);
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
