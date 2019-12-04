import java.io.*;
import java.util.*;

public class mst01ahmad implements Runnable{
    public static void main (String[] args) {new Thread(null, new mst01ahmad(), "_cf", 1 << 28).start();}

    int[] comp;
    int compCnt = 1;
    HashSet<Integer>[] sets;
    TreeSet<Integer> unvisited;

    public void run() {
        FastScanner fs = new FastScanner();
//		try { fs = new FastScanner("testdata.out"); } catch (Exception e) {}
        PrintWriter out = new PrintWriter(System.out);
        System.err.println("Go!");

        int n = fs.nextInt();
        unvisited = new TreeSet<>();
        for(int i = 0; i < n; i++) unvisited.add(i);
        int m = fs.nextInt();
        sets = new HashSet[n];
        for(int i = 0; i < n; i++) {
            sets[i] = new HashSet<>();
            sets[i].add(i);
        }

        for(int i = 0; i < m; i++) {
            int u = fs.nextInt()-1;
            int v = fs.nextInt()-1;
            sets[u].add(v); sets[v].add(u);
        }
        comp = new int[n];
        for(int i = 0; i < n; i++) {
            if(comp[i] != 0) continue;
            dfs(i);
            compCnt++;
        }
        int[] size = new int[compCnt-1];
        for(int i = 0; i < n; i++) size[comp[i]-1]++;
        sort(size);
        out.println(size.length);
        for(int i = 0; i < size.length; i++) {
            if(i > 0) out.print(" ");
            out.print(size[i]);
        }
        out.println();

        out.close();
    }

    void dfs(int u) {
        unvisited.remove(u);
        comp[u] = compCnt;
        Integer v = 0;
        while(v != null) {
            boolean illegalConn = sets[u].contains(v);
            boolean isVisited = !unvisited.contains(v);
            if(illegalConn ||isVisited) {
                // If we connect to it through bad edges, or if visited, skip it.
                v = unvisited.higher(v);
                continue;
            }
            dfs(v);
        }

    }

    class DSU {
        int n;
        int[] size, rank, par;
        DSU(int a) {
            n = a;
            size = new int[n];
            rank = new int[n];
            par = new int[n];
            for(int i = 0; i < n; i++) {
                par[i] = i;
                size[i] = 1;
            }
        }
        int find(int u) {
            if(u == par[u]) return u;
            return par[u] = find(par[u]);
        }
        void union(int u, int v) {
            int pu = find(u), pv = find(v);
            if(pu == pv) return;
            if(rank[pu] >= rank[pv]) {
                if(rank[pu] == rank[pv]) rank[pu]++;
                size[pu] += size[pv];
                par[pv] = pu;
            }
            else {
                size[pv] += size[pu];
                par[pu] = pv;
            }
        }
    }

    void sort (int[] a) {
        int n = a.length;
        for(int i = 0; i < 50; i++) {
            Random r = new Random();
            int x = r.nextInt(n), y = r.nextInt(n);
            int temp = a[x];
            a[x] = a[y];
            a[y] = temp;
        }
        Arrays.sort(a);
    }

    class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double num = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) throws FileNotFoundException {
            in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
        }

        public char nextChar(){
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
            num=1;
            boolean neg = false;
            if(c==NC)c=nextChar();
            for(;(c<'0' || c>'9'); c = nextChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=nextChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                num*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/num;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c>32) {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c!='\n') {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=nextChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }

        public int[] nextIntArray(int n) {
            int[] res = new int[n];
            for(int i = 0; i < n; i++) res[i] = nextInt();
            return res;
        }

    }

}