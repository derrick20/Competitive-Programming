/**
 * @author derrick20
 *
 * I struggled so much because of reading issue -_-
 * Find SCCs, then find min cost in each component.
 * Sum those costs for each component, and return
 */
import java.io.*;
import java.util.*;

public class RichardTaxEvasionRedo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        cost = new int[N];
        for (int i = 0; i < N; i++) {
            cost[i] = sc.nextInt();
        }

        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
        }

        setupSCCs();

        long totalCost = 0;

        for (ArrayList<Integer> component : SCCs) {
//            System.out.println(component);
            long min = oo;
            for (int node : component) {
                if (cost[node] < min) {
                    min = cost[node];
                }
            }
            totalCost += min; //(min * component.size());
        }

        out.println(totalCost);

        out.close();
    }

    static long oo = (long) 1e18;
    static long mod = (long) 1e9 + 7;
    static int N;
    static ArrayList<Integer>[] adjList;
    static ArrayList<ArrayList<Integer>> SCCs;
    static int[] cost;
    static int[] lowLink, dfsNum;
    static boolean[] onStack;
    // Rather than marking states of 0, 1, or 2,
    // we can be clever and use dfsNum to know if it's
    // 0 or > 0. Then, we can use on stack to subdivide
    // the > 0 case into 1 and 2.
    static ArrayDeque<Integer> stack;
    // ArrayDeques can act as stacks too, (push end, pop end)
    static int time;
    static int UNVISITED = -1;

    static void setupSCCs() {
        SCCs = new ArrayList<>();
        lowLink = new int[N];
        dfsNum = new int[N];
        Arrays.fill(dfsNum, UNVISITED);
        onStack = new boolean[N];
        stack = new ArrayDeque<>();
        time = 0;
        for (int node = 0; node < N; node++) {
            if (dfsNum[node] == UNVISITED) {
                dfsLowLink(node);
            }
        }
    }

    static void dfsLowLink(int node) {
        dfsNum[node] = lowLink[node] = time++;
        onStack[node] = true; // Mark it as being on the stack
        stack.push(node);

        // Now, recurse and update our lowlink values
        for (int adj : adjList[node]) {
            // Casework on visited state
            // If it has not been visited/numbered
            // the dfsNum serves a dual purpose of being
            // the ID, as well as indicating if its been visited
            if (dfsNum[adj] == UNVISITED) {
                dfsLowLink(adj);
                // Whatever they can reach, we can, too
                lowLink[node] = Math.min(lowLink[node], lowLink[adj]);
            }
            else if (onStack[adj]) {
                // If it is on the stack, we can only
                // reach them, but we don't know if we
                // can reach
                lowLink[node] = Math.min(lowLink[node], dfsNum[adj]);
            }
        }

        // If our ID = our lowlink, then that means we
        // BEGAN the SCC. That's because we are the highest
        // up in the dfs traversal.
        if (dfsNum[node] == lowLink[node]) {
            ArrayList<Integer> component = new ArrayList<>();
            for (int curr = stack.pop(); ; curr = stack.pop()) {
                component.add(curr);
                onStack[curr] = false;
                if (curr == node) {
                    break;
                }
            }
            SCCs.add(component);
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