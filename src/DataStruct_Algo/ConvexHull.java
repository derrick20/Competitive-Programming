/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ConvexHull {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
//            System.out.println(N);
            Point[] points = new Point[N];

            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                points[i] = new Point(x, y);
            }

            int refIndex = getRefIndex(points);
            // set the reference points after sorting properly
            Point.ref = new Point(points[refIndex].x, points[refIndex].y);
            // now, we can sort by angle w.r.t. that reference point
            Arrays.sort(points);
            ArrayList<Point> filtered = new ArrayList<>();
            for (int i = 0; i < points.length - 1; i++) {
                if (!points[i].collinear(points[i + 1])) {
                    filtered.add(points[i]);
                }
            }
            for (Point p : filtered) {
                System.out.println(p);
            }
            System.out.println();
            Point[] arr = new Point[filtered.size()];
            ArrayList<Point> convexHull = findConvexHull(points); //filtered.toArray(arr));
            for (Point p : convexHull) {
                out.println(p);
            }

            // Now N^2 time
            double max = 0;
            int size = convexHull.size();
            for (int i = 0; i < size; i++) {
                Point p1 = convexHull.get(i);
                for (int j = i + 1; j < size; j++) {
                    Point p2 = convexHull.get(j);
                    double squaredDistance = squaredDist(p1, p2);
                    if (squaredDistance > max) {
                        max = squaredDistance;
                    }
                }
            }
//            System.out.println(convexHull.size());
            out.printf("%.2f\n", Math.sqrt(max));
        }
        out.close();
    }

    static int N;

    static ArrayList<Point> findConvexHull(Point[] points) {
        Stack<Point> stack = new Stack<>();
        stack.push(points[points.length - 1]);
        stack.push(points[0]);
        stack.push(points[1]);
        int i = 2;
        // A key fact is that it is sorted by angle WRT x-axis, therefore this
        // is an optimal way of filling up our hull correctly
        while (i < points.length) {
            Point next = points[i];
            Point pivot = stack.pop(); // this is the point in contention, the potential weak link!
            Point prev = stack.peek();
            while (!counterclockwise(prev, pivot, next)) {
                // Ahah! so that pivot truly was weak, so we don't let it back into our hull
                // Instead, we look at the next weak link! Iterate over and over...
                pivot = stack.pop();
                prev = stack.peek();
            }
            // Whether we threw out 1 or 1000 pivots, now that it is all clockwise,
            // we lift the punishment and let it rejoin our hull, and finally we can put in the new point!
            stack.push(pivot);
            stack.push(next);
            i++;
        }

        ArrayList<Point> convexHull = new ArrayList<>(stack);
        return convexHull;
    }

    static double epsilon = 0;

    // sort by lowest y first, then by lowest x to break ties
    static int getRefIndex(Point[] points) {
        int index = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].y < points[index].y) {
                index = i;
            }
            else if (points[i].y == points[index].y && points[i].x < points[index].x) {
                index = i;
            }
        }
        return index;
    }

    static double squaredDist(Point p1, Point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

    static boolean counterclockwise(Point p1, Point p2, Point p3) {
        Point v1 = p1.getVector(p2);
        Point v2 = p2.getVector(p3);
        // > 0 means we skip collinear points! >= otherwise!
        return crossProduct(v1, v2) > 0;
    }

    static double crossProduct(Point v1, Point v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }

    static double magnitude(Point v) {
        return Math.sqrt(v.x * v.x + v.y * v.y);
    }

    static class Point implements Comparable<Point> {
        static Point ref;
        int x, y;

        public Point(int xx, int yy) {
            x = xx;
            y = yy;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public Point getVector(Point p) {
            return new Point(p.x - this.x, p.y - this.y);
        }

        public boolean collinear(Point p2) {
            Point v1 = ref.getVector(this);
            Point v2 = ref.getVector(p2);
            return crossProduct(v1, v2) < epsilon;
        }

        public boolean isZero() {
            return x == 0 && y == 0;
        }

        public int compareTo(Point p2) {
            Point v1 = ref.getVector(this);
            Point v2 = ref.getVector(p2);

            double crossProduct = crossProduct(v1, v2);
            if (crossProduct > 0) {
                return -1;
            } else if (crossProduct < 0) {
                return 1;
            } else {
                return magnitude(v1) < magnitude(v2) ? -1 : 1;
            }
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