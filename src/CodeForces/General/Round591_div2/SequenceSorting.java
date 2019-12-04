/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class SequenceSorting {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int Q = sc.nextInt();
        int c = 1;
        while (Q-->0) {
            int N = sc.nextInt();
            int[] arr = new int[N];
            int[] lo = new int[N + 1];
            int[] hi = new int[N + 1];
            Arrays.fill(lo, -1);
            Arrays.fill(hi, N + 1);
            HashSet<Integer> nums = new HashSet<>();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = sc.nextInt();
                nums.add(arr[i]);
                if (lo[arr[i]] == -1) {
                    lo[arr[i]] = i;
                }
                hi[arr[i]] = i;
            }

//            if (c == 165) {
//                out.println(9);
//                out.println(N);
//                for (int val : arr) {
//                    out.println(val);
//                }
//            }
            // Two-pointers, checking the start value and seeing how many
            // internal intervals can be clustered in the correct order
            int maxCluster = 1;
            int right = 2;
            int last = 1; // the thing right before the right
            int currCluster = 1;
            for (int left = 1; left <= N; left++) {
                if (lo[left] == -1) continue;
//                int last = left;
                if (right <= left) {
                    right = left + 1;
                }
                last = right - 1;
                sweep: while (right <= N)  {
                    if (lo[right] == -1) {
                        right++;
                    }
                    else if (hi[last] < lo[right]) {
                        last = right; // Update the last thing of the interval
                        right++;
                        currCluster++; // We expanded
                    }
                    else {
                        break sweep;
                    }
                }
                if (currCluster > maxCluster) {
                    maxCluster = currCluster;
                }
                currCluster = Math.max(currCluster - 1, 1);
                // Since we are dropping the leftmost value after
                // sweeping forward
            }
            int needMoving = nums.size() - maxCluster;
            out.println(needMoving);
            c++;
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