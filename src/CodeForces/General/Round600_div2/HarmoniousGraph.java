/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class HarmoniousGraph {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }
        ArrayList<Interval> components = new ArrayList<>();
        visited = new boolean[N];
        for (int node = 0; node < N; node++) {
            if (!visited[node]) {
                min = node;
                max = node;
                dfs(node, -1);
                Interval i = new Interval(min, max);
                components.add(i);
            }
        }

        Collections.sort(components);

        ArrayDeque<Interval> stack = new ArrayDeque<>();
//        System.out.println(components);
        /*
50 5
10 30
20 40
42 44
43 46
44 47
*/
        stack.push(components.get(0));
        int ct = 0;
        for (int i = 1; i < components.size(); i++) {
//            System.out.println(components.get(i).l + " " + stack.getLast().r);
            if (components.get(i).l <= stack.peek().r) {
                Interval top = stack.pop();
                Interval bigger = new Interval(Math.min(top.l,components.get(i).l), Math.max(components.get(i).r, top.r));
                ct++;
//                System.out.println("hi: "+ ct);
                stack.push(bigger);
                // bigger has the farthest right possible, greedily
                // scooping later intervals
            }
            else {
                stack.push(components.get(i));
            }
        }
        // */
        out.println(ct);
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static boolean[] visited;
    static int min, max;

    static void dfs(int node, int par) {
        if (visited[node]) return;
        visited[node] = true;
        min = Math.min(min, node);
        max = Math.max(max, node);
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node);
            }
        }
    }

    static class Interval implements Comparable<Interval> {
        int l, r;
        public Interval(int lo, int hi) {
            l =lo; r = hi;
        }
        public int compareTo(Interval i2) {
            if (l - i2.l != 0) {
                return l - i2.l;
            }
            else {
                return i2.r - r;
            }
        }
        public String toString() {
            return l + " " + r;
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