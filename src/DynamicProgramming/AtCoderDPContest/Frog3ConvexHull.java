/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Frog3ConvexHull {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long C = sc.nextLong();
        long[] height = new long[N];
        for (int i = 0; i < N; i++) {
            height[i] = sc.nextLong();
        }

        LinkedList<Line> deque = new LinkedList<>();
        // Our DP states are a given height and the previous cost
        // Since cost up to index j =
        // (h[j] - h[i])^2 + dpPrev + C
        // h[j]^2 - 2h[i]h[j] + (h[i]^2 + dpPrev + C)
        // When comparing two lines with different starting i's, ending at a specific j,
        // the second part is independent, so that can all be lumped into the prevCost (y-intercept)
        // What remains is:
        // h[j]^2 - 2h[i]h[j] + prevCost
        // However, if h[j] is fixed (since we are testing the cost at a specific h[j],
        // Then that will cancel out when we subtract the two cost values for different lines.
        // So, all we need to do is compare the following:
        // -2h[i1]h[j] + prevCost1 ?= -2h[i2]h[j] + prevCost2
        // This perfectly represents a line!
        // **NOTE we must be sure to not forget the h[j] term of the line!

        deque.add(new Line(-2 * height[0], square(height[0])));
        for (int i = 1; i < N; i++) {
            // Need to maintain two invariants:
            // First, before we use the solution bag to retrieve the best previous
            // partial solution from which to reach this position, we need tha front
            // of the deque to have the minimum cost to reach this position. (Since
            // the lines are all more and more negative slopes, if the current front cost
            // isn't the minimum, it will never be the minimum later. Basically, the line
            // has intersected with the next line (starting slightly later), which means
            // that next line is now better. Essentially, initially, starting from a stone
            // earlier in position is better due to the reduced fixed cost of switching
            // stones. However, at a certain point, the quadratic cost due to increasing
            // height difference will cause the cost to always be worse.
            // Invariant: i < j => cost(i to curr) < cost(j to curr)
            long currHeight = height[i];
            while (deque.size() >= 2 && deque.get(0).getCost(currHeight) > deque.get(1).getCost(currHeight)) {
                deque.pollFirst();
            }
            // Now, the front has the best partial solution to jump from.
            Line start = deque.getFirst();
            long costToHere = start.getCost(currHeight) + square(currHeight);
            // The logic of this is interesting:
            // We associate h[i]^2 with each state starting at h[i]. We leave out the h[j]
            // term for where it'll end, and only compensate by adding that in once we finally
            // decide on where to end by using the invariant deque.

            // So, it just so happens that we need to do that first association and then
            // the compensation at the same time, which means we add h[j]^2 TWICE, for two
            // distinct reasons. We are always doing this, expecting to jump to a future
            // state. So, when we finally reach the answer, we added it an extra time, and need
            // to subtract. (Since we no longer need to do anything to prepare for future
            // DP states, so it's an artifact of our association method)
            long lumpedPrevCost = square(currHeight) + costToHere + C;
            Line next = new Line(-2 * currHeight, lumpedPrevCost);

            // Now we need to maintain the second invariant:
            // The intersection points of the cost functions must be such that
            // the last two lines must intersect the 3rd from last line in ORDER
            // Invariant: i < j < k => inter(i, j) < inter(i, k)
            // Basically, the nextLine will keep barreling through previous lines
            // with later intersection times.
            while (deque.size() >= 2 && !isValid(deque.get(deque.size() - 2), deque.getLast(), next)) {
                deque.pollLast();
            }
            // Now, the invariant is held, so the added line will be correctly placed
            deque.addLast(next);
        }
        Line end = deque.getLast();
        long totalCost = end.prevCost - square(height[N - 1]);
        out.println(totalCost);
        out.close();
    }

    static long square(long val) {
        return val * val;
    }

    static boolean isValid(Line prev, Line curr, Line next) {
        // Need to compare the two intersection points:
        // y1 = neg2h1 * hInt + prevCost1
        // y2 = neg2h2 * hInt + prevCost2
        // hInt for 1 and 2 = (prevCost1 - prevCost2) / (neg2h2 - neg2h1)
        // hInt for 1 and 3 = (prevCost1 - prevCost3) / (neg2h3 - neg2h1)
        // hInt(1, 2) < hInt(1, 3)
        // (prevCost1 - prevCost2) * (h3 - h1) < (prevCost1 - prevCost3) * (h2 - h1)
        return (prev.prevCost - curr.prevCost) * (next.neg2startH - prev.neg2startH) < (prev.prevCost - next.prevCost) * (curr.neg2startH - prev.neg2startH);
    }

    static class Line {
        long neg2startH, prevCost;
        public Line(long myNeg2StartH, long myPrevCost) {
            neg2startH = myNeg2StartH;
            prevCost = myPrevCost;
        }

        public long getCost(long nextHeight) {
            return neg2startH * nextHeight + prevCost;
        }

        public String toString() {
            return neg2startH + ", " + prevCost;
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