/**
 * @author derrick20
 * Key logic: Think in terms of splitting into different parts.
 * In this case, we initially pick two parts and decide to solve on
 * each side.
 * We do 2 * (n-2) queries, n-2 for sign, n-2 for area.
 * This lets us prepare for the later sides.
 * It makes intuitive sense that the issue here is the symmetry with
 * JUST the area info alone. Like, we know two things to the right
 * of the A-B midline are equally far out, but don't know whether
 * above or below x axis. Thus, we need one more symmetry breaker,
 * which can be done by artificially finding the farthest points
 * on each side of the midline.
 *
 * We can find those far points during the same 2n-4 operations initially!
 * Now, all we need to do is figure out which are above and below the
 * x-axis. So, there are n-4 points left. We use the line A-farRight
 * and A-farLeft to serve as the dividers. In each side, we now cut
 * through and determine all remaining orientations!
 *
 * So, the total operations was 3n-6
 * I don't understand how theirs was 3n-7 operations though @#!?%#$?
 */
import java.io.*;
import java.util.*;

public class PointOrdering {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();

        int A = 1;
        int B = 2;

        // Left and right will contain all other points EXCEPT A AND B
        ArrayList<Pair> left = new ArrayList<>();
        ArrayList<Pair> right = new ArrayList<>();

        Pair farLeft = new Pair(-1, 0);
        Pair farRight = new Pair(-1, 0);
        for (int i = 3; i <= N; i++) {
            out.println(2 + " " + A + " " + B + " " + i);
            out.flush();
            int sign = sc.nextInt();

            out.println(1 + " " + A + " " + B + " " + i);
            out.flush();
            long area = sc.nextLong();

            Pair p = new Pair(i, area);
            if (sign > 0) {
                right.add(p);
                if (area > farRight.val) {
                    farRight = p;
                }
            }
            else {
                left.add(p);
                if (area > farLeft.val) {
                    farLeft = p;
                }
            }
        }

        // These will contain all points except TOP, BOTTOM, LEFT, RIGHT
        ArrayList<Pair> topRight = new ArrayList<>();
        ArrayList<Pair> bottomRight = new ArrayList<>();

        for (Pair p : right) {
            if (p.idx != farRight.idx) {
                out.println(2 + " " + A + " " + farRight.idx + " " + p.idx);
                out.flush();

                int sign = sc.nextInt();
                if (sign > 0) {
                    topRight.add(p);
                }
                else {
                    bottomRight.add(p);
                }
            }
        }

        ArrayList<Pair> topLeft = new ArrayList<>();
        ArrayList<Pair> bottomLeft = new ArrayList<>();

        for (Pair p : left) {
            if (p.idx != farLeft.idx) {
                out.println(2 + " " + A + " " + farLeft.idx + " " + p.idx);
                out.flush();

                int sign = sc.nextInt();
                // THIS IS THE OPPOSITE OF BEFORE,
                // since it's mirrored!!
                if (sign < 0) {
                    topLeft.add(p);
                }
                else {
                    bottomLeft.add(p);
                }
            }
        }
/*
6
-1
10
-1
8
1
10
1
8
-1
1
 */
        // Make topLeft increasing order,
        // bottomLeft decreasing.
        // bottomRight increasing,
        // topRight decreasing
        Collections.sort(topLeft);
        Collections.sort(bottomLeft, Collections.reverseOrder());
        Collections.sort(bottomRight);
        Collections.sort(topRight, Collections.reverseOrder());

        StringBuilder sb = new StringBuilder("0 ");
        sb.append(A + " ");
        for (Pair p : topLeft) {
            sb.append(p.idx + " ");
        }
        if (farLeft.idx != -1) {
            // There actually had to be stuff between A and B for this
            // to be possible
            sb.append(farLeft.idx + " ");
        }
        for (Pair p : bottomLeft) {
            sb.append(p.idx + " ");
        }
        sb.append(B + " ");
        for (Pair p : bottomRight) {
            sb.append(p.idx + " ");
        }
        if (farRight.idx != -1) {
            sb.append(farRight.idx + " ");
        }
        for (Pair p : topRight) {
            sb.append(p.idx + " ");
        }
        out.println(sb);
        out.flush();
        out.close();
    }

    static class Pair implements Comparable<Pair> {
        int idx;
        long val;
        public Pair(int i, long v) {
            idx = i; val = v;
        }
        public int compareTo(Pair p2) {
            return val - p2.val < 0 ? -1 : 1;
        }
        public String toString() {
            return "(" + idx + ", " + val + ")";
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