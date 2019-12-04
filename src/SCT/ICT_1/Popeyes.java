/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;
/*
5 7
10 6
1 19
2 0
3 28
4 6
4 10 6
10 2 10
4 3 8
4 2 2
10 3 8
4 1 2
2 3 6
3


 */
public class Popeyes {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        long[] val = new long[N];
        ArrayList<Edge>[] adjList = new ArrayList[N];

        HashMap<Integer, Integer> idToIdx = new HashMap<>();
        HashMap<Integer, Integer> idxToId = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int id = sc.nextInt();
            idToIdx.put(id, i);
            idxToId.put(i, id);
            val[i] = sc.nextLong();
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = idToIdx.get(sc.nextInt());
            int v = idToIdx.get(sc.nextInt());
            int wt = sc.nextInt();
            adjList[u].add(new Edge(v, wt));
            adjList[v].add(new Edge(u, wt));
        }

        long oo = (long) 1e9 + 7;
        long[] dist = new long[N];
        Arrays.fill(dist, oo);

        int source = sc.nextInt();
        dist[source] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(source, 0));
        while (pq.size() > 0) {
            Edge top = pq.poll();
            for (Edge e : adjList[top.to]) {
                long altDist = top.d + e.d;
                if (altDist < dist[e.to]) {
                    dist[e.to] = altDist;
                    pq.add(new Edge(e.to, altDist));
                }
            }
        }

        int best = -1;
        for (int i = 0; i < N; i++) {
            if (i == source) continue;
            if (best == -1 || val[i] * dist[best] > val[best] * dist[i]) {
                best = i;
            }
        }
        out.println(idxToId.get(best));
        out.close();
    }

    static class Edge implements Comparable<Edge> {
        int to; long d;
        public Edge(int other, long dist) {
            to = other; d = dist;
        }
        public int compareTo(Edge e2) {
            return (int) (d - e2.d);
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