/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RemainderProblem {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        // Test new scanners
        PrintWriter out = new PrintWriter(System.out);

        int Q = sc.nextInt();
        int size = 708;
        // Our goal is to minimize the amount of searching
        // If big mods, not much, little mods, more. So, we make the
        // small mods easier by preprocessing extra work so that
        // future queries are fast.
        int[] arr = new int[500001];
        int[][] classSum = new int[size][size];
        // For each residue class i from 0 to size, store the sum
        // of all arrays with index 0, 1, ... i -1.
        while (Q-->0) {
            int type = sc.nextInt();
            if (type == 1) {
                int index = sc.nextInt();
                int val = sc.nextInt();
                arr[index] += val;
                // This index is a unique residue for each possible modulo
                for (int modulo = 1; modulo < size; modulo++) {
                    int remainder = index % modulo;
                    classSum[modulo][remainder] += val;
//                    if (modulo < 10)
//                        System.out.println("Modulo: " + modulo + " remainder: " + remainder + " "  + Arrays.toString(classSum[modulo]));
                }
            }
            else if (type == 2) {
                int modulo = sc.nextInt();
                int remainder = sc.nextInt();
                if (modulo < size) {
                    out.println(classSum[modulo][remainder]);
                }
                else {
                    int sum = 0;
                    for (int index = remainder; index <= 500000; index += modulo) {
                        sum += arr[index];
                    }
                    out.println(sum);
                }
            }
        }
        out.close();
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double num = 1;
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

        public char nextChar(){
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
            num=1;
            boolean neg = false;
            if(c==NC)c=nextChar();
            for(;(c<'0' || c>'9'); c = nextChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=nextChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                num*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/num;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c>32) {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c!='\n') {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=nextChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}