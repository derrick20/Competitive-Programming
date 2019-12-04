/**
 ID: d3rrickl
 LANG: JAVA
 PROG: 2016 USACO Open Gold #1

 Made a stupid optimization of thinking that the bottom left point
 would always the min x and min y of everything, which is WRONG
 Suppose the points were designed to be like a line y = -x + 100

 This would means you are wrongfully using a massive rectangle as your
 lower left subrectangle!!! So, basically, you shouldn't over optimize
 if not necessary, since that can be shaved down post hoc.!!!
 */
import java.io.*;
import java.util.*;

/*
7
4 2
8 10
2 7
1 1
9 12
14 7
2 3
 */
public class SplittingTheField {
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
//        setupIO("split", false);

        N = sc.nextInt();
        Point[] points = new Point[N];

        botLeft = new Point(oo, oo);
        topRight = new Point(0, 0);
        for (int i = 0; i < N; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
            botLeft.minimize(points[i]);
            topRight.maximize(points[i]);
        }
        Arrays.sort(points, Comparator.comparing(Point::getX).thenComparing(Point::getY));
        long best = oo;
//        System.out.println(Arrays.toString(points));
        best = Math.min(best, solve(points));

        Arrays.sort(points, Comparator.comparing(Point::getY).thenComparing(Point::getX));
//        System.out.println(Arrays.toString(points));

        best = Math.min(best, solve(points));

        long initial = botLeft.area(topRight);
        out.println(initial - best);
        out.close();
    }
    static int N;
    static long oo = (long) 9e18;
    static Point botLeft, topRight;

    static long solve(Point[] points) {
        Point[] prefixBotLeft = new Point[N + 1];
        Point[] prefixTopRight = new Point[N + 1];
        prefixBotLeft[0] = new Point(oo, oo);
        prefixTopRight[0] = new Point(0, 0);
        // prefix[i] stores the extreme boundaries of the first i

        Point[] suffixTopRight = new Point[N + 1];
        Point[] suffixBotLeft = new Point[N + 1];
        suffixBotLeft[0] = new Point(oo, oo);
        suffixTopRight[0] = new Point(0, 0);

        // suffix[i] stores the extreme boundaries of the last i
        for (int taken = 1; taken <= N; taken++) {
            Point left = points[taken - 1];
            prefixBotLeft[taken] = new Point(prefixBotLeft[taken - 1]);
            prefixBotLeft[taken].minimize(left);
            prefixTopRight[taken] = new Point(prefixTopRight[taken - 1]);
            prefixTopRight[taken].maximize(left);

            Point right = points[N - taken];
            suffixBotLeft[taken] = new Point(suffixBotLeft[taken - 1]);
            suffixBotLeft[taken].minimize(right);
            suffixTopRight[taken] = new Point(suffixTopRight[taken - 1]);
            suffixTopRight[taken].maximize(right);
        }
//        System.out.println(Arrays.toString(prefixTopRight));
//        System.out.println(Arrays.toString(suffixBotLeft));

        long best = oo;
        for (int i = 1; i <= N - 1; i++) {
            // try letting the left take from 1 to N - 1 of the points
            // (letting right take from N - 1 to 1 of the points)
//            if (prefixTopRight[i].y >= suffixBotLeft[N - i].y && prefixTopRight[i].x >= suffixBotLeft[N - i].x) {
////                System.out.println("hey.");
//                continue;
//            }
//            System.out.println(botLeft + " " + prefixTopRight[i]);
//            System.out.println(suffixBotLeft[N - i] + " " + topRight);
            long area1 = prefixBotLeft[i].area(prefixTopRight[i]);
            long area2 = suffixBotLeft[N - i].area(suffixTopRight[N - i]);
//            System.out.println(area1 + " " + area2);
            best = Math.min(best, area1 + area2);
//            System.err.println(best);
        }
        return best;
    }

    static class Point {
        long x, y;
        public Point(Point p2) {
            x = p2.x;
            y = p2.y;
        }
        public Point(long xx, long yy) {
            x = xx; y = yy;
        }
        public long getX() {
            return x;
        }
        public long getY() {
            return y;
        }
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
        public long area(Point p2) {
            // assume we are lower left
            return  (p2.x - x) * (p2.y - y);
        }
        public void maximize(Point p2) {
            x = Math.max(x, p2.x);
            y = Math.max(y, p2.y);
        }
        public void minimize(Point p2) {
            x = Math.min(x, p2.x);
            y = Math.min(y, p2.y);
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