/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class GameWithTrapsClean {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int M = sc.nextInt();
        int N = sc.nextInt();
        int K = sc.nextInt();
        int T = sc.nextInt();

        ArrayList<Integer> agility = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            agility.add(sc.nextInt());
        }
        Collections.sort(agility, Collections.reverseOrder());
        ArrayList<Trap> traps = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            traps.add(new Trap(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
        Collections.sort(traps);
        int lo = 0;
        int hi = M;
        while (lo < hi) {
            int mid = (lo + hi) / 2 + 1;
            int weakest = agility.get(mid - 1);
            int doublePass = 0;
            int ptr = 0;
            while (ptr < traps.size()) {
                if (traps.get(ptr).d > weakest) {
                    int l = traps.get(ptr).l;
                    int r = traps.get(ptr).r;
                    while ((ptr + 1) < traps.size() && traps.get(ptr + 1).l <= r) {
                        if (traps.get(ptr + 1).d > weakest) {
                            r = Math.max(r, traps.get(ptr + 1).r);
                        }
                        ptr++;
                    }
                    doublePass += r - l + 1;
                }
                ptr++;
            }
            int time = 2 * (doublePass) + N + 1;
            if (time <= T) {
                lo = mid;
            }
            else {
                hi = mid - 1;
            }
        }
        out.println(lo);
        out.close();
    }

    static class Trap implements Comparable<Trap> {
        int l, r, d;
        public Trap(int left, int right, int danger) {
            l = left; r = right; d = danger;
        }
        public int compareTo(Trap t2) {
            return l - t2.l;
        }
        public String toString() {
            return "(" + l + ", " + r + ", " + d + ")";
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