import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 NumberCircle
 * @derrick20
 */

public class NumberCircle {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        if (arr[n-1] >= arr[n-2] + arr[n-3]) {
            out.println("NO");
        }
        else {
            int x = arr[n - 1];
            arr[n - 1] = arr[n - 2];
            arr[n - 2] = x;
            out.println("YES");
            for (int i = 0; i < n; i++) {
                out.print(arr[i] + " ");
                /*if (i % 2 == 0)
                    ans = ans + (arr[i] + " ");
                else
                    ans = (arr[i] + " ") + ans;*/
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

