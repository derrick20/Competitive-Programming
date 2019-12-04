/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ByElevatorOrStairs {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int C = sc.nextInt();
        int[] stairs = new int[N];
        int[] elevator = new int[N];
        for (int i = 1; i < stairs.length; i++) {
            stairs[i] = sc.nextInt();
        }
        for (int i = 1; i < elevator.length; i++) {
            elevator[i] = sc.nextInt();
        }
        int[] dpStairs = new int[N];
        int[] dpElevator = new int[N];

        // Obviously this can be optimized for space efficiency, printing
        // as you go, but that's not really the hard part of the problem -_-
        // dp[i] stores the minimum cost to reach the current point, ending on
        // the specified type of transportation
        dpStairs[1] = stairs[1];
        dpElevator[1] = elevator[1] + C;
        for (int i = 2; i < N; i++) {
            dpStairs[i] = Math.min(dpStairs[i - 1], dpElevator[i - 1]) + stairs[i];
            // Let's add the cost when we get ONTO the elevator. (moving from stairs)
            dpElevator[i] = Math.min(dpStairs[i - 1] + C, dpElevator[i - 1]) + elevator[i];
        }
        for (int i = 0; i < N; i++) {
            out.print(Math.min(dpStairs[i], dpElevator[i]) + " ");
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