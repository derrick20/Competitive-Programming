/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Tournament {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        ArrayList<Integer> arrCurr = new ArrayList<>();
        int mid = -1;
        for (int i = 0; i < N; i++) {
            arrCurr.add(sc.nextInt());
            if (arrCurr.get(i) == -1) {
                mid = i;
            }
        }

        int left = (mid + 1) - 1;
        int right = N - (mid + 1);
        int cost = 0;


        TreeMap<Integer, Integer> currIdxMap = new TreeMap<>();
        TreeMap<Integer, Integer> currValMap = new TreeMap<>();

        // Now, we need to decide how to bribe someone each round
        // Initialize these two inverse maps
        int pos = 0;
        for (int i = mid + 1; i < arrCurr.size(); i++) {
            currIdxMap.put(pos, arrCurr.get(i));
            currValMap.put(arrCurr.get(i), pos);
            pos++;
        }

        // until both sides are killed off continue.
        while (!(left == 0 && right == 0)) {

            TreeMap<Integer, Integer> nextIdxMap = new TreeMap<>();
            TreeMap<Integer, Integer> nextValMap = new TreeMap<>();

            if (left == 0) {
                // Greedily pick the minimum among the remaining to be bribed
                // Only do this when we can't keep picking off the left side's values
                Map.Entry<Integer, Integer> bribed = currValMap.firstEntry();
                int bribedIdx = bribed.getValue();
                int bribedVal = bribed.getKey();
                cost += bribedVal;
                currValMap.remove(bribedVal); // we picked by value, so this is a key for valMap
                currIdxMap.remove(bribedIdx);
            }

            while (currValMap.size() > 0) {
                // Get the right most person, and pair off with the highest value person to the left
                Map.Entry<Integer, Integer> rightMost = currIdxMap.lastEntry();
                int winnerIdx = rightMost.getKey();
                int winnerVal = rightMost.getValue();
                // Now delete this from both maps to avoid them from double-counting
                // (matching him with himself because he's also the highest value)
                currIdxMap.remove(winnerIdx); // picked by idx, so it's a key for idxMap
                currValMap.remove(winnerVal);

                Map.Entry<Integer, Integer> maxLeft = currValMap.lastEntry();
                int loserIdx = maxLeft.getValue();
                int loserVal = maxLeft.getKey();
                currValMap.remove(loserVal); // picked by val, so it's a key for valMap
                currIdxMap.remove(loserIdx);

                // Now, in the next iteration, we will only contain the latter of this pair we
                // just annihilated. Add this new pair to the next maps
                nextIdxMap.put(winnerIdx, winnerVal);
                nextValMap.put(winnerVal, winnerIdx);
            }

            currIdxMap = nextIdxMap;
            currValMap = nextValMap;
            // Both the left and right side are shrinking.

            left /= 2;
            right /= 2;

//            ArrayList<Integer> arrNext = new ArrayList<>();
//            for (int i = 0; i < left; i++) {
//                arrNext.add(0); // filler values
//            }
//            mid = left;
//            arrNext.add(-1);
//            arrNext.addAll(currIdxMap.values());
//            arrCurr = arrNext;
        }

        out.println(cost);
        out.close();
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