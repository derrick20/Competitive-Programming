/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class directoryTraversal {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO("dirtraverse");
        setupIO();

        N = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            String str = sc.next();
            int M = sc.nextInt();
            for (int j = 0; j < M; j++) {
                int v = sc.nextInt() - 1;
                adjList[i].add(v);
                adjList[v].add(i);
            }
        }

        Node first = bfs(0, false);
        pre = new int[N];
        Node opposite = bfs(first.id, true);
        int diameter = opposite.depth;
        int start = opposite.id;
        for (int i = 0; i <= diameter / 2; i++) {
            start = pre[start];
        }
        // now, we should be at the center
//        System.out.println(start);
        dfs(start, -1, 0);
        out.println(total);
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int N;
    static int[] pre;

    static Node bfs(int node, boolean findPath) {
        ArrayDeque<Node> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N];
        queue.add(new Node(node, 0));

        Node farthest = queue.peek();
        while (queue.size() > 0) {
            Node front = queue.removeFirst();
            farthest = front;
            visited[front.id] = true;
            for (int adj : adjList[front.id]) {
                if (!visited[adj]) {
                    queue.addLast(new Node(adj, front.depth + 1));
                    if (findPath) {
                        // use this to trace the path to center
                        pre[adj] = front.id;
                    }
                }
            }
        }
        return farthest;
    }

    static long total = 0;
    static void dfs(int node, int parent, int depth) {
        // CRUCIAL ERROR, THE PARENT IS ON OF THESE ALWAYS
        // THE TREE IS CHARACTERIZED BY EVERYTHING WITh DEGREE >= 1!!!!
        if (adjList[node].size() > 1) {
            for (int adj : adjList[node]) {
                if (adj != parent) {
                    dfs(adj, node, depth + 1);
                }
            }
        }
        else {
            // sum distances to leaves
            total += depth;
        }
    }

    static class Node {
        int id, depth;

        public Node(int ii, int dd) {
            id = ii;
            depth = dd;
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
