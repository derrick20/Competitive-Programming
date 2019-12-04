/**
 * @author derrick20
 * In general, with square problems, we think about bounding cases
 * This is similar to Robot Breakout Round 575 Div 3 C.
 * Basically, I consider two cases for each black rectangle, for four cases
 * total.
 *
 * First, a rectangle must cover from bottom to top or left to right as
 * a prerequisite to altering the rectangle. Otherwise, part will be left
 * open, and it would have had no effect on the limiting bounds (the MAX
 * and MIN x and y). If you don't kill them all, one will persist!
 * So, we update the min and max x and y based on updating to be less restrictive
 * due to the covering of the black rectangle. mins should increase, maxes should
 * decrease. Repeat this process for the second rectanglee.
 * Only now, we can check if the remanining bounding lines form a rectangle
 * with positive area. If not, we're done (basically check if xMin <= xMax and
 * yMin <= yMax. If false, then COVERED)
 *
 * Another clever approach is to use the Rectangle class from java.
 * This allows you to return the intersection of two rectangles. Then,
 * we just sum the areas of the two covered parts, subtract the area of
 * the potential intersection of the covered parts, and see if this is equal
 * to the total area of the white rectangle. This PIE allows for exact checking!
 * Also, we didn't HAVE to use the Java class, we could've also just manually found
 * intersections of rectangles.
 * DO this by using max of lower points and min of upper points.
 * Do this for x and y.
 * Then, do this for each rectangle, pairing black and white
 * Then, pair the two intersections, for the repeated area.
 *
 * KEY Observation:
 * If there is any number that's small in the constraints: (2 black squares)
 * we can immediately suspect drawing out all cases as an option,
 * or also PIE could work!
 *
 */
import org.w3c.dom.css.Rect;

import java.io.*;
import java.util.*;

public class WhiteSheet {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rectangle W = new Rectangle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        Rectangle B1 = new Rectangle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        Rectangle B2 = new Rectangle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        Rectangle X1 = W.intersect(B1);
        Rectangle X2 = W.intersect(B2);
        Rectangle X12 = X1.intersect(X2);
        out.println(X1.area() + X2.area() - X12.area() == W.area() ? "NO" : "YES");
        /*
        int x1 = sc.nextInt();
        int y1 = sc.nextInt();
        int x2 = sc.nextInt();
        int y2 = sc.nextInt();
        int x3 = sc.nextInt();
        int y3 = sc.nextInt();
        int x4 = sc.nextInt();
        int y4 = sc.nextInt();
        int x5 = sc.nextInt();
        int y5 = sc.nextInt();
        int x6 = sc.nextInt();
        int y6 = sc.nextInt();

        if (x5 <= x1 && x6 >= x2) {
            if (y6 >= y1 && y5 <= y1) {
                y1 = y6; // elevate it
            }
            if (y5 <= y2 && y6 >= y2) {
                y2 = y5;
            }
        }
        if (x3 <= x1 && x4 >= x2) {
            if (y4 >= y1 && y3 <= y1) {
                y1 = y4; // elevate it
            }
            if (y3 <= y2 && y4 >= y2) {
                y2 = y3;
            }
        }
        if (y5 <= y1 && y6 >= y2) {
            if (x6 >= x1 && x5 <= x1) {
                x1 = x6;
            }
            if (x5 <= x2 && x6 >= x2) {
                x2 = x5;
            }
        }
        if (y3 <= y1 && y4 >= y2) {
            if (x4 >= x1 && x3 <= x1) {
                x1 = x4;
            }
            if (x3 <= x2 && x4 >= x2) {
                x2 = x3;
            }
        }
//        out.println(x1 + " "  + y1 + " " + x2 + " " + y2);
        out.println((y2 <= y1 || x2 <= x1) ? "NO" : "YES");

         */
        out.close();
    }
//
    static class Rectangle {
        int xa, ya, xb, yb;
        public Rectangle(int a, int b, int c, int d) {
            xa = a; ya = b; xb = c; yb = d;
        }

        public Rectangle intersect(Rectangle N) {
            Rectangle intersection = new Rectangle(0, 0, 0, 0);
            // Make the lower left part as upper right as possible
            intersection.xa = Math.max(xa, N.xa);
            intersection.ya = Math.max(ya, N.ya);

            // Make the upper right part as lower left as possible
            intersection.xb = Math.min(xb, N.xb);
            intersection.yb = Math.min(yb, N.yb);
            return intersection;
        }

        public long area() {
            if (xa < xb && ya < yb) {
                long area = (long) (xb - xa) * (yb - ya);
                return area;
            }
            else {
                return 0;
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