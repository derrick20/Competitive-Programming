/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ShortestUnsortedSubarray {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        ArrayDeque<Pair> incrQueue = new ArrayDeque<>();
        int start = (int) 1e9;
        int end = 0;
        for (int i = 0; i < N; i++) {
            int val = arr[i];
            while (incrQueue.size() > 0 && val < incrQueue.getLast().v) {
                int pos = incrQueue.pollLast().i;
                start = Math.min(start, pos);
//                end = Math.max(end, i);
            }
            incrQueue.add(new Pair(i, val));
        }
        ArrayDeque<Pair> decrQueue = new ArrayDeque<>();

        for (int i = N - 1; i >= 0; i--) {
            int val = arr[i];
            while (decrQueue.size() > 0 && val > decrQueue.getLast().v) {
                int pos = decrQueue.pollLast().i;
                end = Math.max(end, pos);
//                start = Math.min(start, pos);
            }
            decrQueue.add(new Pair(i, val));
        }
        out.println(end - start + 1);
        out.close();
    }

    static class Pair {
        int i, v;
        public Pair(int ii, int vv) {
            i = ii; v = vv;
        }
        public String toString() {
            return "(" + i + ", " + v + ")";
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