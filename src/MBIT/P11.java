/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class P11 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int G = sc.nextInt();
        int A = sc.nextInt();
        PriorityQueue<Point> pq = new PriorityQueue<>();

        double currX = 0;
        double currY = 0;

        Pair prev = new Pair(sc.nextInt(), sc.nextInt());
        currX += prev.x;
        currY += prev.y;
        double prevVx = 0;
        double prevVy = 0;

        double currTimeG = 0;
        for (int i = 1; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            double deltaX = (x - prev.x);
            double deltaY = (y - prev.y);
            double time = Math.sqrt(deltaX * deltaX + deltaY * deltaY) / (1.0 * G);
            double vX = deltaX / time;
            double vY = deltaY / time;

            if (i == N - 1) {
                pq.add(new Point(currTimeG, Integer.MAX_VALUE, 0 - prevVx, 0 - prevVy));
            }
            else {
                pq.add(new Point(currTimeG, currTimeG + time, vX - prevVx, vY - prevVy));
            }
            prevVx = vX;
            prevVy = vY;
            currTimeG += time;
        }

        prev = new Pair(sc.nextInt(), sc.nextInt());

        currX -= prev.x;
        currY -= prev.y;

        prevVx = 0;
        prevVy = 0;
        double currTimeA = 0;
        for (int i = 1; i < M; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            double deltaX = (x - prev.x);
            double deltaY = (y - prev.y);
            double time = Math.sqrt(deltaX * deltaX + deltaY * deltaY) / (1.0 * A);
            double vX = deltaX / time;
            double vY = deltaY / time;
            if (i == M - 1) {
                pq.add(new Point(currTimeA, Integer.MAX_VALUE, -(0 - prevVx), -(0 - prevVy)));
            }
            else {
                pq.add(new Point(currTimeA, currTimeA + time, -(vX - prevVx), -(vY - prevVy)));
            }
            prevVx = vX;
            prevVy = vY;
            currTimeA += time;
        }

        for (Point p : pq) {
            System.out.println(p);
        }



        double runningVx = 0;
        double runningVy = 0;
        double currT = 0;
        double prevT = 0;
        Point p1 = pq.poll();
        Point p2 = pq.poll();
        prevVx = p1.vX+ p2.vX;
        prevVy = p1.vY + p2.vY;
        PriorityQueue<Point> pq2 = new PriorityQueue<>();
        while (pq.size() > 0) {
            Point top = pq.poll();
            currT = top.tS;
            runningVx += prevVx;
            runningVy += prevVy;
            pq2.add(new Point(prevT, currT, runningVx, runningVy));
            prevVx = top.vX;
            prevVy = top.vY;
            prevT = currT;
        }
        System.out.println();
        for (Point p : pq2) {
            System.out.println(p);
        }

        double x0 = currX;
        double y0 = currY;
        double currVx = 0;
        double currVy = 0;
        double minDis = Double.MAX_VALUE;
        while (pq2.size() > 0) {
            Point top = pq2.poll();
            double deltaT = top.tE - top.tS;
            currVx += top.vX;
            currVy += top.vY;

            double xE = x0 + deltaT * currVx;
            double yE = y0 + deltaT * currVy;

            double m = Math.max(1e-20, (currVy / (currVx + 1e-20)));
            double xInt = (m * x0 - y0) / (m + 1.0 / m);
            double yInt = -xInt / m;
            if ((xInt - x0) / currVx < 0) {

            }
            else if ((x0 < xInt && xInt < xE) || (xE < xInt && xInt < x0)) {
                minDis = Math.min(minDis, Math.sqrt(xInt * xInt + yInt * yInt));
            }
            else {
                minDis = Math.min(minDis, Math.sqrt(xE * xE + yE * yE));
            }

            x0 = xE;
            y0 = yE;
        }
        out.println((int) minDis);
        out.close();
    }
    /*
    3 4 6 7
    0 0
    30 0
     0 20
     30 10
     20 -20
     10 0
     10 20

     */

    static class Pair {
        int x, y;
        public Pair(int myX, int myY) {
            x = myX; y = myY;
        }
    }

    static class Point implements Comparable<Point> {
        double tS, tE;
        double vX, vY;
        public Point(double start, double end, double velX, double velY) {
            tS = start; tE = end; vX = velX; vY = velY;
        }

        public int compareTo(Point p2) {
            return (int) (tS - p2.tS);
        }
        public String toString() {
            return tS + " " + tE + " " + vX + " " + vY;
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