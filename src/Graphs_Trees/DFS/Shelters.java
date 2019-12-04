import java.io.*;
import java.util.*;
/*
 * HackerEarth Shelters and Tunnels
 *
 * */

public class Shelters {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        adjList = new ArrayList[N + 1];
        //TreeMap<Integer, Integer> degree = new TreeMap<>();
        int[] degree = new int[N+1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }


        for (int i = 1; i <= N-1; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjList[from].add(to);
            adjList[to].add(from);
            degree[from]++;
            degree[to]++;
        }
        int max = 0;
        for(int value: degree) {
            max = Math.max(value, max);
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i = 1; i <= N; i++) {
            int value = degree[i];
            if (value == max) {
                ans.add(i);
            }
        }
        Collections.sort(ans);
        System.out.println(ans.size());
        for (int i = 0; i < ans.size()-1; i++) {
            System.out.print(ans.get(i) + " ");
        }
        System.out.println(ans.get(ans.size()-1));
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