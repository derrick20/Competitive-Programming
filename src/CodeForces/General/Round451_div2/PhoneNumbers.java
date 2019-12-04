/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PhoneNumbers {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        HashMap<String, HashSet<String>> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String name = sc.next();
            // Current name
            int size = sc.nextInt();
            if (!map.containsKey(name)) {
                map.put(name, new HashSet<>());
            }
            for (int j = 0; j < size; j++) {
                String number = sc.next();
                map.get(name).add(number);
            }
        }
//        System.out.println(map);
        out.println(map.size());
        for (String name : map.keySet()) {
            ArrayList<String> arr = new ArrayList<>(map.get(name));
            // Try all combinations and delete unecessary ones
//            System.out.println(arr);
            boolean[] deleted = new boolean[arr.size()];
            for (int i = 0; i < arr.size(); i++) {
                // first string matching with second
                StringBuilder first = new StringBuilder(arr.get(i)).reverse();
                for (int j = i + 1; j < arr.size(); j++) {
                    StringBuilder second = new StringBuilder(arr.get(j)).reverse();
                    boolean poss = true;
                    // check for mismatched chars (reversed allows us to check with prefixes)
                    search : for (int k = 0; k < Math.min(first.length(), second.length()); k++) {
                        if (first.charAt(k) != second.charAt(k)) {
                            poss = false;
                            break search;
                        }
                    }
                    if (poss) {
                        // can delete one of them
                        if (second.length() < first.length()) {
                            deleted[j] = true;
                        }
                        else {
                            deleted[i] = true;
                        }
                    }
                }
            }

            StringBuilder res = new StringBuilder();
            int ct = 0;
            for (int i = 0; i < arr.size(); i++) {
                if (!deleted[i]) {
                    res.append(" " + arr.get(i));
                    ct++;
                }
            }
            out.println(name + " " + ct + res);
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

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=nextChar();
            for(;(c<'0' || c>'9'); c = nextChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=nextChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public char nextChar(){
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

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c>32) {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c!='\n') {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=nextChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}