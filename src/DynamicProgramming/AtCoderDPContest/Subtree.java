/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Subtree {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        mod = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }

        dpDown = new long[N];
        dpUp = new long[N];
        solveDown(0, 0);

        dpUp[0] = 1;
        solveUp(0, 0);

//        System.out.println(Arrays.toString(dpUp));
//        System.out.println(Arrays.toString(dpDown));
        for (int i = 0; i < N; i++) {
            long ans = dpUp[i] * dpDown[i];
            ans %= mod;
            out.println(ans);
        }

        out.close();
    }

    static int N, mod;
    static ArrayList<Integer>[] adjList;
    static long[] dpUp;
    static long[] dpDown;
    // dpDown[node] stores the number of ways for a node's subtree to be colored
    // in a connected way, with node ALWAYS being colored
    // Transition: use all our children + 1, since each one can also be uncolored

    static long solveDown(int node, int par) {
        long ways = 1;
        for (int adj : adjList[node]) {
            if (adj != par) {
                ways *= (solveDown(adj, node) + 1);
                ways %= mod;
            }
        }
        return dpDown[node] = ways;
    }

    static void solveUp(int node, int par) {
        int size = adjList[node].size();
        long[] prefixDown = new long[size + 1];
        // store the product of everything up to certain points
        // WE CAN'T DO DIVISION, so we snake around the problem by using groups/clustering
        prefixDown[0] = 1;
        for (int i = 1; i <= size; i++) {
            prefixDown[i] = prefixDown[i - 1];
            int adj = adjList[node].get(i - 1);
            if (adj != par) {
                prefixDown[i] *= (dpDown[adj] + 1);
                prefixDown[i] %= mod;
            }
        }
        long[] suffixDown = new long[size + 1];
        // store the product of the last i nodes
        suffixDown[0] = 1;
        for (int i = 1; i <= size; i++) {
            suffixDown[i] = suffixDown[i - 1];
            int adj = adjList[node].get((size - 1) - (i - 1));
            if (adj != par) {
                suffixDown[i] *= (dpDown[adj] + 1);
                suffixDown[i] %= mod;
            }
        }

        // Now, we have an O(1) way to get the product of everything BUT the current node
        for (int i = 1; i <= size; i++) {
            int adj = adjList[node].get(i - 1);
            if (adj != par) {
                // prefix + suffix (exclude me)
                // Before, i used dpDown[adj] / (dpDown[adj] + 1).
                // However, I knew the modulo being nonprime also made it impossible to do mod inverse, so
                // we need to tackle it head on. Logically, we need to reuse this info, so we should
                // think to precomp it! We don't need a global prefix sum array since we only use the dpDown
                // info during this specific node's adjacents
                long above = dpUp[node];
                long below = (prefixDown[i - 1] * suffixDown[size - i]) % mod;
                dpUp[adj] = above * below + 1;
                dpUp[adj] %= mod;

                solveUp(adj, node);
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