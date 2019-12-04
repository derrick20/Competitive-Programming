/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class FunctionalGraphPaths {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long K = sc.nextLong();
        int maxPower = 34; // A bit over log_2(10^10) = 33.2
        int[][] parent = new int[maxPower + 1][N];
        // parent[i][j] Stores the 2^ith parent of node j
        for (int i = 0; i < N; i++) {
            int firstParent = sc.nextInt();
            parent[0][i] = firstParent;
        }
        int[][] min = new int[maxPower + 1][N];
        long[][] sum = new long[maxPower + 1][N];
        for (int i = 0; i < N; i++) {
            int edgeWeight = sc.nextInt();
            min[0][i] = edgeWeight;
            sum[0][i] = edgeWeight;
        }
        int oo = (int) 1e9;
        for (int k = 1; k <= maxPower; k++) {
            Arrays.fill(min[k], oo);
            for (int i = 0; i < N; i++) {
                int halfParent = parent[k - 1][i];
                parent[k][i] = parent[k - 1][halfParent];
                min[k][i] = Math.min(min[k - 1][i], min[k - 1][halfParent]);
                sum[k][i] = sum[k - 1][i] + sum[k - 1][halfParent];
            }
        }

        for (int i = 0; i < N; i++) {
            long height = K;
            int currNode = i;
            long totalSum = 0;
            int minVal = oo;
            for (int power = maxPower; power >= 0; power--) {
                long currHeight = (1L << power);
                if (height >= currHeight) {
                    totalSum += sum[power][currNode];
                    minVal = Math.min(minVal, min[power][currNode]);
                    height -= currHeight;
                    currNode = parent[power][currNode];
                }
            }
            out.println(totalSum + " " + minVal);
        }
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