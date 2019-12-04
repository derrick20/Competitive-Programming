/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PaintingPlan {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] arr = new int[2 * N];
        int[] delta = new int[2 * N];
        // Create a difference array
        // delta[i] = arr[i] - arr[i - 1]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            if (i > 0) {
                delta[i] = arr[i] - arr[i - 1];
            }
        }

        int currentCoverage = 0;
        for (int i = 1; i < delta.length; i += 2) {
            currentCoverage += delta[i];
        }
        // We initialize it at just pairing off everything minimally
        // to the thing right next to it. We then see if we can
        // fill the distance to K using the remaining gaps

        int goal = K - currentCoverage;
        if (goal == 0) {
            out.println("Yes");
            for (int i = 1; i <= 2 * N - 1; i += 2) {
                out.println(i + " " + (i + 1));
            }
        }
        else if (goal < 0) {
            out.println("No");
        }
        else {
//            boolean[] dp = new boolean[goal + 1];
            int[] parent = new int[goal + 1];
            parent[0] = -1;
            // Create a sort of tree where each sum produced can route back
            // to the initial sum of 0 by subtracting a certain value
            // This reconstructs the solution to the DP
            for (int i = 2; i < delta.length; i += 2) {
                int amt = delta[i];
                for (int prev = goal - amt; prev >= 0; prev--) {
                    if (parent[prev + amt] == 0 && parent[prev] != 0) {// dp[prev]) {
//                        dp[prev + amt] = true;
                        parent[prev + amt] = i;
                    }
                }
            }
            // A 0 denotes not visited
            if (parent[goal] == 0) {
                out.println("No");
            }
            else {
                ArrayList<Integer> added = new ArrayList<>();
                int cover = goal;
                // We now use the structure to reconstruct.
                // Each time, we use the "gap size" specified by the parent array.
                // As we use that gap, we get to a smaller subproblem and then
                // continue to build the full solution
                while (cover > 0) {
                    added.add(parent[cover]);
                    cover -= delta[parent[cover]];
                }
                Collections.sort(added);
                // Sort it, just for fun
                // Now, we create a special array to represent intervals
                // Initially, it will be alternating, false true false true...
                // This represents just having the intervals of [0,1] [2,3]...
                // However, because we just said we wanted to merge intervals,
                // we need to use the ones that were merged. Specifically, we
                // looked at the gaps between positions 1 and 2, 3 and 4, etc.
                // So, all even indexed position (0, 2, 4..) are false currently.
                // I now mark those as true by using the positions we recovered
                // from the DP.
                // For example, if we mark 2 as true, the array looks like:
                // [false, true, true, true, false, true, false, true...]
                // Before, it was [0,1], [2,3], [4,5] ...
                // Now, it is [0,3], [4,5], ...
                // Essentially, you start at a false, and continue forward until you
                // get to the last true, to get the intervals.
                boolean[] marked = new boolean[2 * N];
                for (int i = 1; i <= 2 * N - 1; i += 2) {
                    marked[i] = true;
                }
                for (int pos : added) {
                    marked[pos] = true;
                }
                ArrayList<Interval> ans = new ArrayList<>();
                int hi = 2 * N - 1;
                int lo = 2 * N - 1;
                // Start from the back (true), and keep moving backwards until we get to false
                // which represents opening an interval
                while (lo >= 0) {
                    while (marked[lo]) {
                        lo--;
                    }
                    // Once that spot has been reached (the first false)
                    // we need to nest a bunch of intervals inside it
                    // So, I decrement the "radius" of the interval until it becomes
                    // a degenerate interval (lo > hi)
                    int d = 0;
                    // converge at that spot
                    while (lo + d < hi - d) {
                        ans.add(new Interval(lo + d + 1, hi - d + 1));
                        d++;
                    }
                    // Go down one more, and restart the process
                    lo--;
                    hi = lo;
                }
                Collections.sort(ans);
                out.println("Yes");
                for (Interval i : ans) {
                    out.println(i);
                }
            }
        }

        out.close();
    }

    static class Interval implements Comparable<Interval> {
        int lo, hi;
        public Interval(int l, int h) {
            lo = l; hi = h;
        }
        public String toString() {
            return lo + " " + hi;
        }
        public int compareTo(Interval i2) {
            return (lo - i2.lo != 0) ? lo - i2.lo : -(hi - i2.hi);
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

        public int[] nextInts(int N) {
            int[] res = new int[N];
            for (int i = 0; i < N; i++) {
                res[i] = (int) nextLong();
            }
            return res;
        }

        public long[] nextLongs(int N) {
            long[] res = new long[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextLong();
            }
            return res;
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