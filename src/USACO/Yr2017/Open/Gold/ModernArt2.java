/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ModernArt2 {
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
//        setupIO();
      setupIO("art2", false);

        int N = sc.nextInt();
        int[] first = new int[N + 1];
        Arrays.fill(first, N);
        int[] last = new int[N + 1];
        Arrays.fill(last, -1);
        for (int pos = 0; pos < N; pos++) {
            int color = sc.nextInt();
            // Find first occurrence of each by minning
            first[color] = Math.min(first[color], pos);
            // Find last by maxing
            last[color] = Math.max(last[color], pos);
        }
        PriorityQueue<Interval> pq = new PriorityQueue<>(Comparator.comparing(Interval::getStart).thenComparing(Interval::getEnd, Comparator.reverseOrder()));

        for (int color = 1; color <= N; color++) {
            if (first[color] <= last[color]) {
                pq.add(new Interval(color, first[color], last[color]));
            }
        }
        // We must create intervals since there may be lots of repeats
        int maxStack = 1;
//        System.out.println(pq);
        // Invariant, the top of the stack's endpoint is always greater than current spot
        // Also, the stack will have earlier endpoints at top, later endpoints lower
        PriorityQueue<Interval> stack = new PriorityQueue<>(Comparator.comparing(Interval::getEnd));
        boolean flag = false;
        for (int pos = 0; pos < N; pos++) {
//            System.out.println(pq.peek());
//            if (pq.size() == 0) {
//                break;
//            }
             if (pq.size() > 0 && pq.peek().start == pos) {
                 // At a position, only one thing could be added, if at all
                 stack.add(pq.poll());
                 // each round, check if possibly improved
                 // This is the only time where we would observe the maximum number
                 // of opening parentheses essentially!
                 maxStack = Math.max(maxStack, stack.size());
             }
             if (stack.size() > 0 && stack.peek().end == pos) {
                 // OR, only one thing can end at a time, also if at all
                 Interval curr = stack.poll();
                 if (stack.size() > 0) {
                     Interval next = stack.peek();
                     if (next.start > curr.start) {
                         // Then this was an illegal bracket closing
                         // It is NECESSARY for the earliest thing that is coming
                         // off of the stack (to maintain the invariant) starts
                         // LATER than the next thing to be popped off. 3 5 5 3
                         // However, if 3 5 3 5, we have an intersection, where
                         // 3's start is before the next start, but we end earlier
                         //
                         flag = true;
                         break; // This is safe!
                     }
                 }
             }
        }
        out.println(flag ? -1 : maxStack);
        out.close();
    }

    static class Interval {
        int color, start, end;

        public Interval(int c, int s, int e) {
            color = c; start = s; end = e;
        }

        public String toString() {
            return "(" + color + ", " + start + ", " + end + ")";
        }

//        public int compareTo(Interval i2) {
//            // We want to sort by the earliest start, then by the latest end
//            return start - i2.start != 0 ? start - i2.start : -(end - i2.end);
//        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
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