/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class GiftStack{
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int[] vals = new int[]{sc.nextInt(), sc.nextInt(), sc.nextInt()};
            Arrays.sort(vals);
            boxes.add(new Box(vals[0], vals[1], vals[2]));
            boxes.add(new Box(vals[1], vals[2], vals[0]));
            boxes.add(new Box(vals[0], vals[2], vals[1]));
            // width and length are always in the correct order (w <= l)
        }
        Collections.sort(boxes);
        int ans = 0;
        int[] dp = new int[3 * N];
        // dp[i] stores the maximum height achievable by placing box i on top of
        // some prior state
        dp[0] = boxes.get(0).h;
        for (int i = 1; i < 3 * N; i++) {
            Box curr = boxes.get(i);
            for (int j = 0; j < i; j++) {
                Box prev = boxes.get(j);
                if (curr.w < prev.w && curr.l < prev.l) {
                    dp[i] = Math.max(dp[i], dp[j] + curr.h);
                    ans = Math.max(ans, dp[i]);
                }
            }
        }
        out.println(ans);
        out.close();
    }

    static class Box implements Comparable<Box> {
        public int w, l, h;
        public Box(int width, int length, int height) {
            w = width;
            l = length;
            h = height;
        }

        // Decreasing area order
        public int compareTo(Box b2) {
            return -((l * w) - (b2.l * b2.w));
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