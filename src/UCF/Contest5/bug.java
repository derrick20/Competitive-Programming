/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;
public class bug {
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
            }
            else if (crossProduct < 0) {
                return 1;
            }
            else {
                return magnitude(v1) < magnitude(v2) ? -1 : 1;
            }
        }
        /*public int compareTo(Point p2) {
            if (this.x == p2.x && this.y == p2.y) {
                return 0;
            }
            Point v1 = ref.getVector(this);
            Point v2 = ref.getVector(p2);
            System.out.println(v1 + " " + v2);
            // Must watch out for arc tan division by 0 issues!
            if (v1.isZero()) {
                // we are literally the pivot, so we are first
                return -1;
            }
            else if (v2.isZero()) {
                // then they are pivot, and we come later
                return 1;
            }
            else if (collinear(v1, v2)) {
                // If they are somehow equal, one of them won't matter basically
                return magnitude(v1) < magnitude(v2) ? -1 : 1;
            }
            else {
                // subtracts the angle with respect to y = 0 (actually y = ref.y, since we have vectors)
                // Thus, sorts by angles!
                double angle1 = Math.atan2(v1.y, v1.x);
                double angle2 = Math.atan2(v2.y, v2.x);
                // they'll never be equal since that's collinearity (checked alreadY)
                return angle1 < angle2 ? -1 : 1;
            }
        }*/
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
