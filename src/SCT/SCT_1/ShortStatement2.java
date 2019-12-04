/**
 * @author derrick20
 * Key insight/observation:
 * Given the number of 1's and 0's as a, b, we can tell how many swaps there
 * are by just looking at the rightmost a and seeing how many 0's there are,
 * since that tells us that each spot must be filled in with some 1
 * that is part of the N - a = b slots to the left.
 *
 * With this idea, we think about the transitions between each of the 4
 * cases. Essentially, we have this window that checks the first b slots,
 * seeing how many 1's are there. This means any 1 here must be moved out
 * of this zone into the N - b = a slots on the right.
 * So, this window expands right if we add a 0 to the right.
 * If we add a 0 to the left, the left endpoint expands, but the right endpoint
 * of the window stays put
 *
 * If we add a 1 to the right, nothing happens to the window.
 * If we add a 1 to the left, we must shift the window left by one, but
 * the size stays the same. So, we need to subtract the right edge
 * and add the new left edge added in.
 *
 * With this, it is enough to run it in O(N + Q) time.
 */
import java.io.*;
import java.util.*;

public class ShortStatement2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int Q = sc.nextInt();
        String s = sc.next();

        HashMap<Integer, Integer> dq = new HashMap<>();
        // Answer is how many 1s in the first (N - total 1's = total 0's) values
        int left = 0;
        int right = 0;
        int size = -1;
        int sum = 0;
        // track sum[left, left + size]
        int ct = N;
        for (int i = 0; i < N; i++) {
            int val = Character.getNumericValue(s.charAt(i));
            dq.put(right, val);
            right++;
            if (val == 0) {
                size++;
                sum += dq.get(size);
                // Keep expanding to eat up all the ones in the growing interval
            }
        }

        out.println(sum);

        while (Q-->0) {
            int type = sc.nextInt();
            int add = sc.nextInt();
            if (type == 0) {
                if (add == 1) {
                    sum -= dq.get(left + size);
                    sum += add;
                    dq.put(left - 1, add);

                    left--;
                }
                else if (add == 0) {
                    dq.put(left - 1, add);

                    left--;
                    size++; // sum won't change, since right point stays pinned down
                }
            }
            else if (type == 1) {
                if (add == 1) {
                    dq.put(right, add);
                    right++;
                }
                else if (add == 0) {
                    sum += dq.get(left + size + 1);
                    size++;
                    dq.put(right, add);
                    right++;
                }
            }
            out.println(sum);
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