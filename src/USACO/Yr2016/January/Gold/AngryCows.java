/**
 * @author derrick20
 *
 * Key ideas I missed:
 * Don't try to instantly jump to the proper position, then fix your errors.
 * Rather, the mindset for a problem is usually to build to the solution by
 * successively creating the optimal solution bag. So, start from the edges,
 * then see how far we can go inward, which results in two DP arrays, which
 * store the minimum radius needed starting from an endpoint to get to the
 * end. (Note this is caused by the complication of losing 1 each step
 * of the explosion process, otherwise it'd just be the limiting factor,
 * that is, the largest gap up until that point, which is what I deduced)
 *
 * Next, we essentially have two lines, one increasing (starting from left,
 * exploding out), and one decreasing (starting from right, exploding in)
 *
 * We want to find the point at which the max of the two lines is minimal,
 * that is, the intersection. If we view it graphically (by Intermediate
 * value theorem, they start at 0 and end at the range of the bales, so
 * they must cross. y1 - y2 changes sign, so it must be 0 at some point)
 *
 * After looking at sol:
 * I mixed up whether DP defines as min power we RECEIVE vs. we SEND
 *
 * Crap  this is so hard each time, you have to  recompute the minimum
 * radius you send?!!? 1stJ term?! Too shaft. Was relying too much on sol
 * Learned that you can break it into two halves/directions, build from
 * one side first
 */
import java.io.*;
import java.util.*;

public class AngryCows {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
//      setupIO("angry", false);

        int N = sc.nextInt();
        int[] bales = new int[N];
        for (int i = 0; i < N; i++) {
            bales[i] = 2 * sc.nextInt();
        }
        Arrays.sort(bales);

        int[] dpLeft = new int[N];
//        dpLeft[0] = 2;
        int[] dpRight = new int[N];
//        dpRight[N - 1] = 2;
        // Each of them
        // Store the minimum initial power that this bale must have
        // to reach the respective end

        for (int i = 1; i < N; i++) {
            dpLeft[i] = Math.max(dpLeft[i - 1] + 2, bales[i] - bales[i - 1]);
        }
        for (int i = N - 2; i >= 0; i--) {
            dpRight[i] = Math.max(dpRight[i + 1] + 2, bales[i + 1] - bales[i]);
        }

        int minRadius = Integer.MAX_VALUE;
        int i = 0;
        int j = N - 1;
        while (i < j) {
            int stepRadius = 2 + Math.max(dpLeft[i], dpRight[j]);
            int startRadius = (bales[j] - bales[i]) / 2;
            int radius = Math.max(stepRadius, startRadius);
            minRadius = Math.min(radius, minRadius);
            if (dpLeft[i + 1] < dpRight[j - 1]) {
                // Advancing something that doesn't affect the maximum/limiting
                // blast distance factor is greedily safe.
                i++;
            }
            else {
                j--;
            }
        }
        double ans = minRadius / 2.0;
        out.printf("%.1f", ans);
//        int startIndex = 0; // start means we start in the interval from start to start+1
//        int maxGap = 0;
//        int mid = (N - 1) / 2;
//        for (int i = 0; i < N - 1; i++) {
//            int gap = bales.get(i + 1) - bales.get(i);
//            if (gap > maxGap && Math.abs(mid - i) < Math.abs(mid - startIndex)) {
//                startIndex = i;
//                maxGap = gap;
//            }
//        }

//        System.out.println(startIndex);
//        System.out.println(maxGap);

        out.close();
    }

/*
    static double minRadius(int startIndex) {
        int L = startIndex;
        int R = startIndex + 1;
        int left = bales.get(L);
        int right = bales.get(R);
        int delta = 0;
        boolean needHalf = left % 2 == right % 2;
        int initialRadius = (right - left) / 2;
        int currRadius = initialRadius - 1;
        L--; R++;
        while (L >= 0 || R <= N - 1) {
            // If we can't reach the next one, then we need to increase the
            // initial which we'll track in delta. Once we do this, let's
            // greedily stop and see if we can finish the rest with this alone
            if (L >= 0) {
                while (bales.get(L) < left - currRadius) {
                    currRadius++;
                    delta++;
                    needHalf = false;
                }
                int farthestHit = left; // just to give it a default value
                // Keep exploding the nearest one's that are within range
                while (L > 0 && bales.get(L - 1) >= left - currRadius) {
                    farthestHit = bales.get(--L);
                }
                L = farthestHit;
                left = bales.get(L);
            }

            if (R <= N - 1) {
                // Again, expand if we can't hit the next.
                while (bales.get(R) > right + currRadius) {
                    currRadius++;
                    delta++;
                    needHalf = false;
                }
                int farthestHit = right;
                // Keep exploding the nearest one's that are within range
                while (R < N - 1 && bales.get(R + 1) <= right + currRadius) {
                    farthestHit = bales.get(++R);
                }
                R = farthestHit;
                right = bales.get(R);
            }
            currRadius--; // Decreases each round
        }
        double ans = initialRadius + delta + (needHalf ? 0.5 : 0);
        return ans;
    }
*/
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