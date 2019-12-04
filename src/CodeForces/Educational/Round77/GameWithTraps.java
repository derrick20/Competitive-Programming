/**
 * @author derrick20
 * Several key weaknesses in solving this problem:
 * 1. First, we didn't understand the problem fully, since we didn't have the
 * idea that you could move the group up, then go up and down to
 * cover the interval, then pull the group up again.
 * What this means, is we only go over intervals twice MINIMALLY
 *
 * 2. To do this, we needed to be careful about how we add those intervals
 * The key to understand is that we must step onto EACH of those spots
 * inside the interval, because we must return to l-1 from r. So,
 * we must step r - l. In order to do this, we need to merge the intervals
 * correctly.
 *
 * 3. MERGING INTERVALS must be done well. We must MAXIMIZE THE RIGHT INTERVAL
 * as we spread forward. Each time we end the merging of an interval, we add
 * r - l + 1. Then, at the end, we do 2 * doublePass + N + 1.
 */
import java.io.*;
import java.util.*;

public class GameWithTraps {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int M = sc.nextInt();
        int N = sc.nextInt();
        int K = sc.nextInt();
        int T = sc.nextInt();

        ArrayList<Integer> agility = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            agility.add(sc.nextInt());
        }
        Collections.sort(agility, Collections.reverseOrder());
        ArrayList<Trap> traps = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            traps.add(new Trap(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
        Collections.sort(traps);
        // find highest # still possible to do in under T seconds
        // 1100
        // find # of soldiers possible
        int lo = 0; // TODO NEEDED TO HAVE 0 AS A MINNN
        int hi = M;
        // Search through all M soldiers
        while (lo < hi) {
            int mid = (lo + hi) / 2 + 1;
            // Among the first "mid" soldiers, what is the first position
            // of the farthest bomb needed to disarm due to the weakest
            int weakest = agility.get(mid - 1); //minAgility[mid];

            // IT's WRONG TO ONLY GO OUT ONCE!!
            // we should skip those gaps where the team can all walk through
            // (The team will monotically move forward, sweeping one length of
            // N + 1. However, we CAN control how much we move: that is,
            // we only need to make two back and forth passes for those intervals
            // which have danger greater than the weakest link.
            int doublePass = 0;
            ArrayList<Trap> segments = new ArrayList<>();
            for (Trap t : traps) {
                if (t.d > weakest) {
                    segments.add(t);
                }
            }
            for (int ptr = 0; ptr < segments.size(); ptr++) {
                // While it's too strong, we keep merging the intervals
                int l = segments.get(ptr).l;
                int r = segments.get(ptr).r;
                while ((ptr + 1) < segments.size() && segments.get(ptr + 1).l <= r) {
                    // todo KEY ERROR!!!! NEED TO MAXIMIZE RIGHT BOUNDARY!!
                    r = Math.max(r, segments.get(ptr + 1).r);
                    // We can't include the weakness part in the main loop
                    // because there might be a TON OF overlapping intervals!!
                    ptr++;
                }
                // Merge everything that's overlapping
                doublePass += r - l + 1;
                // Need to step onto each spot in the range
                // so there's a +1
                // Now, move to the next interval
//                ptr++;
            }

            // we must disarm everything less than L.
            // --A--B--C
            // A + (B-A)*2 + C-A
            //
            int time = 2 * (doublePass) + N + 1;
            if (time <= T) {
                // we can do at least this many soldiers
                lo = mid;
            }
            else {
                hi = mid - 1;
            }
        }
        out.println(lo);
        out.close();
    }

    static class Trap implements Comparable<Trap> {
        int l, r, d;
        public Trap(int left, int right, int danger) {
            l = left; r = right; d = danger;
        }
        public int compareTo(Trap t2) {
            // Sort so earlier things first. Also, just let the longer things
            // be first to be more inclusive :P
            return l - t2.l != 0 ? l - t2.l : r - t2.r; //t2.d - d;
        }
        public String toString() {
            return "(" + l + ", " + r + ", " + d + ")";
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