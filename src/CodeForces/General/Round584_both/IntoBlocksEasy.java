/**
 * @author derrick20
 */
import javax.swing.text.Segment;
import java.io.*;
import java.util.*;

public class IntoBlocksEasy {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int q = sc.nextInt();

        int[] lo = new int[200_001];
        Arrays.fill(lo, (int) 1e9);
        int[] hi = new int[200_001];
        int[] ct = new int[200_001];
        HashSet<Integer> values = new HashSet<>();
        for (int i = 0; i < N; i++) {
            int v = sc.nextInt();
            values.add(v);
            lo[v] = Math.min(i, lo[v]);
            hi[v] = i;
            ct[v]++;
        }

        ArrayList<Interval> intervals = new ArrayList<>();
        for (int v : values) {
            intervals.add(new Interval(v, lo[v], hi[v]));
        }
        Collections.sort(intervals);

//        SegmentTree sumTree = new SegmentTree(ct);
        int ans = 0;
        int pos = 0;
        int size = intervals.size();
        while (pos < size) {
            ArrayList<Interval> clump = new ArrayList<>();
            int maxCover = 0;
            int totalCover = 0;
            Interval curr = intervals.get(pos);
            int leftEdge = curr.l;
            int rightEdge = curr.r;

            clump.add(curr);
            maxCover = Math.max(maxCover, ct[curr.v]);
//            totalCover += ct[curr.v];
            while (pos + 1 < size && intervals.get(pos + 1).l < rightEdge) {
                Interval next = intervals.get(pos + 1);
                clump.add(next);
                curr = next;
                rightEdge = Math.max(rightEdge, curr.r); // it expands potentially
                maxCover = Math.max(maxCover, ct[curr.v]);
//                totalCover += ct[curr.v];
                pos++;
            }
            totalCover = rightEdge - leftEdge + 1;
            ans += totalCover - maxCover;
            pos++;
        }

        out.println(ans);
        out.close();
    }

    static class Interval implements Comparable<Interval> {
        int v, l, r;
        public Interval(int color, int left, int right) {
            v = color; l = left; r = right;
        }

        public int compareTo(Interval i2) {
            return l != i2.l ? l - i2.l : r - i2.r;
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