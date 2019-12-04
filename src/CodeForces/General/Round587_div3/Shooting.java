/**
 * @author derrick20
 * Initially, I jumped to the idea of DP in order to find thte maximum
 * However, the cost is just using greediness, since we can NECESSARILY
 * pick the least-priced choice each time. The key is I didn't differentiate
 * this from the question "Line of wines" by Errichto, which would force
 * a DP solution, since it was only possible to pick from left to right.
 *
 * In this case, I still managed to force it to be DP afterward, simply by
 * considering the possible cases, given the optimal cost ordering of range
 * [L, R]. Basically, we can either do [][...] or [...][] and then in each
 * case we can choose one of two orders of how to order when those were shot
 * (singleton then rest, or rest then single)
 *
 * However, the correct solution is to use a greedy approach, as we can
 * necessarily show by exchange arguments that if something is out of order,
 * a and b, then switching will always reduce the cost. (Also, they're distinct,
 * but that's not really relevant)
 */
import java.io.*;
import java.util.*;

public class Shooting {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        int[] prefixSums = new int[N + 1];
        ArrayList<Pair> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            list.add(new Pair(i + 1, arr[i]));
        }
        for (int i = 1; i <= N; i++) {
            prefixSums[i] = arr[i - 1] + prefixSums[i - 1];
        }

//        for (int i = 0; i < N; i++) {
//            arr.add(new Pair(i + 1, sc.nextInt()));
//        }
        Collections.sort(list, Collections.reverseOrder());
//
        int sum = 0;
        StringBuilder order = new StringBuilder();
        for (int i = 0; i < N; i++) {
            order.append(list.get(i).i + " ");
            sum += list.get(i).val * i + 1;
        }
        out.println(sum);
        out.println(order);
        /*
        OHHH YOU CAN'T USE DP ON THISSSS
        This is because we have too much mobility for the transitions!
        Note that this is not a simple "take-from-the-ends" dp, and we can
        transition from a given set of taken to N other choices. This is
        thus fundamentally wrong to try a DP on this, but good attempt!
        It's essentially trying to sort the two blocks by only appending
        and prepending parts from the sequence, wedging and starting from
        some random spot in the array. As you can think, this is impossible
        for many larger cases of sequences. We can't guarantee the sorted
        version without the capability of insertion!
        2 3 4 1 6 5
        ^ in the above case, We can get 1 2 3 4 sorted, but can never get the
        6 5 block included, unless there was insertion accounted for in our
        system
        int[][] dp = new int[N][N];
        // Store min shots for range [i, j];
        int oo = (int) 1e9;
        // Specific movement order:
        // This is because the right edge is pull-dp, relying on smaller right edges
        // and the left edge is pull-dp too, where we rely on higher left edges
        // Overall, this enables us to extract information about previous subcases
        // in the correct order
        //      ->
        //     -->
        //    --->
        //   ---->
        //  ----->
        // ------>
        for (int i = N - 1; i >= 0; i--) {
            Arrays.fill(dp[i], oo);
            for (int j = i; j < N; j++) {
                int shot = j - i; // = number of shots between, -1 since exclude the current one of interest
                if (i == j) {
                    dp[i][j] = 1; // Since 0 prior
                }
                else {
                    int best = oo;
                    // Four cases:
                    // Either we do [] + [...] or [...] + []
                    // For each of those, we can do [] first, before the rest, in which case we must shift the rest up
                    // , or [] after the main block
                    int costFirst = 1;
                    int costLast = 1 + shot * arr[i];
                    if (i < N - 1) {
                        int shift = prefixSums[j + 1] - prefixSums[i + 1];
                        int smallFirst = costFirst + dp[i + 1][j] + shift;
                        int smallLast = costLast + dp[i + 1][j];
                        best = Math.min(best, Math.min(smallFirst, smallLast));
                    }
                    if (j > 0) {
                        int shift = prefixSums[j] - prefixSums[i];
                        int smallFirst = costFirst + dp[i][j - 1] + shift;
                        int smallLast = costLast + dp[i][j - 1];
                        best = Math.min(best, Math.min(smallFirst, smallLast));
                    }
                    dp[i][j] = best;
                }
            }
            out.println(Arrays.toString(dp[i]));
        }

        // 997 977 927 933 968 951 949 903 931

        //1, 953, 2852, 5562, 9259]
        // Last 5
        // [1, 953, 2852, 5562, 9259]
        // First 5
        // [1, 935, 2802, 5602, 9295]
        out.println(dp[0][N-1]); */
        out.close();
    }

    static class Pair implements Comparable<Pair> {
        int i, val;

        public Pair(int ii, int v) {
            i = ii; val = v;
        }

        public int compareTo(Pair p2) {
            return val - p2. val;
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