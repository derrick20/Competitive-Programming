/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RemainderMolecule {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        int[] input = new int[N];
        TreeMap<Integer, Integer> values = new TreeMap<>();
        TreeSet<Integer> seen = new TreeSet<>();
        for (int i = 0; i < input.length; i++) {
            input[i] = sc.nextInt();
            seen.add(input[i]);
        }
        // CLEAN OUT ALL DUPLICATES, THEN SOLVE IT
        N = seen.size();
        int[] arr = new int[N];
        int temp = 0;
        for (int v : seen) {
            arr[temp++] = v;
        }
        int rootN = (int) Math.sqrt(N);

        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            values.put(arr[i], i);
        }

        int maxVal = arr[N - 1];
        int[] floor = new int[maxVal + 1];
        // For each value in the array (up to the max), we track
        // the greatest value less than the value specified by an index
        // floor[3] = max value in array below 3
        //     3   5       9
        // 0 0 0 3 3 5 5 5 5 9 ...
        // 1 2 3 4 5 6 7 8 9 10
        int pos = 0;
        for (int val = 1; val <= maxVal; val++) {
            if (pos < N && arr[pos] == val - 1) {
                floor[val] = arr[pos];
                /** KEY ISSUE:
                 *  YOU HAVE TO ACCOUNT FOR DUPLICATE VALUES!!!!
                 *  So, what that means is you need to move the pointer
                 *  forward to skip repeated values!!
                 *  However, even better would be to get rid of duplicates
                 *  in the beginning!
                  */
//                while (pos < N && arr[pos] == val - 1)
                pos++;
            }
            else {
                floor[val] = floor[val - 1];
            }
        }
//        System.out.println(Arrays.toString(floor));

        // Precompute for all things sqrt(N) and bigger, the biggest value less
        // than a given multiple of it inside the array.
        // for a given value, and k, we will determine: (k-1)*value + rem.
        // We try all k's from 1 to sqrt(N)
        // and then see the remainder at each one. Since value is big enough, we
        // are able to do this more effectively, despite there being ~N such values

        // On the other hand, the lower sqrt(N) is a small set, so we can just
        // check all N against it to see the best remainder from that case

        int rem = 0;
        // For each value, try the k from 2 to ~sqrt(N) multiples, and see if
        // we can improve our best remainder. We go frm 2 up because it must be
        // GREATER than a, the thing we are modding with
        int start = values.higherEntry(rootN).getValue();
        for (int i = start; i < N; i++) {
            // Keep going while there exists something from a(k-1)... ak, because
            // we are looking for some residue below ak to optimize our answer
            // since we can represent it as a(k-1) + rem, and we want rem to be
            // high, so we want the biggest value below ak.
            int a = arr[i];
            for (int k = 2; a * (k - 1) <= maxVal; k++) {
                int b;
                if (a * k > maxVal) {
                    b = maxVal;
                }
                else {
                    b = floor[a * k];
                }
                // if it goes outside of maxVal, the floor value will remain
                // the maxVal in the array!
                rem = Math.max(rem, b % a);
            }
        }

        // Now we do the easier, small set:
        // For each of the numbers <= sqrt(N), try all values greater (~N) and see
        // if we can improve the remainder.
        for (int i = 0; i < start; i++) {
            int a = arr[i];
            for (int j = i + 1; j < N; j++) {
                int b = arr[j];
                rem = Math.max(rem, b % a);
            }
        }
        out.println(rem);
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