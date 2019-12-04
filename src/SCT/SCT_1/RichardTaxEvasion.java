/**
 * @author derrick20
 */
/*
5
1 2 1 0 1
7
1 2
2 3
3 4
4 5
5 1
2 5
5 3

 */
import java.io.*;
import java.util.*;

public class RichardTaxEvasion {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        cost = new int[N];
        for (int i = 0; i < N; i++) {
            cost[i] = sc.nextInt();
        }

        int M = sc.nextInt();
        adjList = new TreeSet[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new TreeSet<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(new Edge(v, cost[v]));
        }
        visited = new int[N];
        best = oo;
        shortestToSeen = new long[N];
        met = -1;
        dfs(0);
//        System.out.println(Arrays.toString(shortestToSeen));
        out.println(best);
        out.close();
    }

    static TreeSet<Edge>[] adjList;
    static int[] visited;
    static int[] cost;
    static long[] shortestToSeen;
    static long best;
    static long oo = (long) 1e18;

    // Return the shortest distance to reach something still
    // on the call stack.
    static int met;
    static long dfs(int node) {
        if (visited[node] == 1) {
            met = node;
            return 0;
        }
        else if (visited[node] == 2) {
            return shortestToSeen[node];
        }
        else {
            visited[node] = 1;
            long ans = oo;
            for (Edge e : adjList[node]) {
                if (e.wt + dfs(e.to) <= ans) {
                    ans = e.wt + dfs(e.to);
                    if (met == node) {
                        best = Math.min(best, ans);
                    }
                }
            }
            visited[node] = 2;
            return shortestToSeen[node] = ans;
        }
    }

    static class Edge implements Comparable<Edge> {
        int to, wt;
        public Edge(int other, int weight) {
            to = other; wt = weight;
        }
        public int compareTo(Edge e2) {
            return wt - e2.wt;
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}