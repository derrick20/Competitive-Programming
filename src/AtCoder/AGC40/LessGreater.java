/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LessGreater {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        String s = sc.next();
        ArrayList<Pair> arr = new ArrayList<>();
        int pos = 0;
        while (pos < s.length()) {
            int curSize = 1;
            char c = s.charAt(pos);
            while (pos + 1 < s.length() && s.charAt(pos + 1) == c) {
                curSize++;
                pos++;
            }
            arr.add(new Pair(c, curSize));

            pos++; // Now finish, and jump forward
        }

        long ans = arr.get(0).size * (arr.get(0).size + 1) / 2;
        for (int i = 1; i < arr.size(); i++) {
            ans += arr.get(i).size * (arr.get(i).size + 1) / 2;
            // The other case is unaffeccted (put zeros in the middle
            if (arr.get(i - 1).type == '<' && arr.get(i).type == '>') {
                ans -= Math.min(arr.get(i).size, arr.get(i - 1).size);
            }
        }
        out.println(ans);
        out.close();
    }

    static class Pair {
        char type;
        long size;
        public Pair(char t, long s) {
            type = t; size = s;
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