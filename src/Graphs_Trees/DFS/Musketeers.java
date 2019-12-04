import java.io.*;
import java.util.*;
/*
 * HackerEarth Mrinal and Three Musketeers unsolved... :(
 *
 * */

public class Musketeers {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        adjList = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++)
            adjList[i] = new ArrayList<>();
    }

    static ArrayList<Integer>[] adjList;

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