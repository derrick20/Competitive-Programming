/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MaxFlowPrefix {
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
      setupIO("maxflow", false);

        N = sc.nextInt();
        int K = sc.nextInt();
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
        setupLCA();
        diffs = new int[N];
        for (int i = 0; i < K; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int lca = leastCommonAncestor(u, v);
            diffs[u]++;
            diffs[v]++;
            diffs[lca]--;
            int above = parent[0][lca];
            if (above != -1) {
                // Make the thing right before subtract, this is the range
                // query prefix sum trick
                diffs[above]--;
            }
        }
        propagate(0, -1);
        out.println(ans);
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[] diffs;
    static int ans;

    static void propagate(int node, int par) {
        for (int adj : adjList[node]) {
            if (adj != par) {
                propagate(adj, node);
                diffs[node] += diffs[adj];
            }
        }
        ans = Math.max(ans, diffs[node]);
    }

    static int maxDepth;
    static int[] depth;
    // parent takes the exponent for the binary lift and the child node as the arguments
    static int[][] parent;
    // the immediate parents of each node, for initializing of base cases for parent[][]
    static int[] firstParents;

    /*
    todo CRUCIAL NOTE: THIS IS SET UP WITH BEGINNING AT NODE 0
    todo DO NOT MESS THAT UP WHEN READING IN STUFF
     */

    static void setupLCA() {
        maxDepth = 0;
        depth = new int[N]; // store the depth of each node
        firstParents = new int[N]; // the immediate parent
        firstParents[0] = -1;
        depth[0] = 0;
        dfs(0);
        int power = 1;
        // Increase the power higher until we go over the maxDepth
        // This will be one more than we need
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N]; // go up 2^power from node i of N
        // Now, use that initial information acquired from the dfs to build
        // base cases of the sparse tables!
        for (int node = 0; node < N; node++) {
            parent[0][node] = firstParents[node];
        }
        // Necessary to set everything else to zero for now, so
        // that we never get confused if we are out of bounds

        // Must fill it up with -1's
        for (int p = 1; p < parent.length; p++) {
            for (int i = 0; i < N; i++) {
                parent[p][i] = -1;
            }
        }
        // p represents going up by 1 << p
        for (int p = 1; p < parent.length; p++) {
            for (int node = 0; node < N; node++) {
                if (parent[p - 1][node] != -1) {
                    int myParent = parent[p - 1][node];
                    parent[p][node] = parent[p-1][myParent];
                }
            }
        }
    }

    // no visited array needed since it's a tree
    // Ooh cleverness: we could take out par in our parameters because
    // we had that information via the firstParents array
    // We could also remove the depth parameter by using the depth array!
    static void dfs(int node) {
        for (int adj : adjList[node]) {
            if (adj != firstParents[node]) {
                firstParents[adj] = node;
                depth[adj] = depth[node] + 1;
                maxDepth = Math.max(maxDepth, depth[adj]);
                dfs(adj);
            }
        }
    }

    // Return the distance between 2 nodes in a tree!
    static int distance(int a, int b) {
        return depth[a] + depth[b] - 2 * depth[leastCommonAncestor(a, b)];
    }

    static int leastCommonAncestor(int a, int b) {
        if (depth[b] > depth[a]) {
            int c = a;
            a = b;
            b = c;
        }
        // We assume that a is farther down, so that's why we swap
        // Now, keep increasing the level of a by using sparse table. Try using the
        // biggest jumps possible, then exponentially refine it
        // This is just a more complex way of representing it as a binary number

        int dist = depth[a] - depth[b];
        while (dist > 0) {
            int power = (int) (Math.log(dist) / Math.log(2));
            a = parent[power][a];
            dist -= 1 << power;
        }

        // Sometimes, we'll be at the same level AND be the same, so return early
        // (the other statement will be wrong since it'll return our parent, when we were already the same
        if (a == b) {
            return a;
        }

        for (int j = parent.length - 1; j >= 0; j--) {
            // We want to make sure that we keep going while they're parents are different.
            // This process will take us right to here (since we can always represent
            // any number in binary, just subtract 1):
            //  lca
            //  / \
            // a   b
            if (parent[j][a] != parent[j][b]) {
                a = parent[j][a];
                b = parent[j][b];
            }
        }
        return parent[0][a];
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