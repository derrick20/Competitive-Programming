/**
 * @author derrick20
 * Basically, we needed a way to connect everything with at least
 * one factor in common together. So, I came up with a way where
 * we have the graph of all primes, and I connect different primes
 * by connecting each of the factors of a given number together.
 * I then arbitrarily add 1 to one of the nodes connected.
 * The logic is, it doesn't matter which prime we add 1 to,
 * since they are all connected and will get added eventually...
 *
 * Then, I dfs from each prime, and sum the number of numbers among
 * them, and find the max.
 *
 * The issue is I didn't know how to use UnionFind for this
 */
import java.io.*;
import java.util.*;

public class ConnectN {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        adjList = new ArrayList[100_001]; // Prime factor graph
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }
        count = new HashMap<>();
        // Each time we try a number, we factorize it,
        // then join all the components it captures like a net
//        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            int x = arr[i];
            ArrayList<Integer> factors = new ArrayList<>();
            for (int f = 2; f * f <= x; f++) {
                if (x % f == 0) {
                    factors.add(f);
                    while (x % f == 0) {
                        x /= f;
                    }
                }
            }
            if (x > 1) {
                // at this point, x must be a prime, because the biggest
                // smallest possible number with >= 2 factors is
                // > (x+1)(x+1), so we are necessarily just one factor
                factors.add(x);
            }

            for (int j = 0; j < factors.size(); j++) {
                int factor = factors.get(j);
                // Increment the number with that factor
                count.merge(factor, 1, Integer::sum);
                if (j > 0) {
                    adjList[factor].add(factors.get(j - 1));
                    adjList[factors.get(j - 1)].add(factor);
                }
            }
            // We over count by the # of factors - 1, since all of those
            // represent just this one number
            if (factors.size() > 0) {
                count.merge(factors.get(0), -(factors.size() - 1), Integer::sum);
            }
        }

        visited = new boolean[adjList.length];
        int best = 0;
        for (int i = 0; i < adjList.length; i++) {
            if (!visited[i]) {
                best = Math.max(best, dfs(i, -1));
            }
        }
        out.println(best);
        out.close();
    }

    static HashMap<Integer, Integer> count;
    static ArrayList<Integer>[] adjList;
    static boolean[] visited;

    static int dfs(int node, int par) {
        int ans = 0;
        if (!visited[node]) {
            visited[node] = true;
            ans = count.getOrDefault(node, 0);
            for (int adj : adjList[node]) {
                if (adj != par) {
                    ans += dfs(adj, node);
                }
            }
        }
        return ans;
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