/**
 * @author derrick20
 * Fix this later!
 */
import java.io.*;
import java.util.*;

public class FairNutAndRectangles {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        Point[] arr = new Point[N];
        for (int i = 0; i < N; i++) {
            arr[i] = new Point(sc.nextLong(), sc.nextLong(), sc.nextLong());
        }
        Arrays.sort(arr);
/*
Test 21 !!
126348928230692103
104559046981522768
133364902181970461
 */
        LinkedList<Line> dq = new LinkedList<>();
        dq.addFirst(new Line(0, 0));
//        dq.add(new Line(-arr[0].x, arr[0].x * arr[0].y - arr[0].cost));
        // O(N) points to check. Each point will be added and removed at most once
        // (the while loops will sum to O(N) basically)
        long best = 0;
        for (int i = 0; i < N; i++) {
            // Maintain first invariant:
            // i < j => value(i to here) > value(j to here)
//            dq.addFirst(new Line(0, 0));
            while (dq.size() >= 2 && dq.getFirst().getValue(arr[i]) <= dq.get(1).getValue(arr[i])) {
                dq.pollFirst();
            }
            // Now, the state at the front has the highest value possible
            // Basically, we are checking at a given x,  what is the highest
            // value possible. Since we are adding the lines progressively,
            // the slopes will be increasing (once this line is succeeded,
            // it will never be optimal again, so we can throw it out)
            Line curr = dq.getFirst();
            long dpPrev = curr.getValue(arr[i]);
            // This line now has the previous value obtained, and the added value
            // of moving to the current point

            long lumpedValue = arr[i].x * arr[i].y - arr[i].cost + dpPrev;
            best = Math.max(best, lumpedValue); // Keep seeing if this is better
            // Additionally, we must now put in the extra components into the y-intercept
            Line next = new Line(-arr[i].x, lumpedValue);

            while (dq.size() >= 2 && !isValid(dq.get(dq.size() - 2), dq.getLast(), next)) {
                dq.pollLast();
            }
            dq.addLast(next); // Now this line will be added correctly
        }
//        long ans = dq.getLast().prevValue;
        out.println(best);
        out.close();
    }


    // ValFrom0 = (x - x0)y + dpPrev0
    // = -y * x0 + xy + dpPrev0
    // ValFrom1 = (x - x1)y + dpPrev1
    // = -y * x1 + xy + dpPrev1
    // ValFrom2 = (x - x2)y + dpPrev2
    // = -y * x2 + xy + dpPrev2

    // To compare the intersection points, the xy is independent
    // So, to find the intersection, of V0 and V1
    // negX1 * y + dpPrev1 = negX0 * y + dpPrev0
    // yInt0,1 = (dpPrev0 - dpPrev1) / (negX1 - negX0)
    // The intersection of V0 and V2:
    // yInt0,2 = (dpPrev0 - dpPrev2) / (negX2 - negX0)

    // The goal is to see whether the intersection is correctly ordered
    // This should mean that they intersect at DEcreasing y's
    // WRONG: yInt0,1 < yInt0,2
    // ACTUALLY >, since the slopes are negative
    // Also, they might be PARALLEL!!
    static double epsilon = Double.MIN_VALUE;
    static boolean isValid(Line prev, Line curr, Line next) {
        long num1 = (prev.prevValue - curr.prevValue);
        long denom1 = (curr.negX - prev.negX);
        long num2 = (prev.prevValue - next.prevValue);
        long denom2 = (next.negX - prev.negX);
//        if (denom1 == 0 && curr.prevValue < prev.prevValue) {
//            return false; // This means the middle line is automatically bad
//        }
        return num1 * denom2 > num2 * denom1;
    }

    static class Line {
        long negX, prevValue;
        public Line(long myNegX, long myPrevValue) {
            negX = myNegX; prevValue = myPrevValue;
        }

        // Val(from point 1) = (x - x1)y + dpPrev1
        // = -y * x1 + xy + dpPrev1
        // So, if point 2 is fixed, then the val(from point 0)
        // = -y * x0 + xy + dpPrev0
        public long getValue(Point next) {
            return negX * next.y + prevValue;
        }
    }

    static class Point implements Comparable<Point> {
        long x, y, cost;

        public Point(long myX, long myY, long myCost) {
            x = myX; y = myY; cost = myCost;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public int compareTo(Point p2) {
            return (int) (x - p2.x);
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