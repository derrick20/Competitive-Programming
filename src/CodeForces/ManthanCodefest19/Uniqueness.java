/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Uniqueness {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        int[] rev = new int[N];
        int left = -1;
        boolean found = false;
        HashSet<Integer> seen = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            rev[N - i - 1] = arr[i];

            if (!seen.contains(arr[i])) {
                seen.add(arr[i]);
            }
            else if (!found && seen.contains(arr[i])) {
                left = i;
                found = true;
            }
        }
        int left2 = -1;
        found = false;
        seen = new HashSet<>();
        for (int i = 0; i < N; i++) {
            if (!seen.contains(rev[i])) {
                seen.add(rev[i]);
            } else if (!found && seen.contains(rev[i])) {
                left2 = i;
                found = true;
            }
        }

        int ans = 0;
        if (left != -1) {
            for (int right = 0; right < N; right++) {
                boolean unique = true;
                HashSet<Integer> visited = new HashSet<>();
                for (int i = 0; i < left; i++) {
                    if (!visited.contains(arr[i])) {
                        visited.add(arr[i]);
                    } else {
                        unique = false;
                        break;
                    }
                }
                for (int i = right + 1; i < N; i++) {
                    if (!visited.contains(arr[i])) {
                        visited.add(arr[i]);
                    } else {
                        unique = false;
                        break;
                    }
                }
                if (unique) {
                    ans = right - left + 1;
                    break;
                }
            }
        }
        if (left2 != -1) {
            for (int right = 0; right < N; right++) {
                boolean unique = true;
                HashSet<Integer> visited = new HashSet<>();
                for (int i = 0; i < left2; i++) {
                    if (!visited.contains(rev[i])) {
                        visited.add(rev[i]);
                    } else {
                        unique = false;
                        break;
                    }
                }
                for (int i = right + 1; i < N; i++) {
                    if (!visited.contains(rev[i])) {
                        visited.add(rev[i]);
                    } else {
                        unique = false;
                        break;
                    }
                }
                if (unique) {
                    ans = Math.min(ans, right - left2 + 1);
                    break;
                }
            }
        }
        out.println(ans);

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