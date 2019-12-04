/**
 ID: d3rrickl
 LANG: JAVA
 PROG: concom
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class concom {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("concom", false);
        int N = sc.nextInt();
        control = new int[100][100];
        adjList = new ArrayList[100];
        for (int i = 0; i < 100; i++) {
            adjList[i] = new ArrayList<>();
        }
        visited = new boolean[100][100];
        for (int i = 0; i < N; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int amt = sc.nextInt();
            control[u][v] = amt;
            adjList[u].add(v);
        }

//        // store the max control i has over j. Initially, they are only connected
//        // through one edge. Over each k iterations, we expand the number of
//        // possible intermediates edges it can move through
//        for (int k = 0; k < 100; k++) {
//            // Gradually expand the number of intermediates we can use
//            for (int i = 0; i < 100; i++) {
//                for (int j = 0; j < 100; j++) {
//                    if (control[i][k] > 50 && control[i][j] < 50) {
//                        // As stated, with each company A controls, A gains
//                        // control over B through the intermediate companies.
//                        control[i][j] += control[k][j];
//                    }
//                }
//            }
//        }
        // The key idea is that we need to update everything for a single source's
        // potential controls once.
        // When we exapnd through, we will never need to come back
        for (int node = 0; node < 100; node++) {
            dfs(node, node);
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (i != j && control[i][j] > 50) {
                    out.println((i + 1) + " " + (j + 1));
                }
            }
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[][] control;
    static boolean[][] visited;

    static void dfs(int node, int source) {
        if (visited[source][node]) return;

        visited[source][node] = true;
        for (int adj : adjList[node]) {
            // We can now go into this node, using it as a proxy to
            // control others
            if (control[node][adj] > 50) {
                for (int sink = 0; sink < 100; sink++) {
                    if (control[source][sink] <= 50) {
                        // Use the adj to gain control over the sink
                        // We must only do this if it doesn't have control yet
                        // otherwise, the amounts this connection will have
                        // can become too great?! todo Not sure why deleting
                        // todo this IF statement breaks it
                        // todo, perhaps we goot lucky, and really we shouold
                        // todo have another aux array storing if controlled or not!
                        control[source][sink] += control[adj][sink];
                    }
                }
                // Go down, essentially expanding a connected component of
                // conquered nodes FROM THE SOURCE SPECIFICALLY
                dfs(adj, source);
            }
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

        public int[] nextInts(int N) {
            int[] res = new int[N];
            for (int i = 0; i < N; i++) {
                res[i] = (int) nextLong();
            }
            return res;
        }

        public long[] nextLongs(int N) {
            long[] res = new long[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextLong();
            }
            return res;
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