/**
 * @author derrick20
 * Ahhh the issue was the sorting of pairs was wrong!! When sorting a pair,
 * the condition is try sorting by a, if equal then sort by b. Don't mess up
 * the logic!
 */
import java.io.*;
import java.util.*;

public class NumberPermutations {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        HashMap<Integer, Integer> freqA = new HashMap<>();
        HashMap<Integer, Integer> freqB = new HashMap<>();

        Pair[] arr = new Pair[N];
        for (int i = 0; i < arr.length; i++) {
            int a = sc.nextInt(); int b = sc.nextInt();
            update(freqA, a);
            update(freqB, b);
            arr[i] = new Pair(a, b);
        }

        long mod = 998244353;
        long[] factorial = new long[N + 1];
        factorial[0] = 1;
        for (int i = 1; i <= N; i++) {
            factorial[i] = (factorial[i - 1] * (long) i) % mod;
        }

        long aWays = 1;
        long bWays = 1;
        for (int val : freqA.keySet()) {
            int freq = freqA.get(val);
            aWays *= factorial[freq];
            aWays %= mod;
        }

        for (int val : freqB.keySet()) {
            int freq = freqB.get(val);
            bWays *= factorial[freq];
            bWays %= mod;
        }
//        System.out.println(freqA);
//        System.out.println(freqB);
        Arrays.sort(arr);
        boolean bothSorted = true;
        HashMap<String, Integer> freqBoth = new HashMap<>();
//        System.out.println(Arrays.toString(arr));
        for (int i = 1; i < N; i++) {
            if (!(arr[i - 1].a <= arr[i].a && arr[i - 1].b <= arr[i].b)) {
                bothSorted = false;
                break;
                // not all adjacents are increasing
            }
        }
        long bothWays = 0;
        if (bothSorted) {
            for (Pair p : arr) {
                String key = p.toString();
                if (freqBoth.containsKey(key)) {
                    freqBoth.put(key, freqBoth.get(key) + 1);
                }
                else {
                    freqBoth.put(key, 1);
                }
            }
            bothWays = 1;
            for (String val : freqBoth.keySet()) {
                int freq = freqBoth.get(val);
                bothWays *= factorial[freq];
                bothWays %= mod;
            }
        }
//        System.out.printf("A = %d B = %d Both = %d \n", aWays, bWays, bothWays);
        long bad = (aWays + bWays - bothWays + mod) % mod;
        long good = (factorial[N] - bad + mod) % mod;
        out.println(good);

        out.close();
    }

    static void update2(HashMap<String, Integer> map, String val) {
        if (map.containsKey(val)) {
            map.put(val, map.get(val) + 1);
        }
        else {
            map.put(val, 1);
        }
    }

    static void update(HashMap<Integer, Integer> map, int val) {
        if (map.containsKey(val)) {
            map.put(val, map.get(val) + 1);
        }
        else {
            map.put(val, 1);
        }
    }

    static class Pair implements Comparable<Pair> {
        int a, b;
        public Pair(int a, int b) {
            this.a = a; this.b = b;
        }

        public String toString() {
            return a + "#" + b;
        }

        public int compareTo(Pair p2) {
            if (a != p2.a) {
                return a - p2.a;
            }
            else {
                return b - p2.b;
            }
        }
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

        public int[] nextIntArray(int n) {
            int[] res = new int[n];
            for(int i = 0; i < n; i++) res[i] = nextInt();
            return res;
        }
    }
}