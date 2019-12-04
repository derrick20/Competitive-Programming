/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class WiFi {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        boolean[] hasRouter = new boolean[N];
        String s = sc.next();
        for (int i = 0; i < hasRouter.length; i++) {
            hasRouter[i] = s.charAt(i) == '1';
        }

        int[] painted = new int[N + 1];
        int pos = 0;
        long cost = 0;
        outer: while (pos < N - K - 1) {
            boolean found = false;
            for (int i = Math.min(N - 1, pos + K); i >= pos; i--) {
                if (hasRouter[i]) {
                    found = true;
                    painted[Math.max(0, i - K)]++;
                    cost += (i + 1);
                    painted[Math.min(N, i + K + 1)]--;
                    // Skip forward now by the jumped amount
                    pos = i + K + 1;
                    break;
                }
            }
            if (!found) {
                pos += K + 1; // Jump up past thtat point,
            }
//
//            while (!hasRouter[pos]) {
//                pos++;
//                if (pos >= N - 1 - K) {
//                    break outer;
//                }
//            }
            // Now there is a router here!
            // Once we've gotten past the initial radius, it is always greedily
            // beneficial to take something when we see it. This is because
            // it will NOT ONLY grab that spot, but any others later.
            //
        }
//        pos = N - K - 1;
        if (pos < N) {
            pos = N - K - 1; // Go back, to see if we can finish the rest
            // we are currently >= N - K - 1, but let's force our search here
            // so that we greedily take the least cost router to finish!
            while (pos < N) {
                if (hasRouter[pos]) {
                    painted[Math.max(0, pos - K)]++;
                    cost += (pos + 1);
                    painted[Math.min(N, pos + K + 1)]--;
                    // Skip forward now by the jumped amount, but doesn't really matter
                    pos = Math.min(N, pos + K + 1);
                    break;
                }
                pos++;
            }
        }
        for (int i = 1; i <= N; i++) {
            painted[i] += painted[i - 1];
        }

        for (int i = 0; i < N; i++) {
            if (painted[i] == 0) {
                cost += (i + 1);
            }
        }
//        out.println(Arrays.toString(painted));
        out.println(cost);
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