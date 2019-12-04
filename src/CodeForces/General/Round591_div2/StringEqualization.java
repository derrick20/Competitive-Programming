/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class StringEqualization {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int Q = sc.nextInt();
        while (Q-->0) {
            String s = sc.next();
            String t = sc.next();
            int[] seen1 = new int[26];
            int[] seen2 = new int[26];

            for (char c : s.toCharArray()) {
                seen1[c - 'a']++;
            }
            for (char c : t.toCharArray()) {
                seen2[c - 'a']++;
            }
            boolean poss = false;
//            boolean allOnes = true;
            for (int i = 0; i < 26; i++) {
//                if ((seen1[i] > 0 && seen2[i] == 0) || seen2[i] > 0 && seen1[i] == 0) {
//                    poss = false;
//                }
                if (seen1[i] > 0 && seen2[i] > 0) {
                    poss = true;
//                    // if one is greater than 1, buffer is enough
//                    if (seen1[i] > 1) {
//                        allOnes = false; // found one!
//                    }
////                     if (seen1[i] == 1 && seen2[i] == 1) {
////                         if (s.indexOf(i + 'a') != t.indexOf(i + 'a')) {
////                             poss = false;
////                         }
////                     }
                }
            }
            out.println(poss ? "YES" : "NO");
        }

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