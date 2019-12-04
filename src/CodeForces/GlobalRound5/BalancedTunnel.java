/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BalancedTunnel {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] inOrder = new int[N];
        int[] outOrder = new int[N];
        int[] exit = new int[N];
        for (int i = 0; i < N; i++) {
            inOrder[N - 1 - i] = sc.nextInt() - 1;
        }
        for (int i = 0; i < N; i++) {
            int car = sc.nextInt() - 1;
            outOrder[N - 1 - i] = car;
            exit[car] = N - 1 - i;
        }
        // Keep track of the thing that exited latest
        // By moving backwards, we know we entered after them,
        // but we want to see if we cut in front of ANYONE ->
        // so we check the best case, meaning we cut in front of the
        // slowest person thus far (lowest exit position)
        int[] toFine = new int[N];
        int minExit = N;
        for (int i = N - 1; i >= 0; i--) {
            if (exit[inOrder[i]] > minExit) {
                toFine[inOrder[i]]++;
            }
            minExit = Math.min(minExit, exit[inOrder[i]]);
        }
        int sum = 0;
        for (int val : toFine) {
            sum += val;
        }
        out.println(sum);
        out.close();
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