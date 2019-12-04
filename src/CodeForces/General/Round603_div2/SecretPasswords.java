/**
 * @author derrick20
 * solved 1:02
 */
import java.io.*;
import java.util.*;

public class SecretPasswords {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        adjList = new HashSet[26];
        for (int i = 0; i < 26; i++) {
            adjList[i] = new HashSet<>();
        }

        HashSet<Integer> used = new HashSet<>();
        for (int i = 0; i < N; i++) {
            String s = sc.next();
            for (char c : s.toCharArray()) {
                used.add(c - 'a');
            }
            for (int pos = 1; pos < s.length(); pos++) {
                adjList[s.charAt(pos - 1) - 'a'].add(s.charAt(pos) - 'a');
                adjList[s.charAt(pos) - 'a'].add(s.charAt(pos - 1) - 'a');

            }
        }

        visited = new boolean[26];
        int comps = 0;
        for (int c : used) {
            if (!visited[c]) {
                dfs(c, -1);
                comps++;
            }
        }
        out.println(comps);
        out.close();
    }

    static HashSet<Integer>[] adjList;
    static boolean[] visited;
    static void dfs(int node, int par) {
        if (visited[node]) return;
        visited[node] = true;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node);
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