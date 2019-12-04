/**
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class BalancedRemovals {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
//        PriorityQueue<Point> pq = new PriorityQueue<>();
        Point[] pts = new Point[N];
        for (int i = 0; i < N; i++) {
            pts[i] = new Point(i + 1, sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        Arrays.sort(pts);
        int i = 0;
        ArrayList<Point> linesFiltered = new ArrayList<>();
        while (i < N) {
            if (i < N - 1 && pts[i].x == pts[i + 1].x && pts[i].y == pts[i + 1].y) {
                out.println(pts[i].idx + " " + pts[i + 1].idx);
                i += 2;
            }
            else {
                linesFiltered.add(pts[i]);
                i++;
            }
        }
        i = 0;
        ArrayList<Point> planesFiltered = new ArrayList<>();
        while (i < linesFiltered.size()) {
            // After this, there will be no x groups
            if (i < linesFiltered.size() - 1 && linesFiltered.get(i).x == linesFiltered.get(i + 1).x) {
                out.println(linesFiltered.get(i).idx + " " + linesFiltered.get(i + 1).idx);
                i += 2;
            }
            else {
                planesFiltered.add(linesFiltered.get(i));
                i++;
            }
        }
        i = 0;
        while (i < planesFiltered.size()) {
            // After this, there will be no x groups
            out.println(planesFiltered.get(i).idx + " " + planesFiltered.get(i + 1).idx);
            i += 2;
        }

        out.close();
    }

    static class Point implements Comparable<Point> {
        int idx, x, y, z;
        public Point(int index, int myX, int myY, int myZ) {
            idx = index; x = myX; y = myY; z = myZ;
        }
        public String toString() {
            return "(" + x + ", " + y + ", " + y + ")";
        }
        public int compareTo(Point p2) {
            if (x != p2.x) return x - p2.x;
            else if (y != p2.y) return y - p2.y;
            else return z - p2.z;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getZ() {
            return z;
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