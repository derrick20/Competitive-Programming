/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MaliciousBacteria {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();

        PriorityQueue<Edge> edges = new PriorityQueue<>();
        // Make the graph of the possible connections represented by the matches
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            edges.add(new Edge(u, v, 1));
        }
        long totalCost = KruskalMST(N, edges);
        out.println(totalCost);
        out.close();
    }

    // Return the min or MAX cost of a spanning TREE
    static long KruskalMST(int N, PriorityQueue<Edge> edges) {
        UnionFind unionFind = new UnionFind(N);
        long totalCost = 0;
        while (unionFind.numComponents > 1) {
            Edge e = edges.poll();
            if (!unionFind.connected(e.u, e.v)) {
                unionFind.unify(e.u, e.v);
                totalCost += e.wt;
            }
        }
        return totalCost;
    }

    static class Edge implements Comparable<Edge> {
        int wt;
        int u, v;

        public Edge(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }

        public int compareTo(Edge e) {
            return - (this.wt - e.wt);
        }
    }

    static class UnionFind {

        private int size;
        private int[] sz;
        private int[] id;
        private int numComponents;

        public UnionFind(int size) {

            if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

            this.size = numComponents = size;
            sz = new int[size];
            id = new int[size];

            for (int i = 0; i < size; i++) {
                id[i] = i; // Link to itself (self root)
                sz[i] = 1; // Each component is originally of size one
            }
        }

        public int find(int p) {

            // Find the root of the component/set
            int root = p;
            while (root != id[root]) root = id[root];

            // Compress the path leading back to the root.
            // Doing this operation is called "path compression"
            // and is what gives us amortized time complexity.
            while (p != root) {
                int next = id[p];
                id[p] = root;
                p = next;
            }

            return root;
        }
        // Return whether or not the elements 'p' and
        // 'q' are in the same components/set.
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        // Return the size of the components/set 'p' belongs to
        public int componentSize(int p) {
            return sz[find(p)];
        }

        // Return the number of elements in this UnionFind/Disjoint set
        public int size() {
            return size;
        }

        // Returns the number of remaining components/sets
        public int components() {
            return numComponents;
        }

        // Unify the components/sets containing elements 'p' and 'q'
        public void unify(int p, int q) {

            int root1 = find(p);
            int root2 = find(q);

            // These elements are already in the same group!
            if (root1 == root2) return;

            // Merge smaller component/set into the larger one.
            if (sz[root1] < sz[root2]) {
                sz[root2] += sz[root1];
                id[root1] = root2;
            } else {
                sz[root1] += sz[root2];
                id[root2] = root1;
            }

            // Since the roots found are different we know that the
            // number of components/sets has decreased by one
            numComponents--;
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