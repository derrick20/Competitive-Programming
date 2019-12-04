/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

/*
0 1 4 5
2 3 6 7
8 9 12 13
10 11 14 15

0 4 2 6
8 12 10 14
1 5 3 7
9 13 11 15
 */
public class MagicGrid {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int block = N / 2;
        int[][] grid = new int[N][N];
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                int amt = (i % block) * block + (j % block);
//                int offset = ((i / block) * 2 + j / block) * (block * block);
//                grid[i][j] = amt + offset;
//            }
//        }
//        for (int i = 0; i < N; i++) {
//            int xor = 0;
//            for (int j = 0; j < N; j++) {
//                xor ^= grid[i][j];
//            }
//            out.println(xor);
//        }
//        for (int i = 0; i < N; i++) {
//            int xor = 0;
//            for (int j = 0; j < N; j++) {
//                xor ^= grid[j][i];
//            }
//            out.println(xor);
//        }
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                out.print(grid[i][j] + " ");
//            }
//            out.println();
//        }
        int curr = 0;
//        int[][] grid = new int[N][N];
        // /*
        // Each block uses the curr, multiplying it by 4. Since the
        // block is an even size, the 00001111, 22223333, 00002222, 11113333
        // will all cancel out. I think the above fails because I was multiplying
        // by a non power of 2 (for 12, it'd be times 6!!
        for (int i = 0; i < block; i++) {
            for (int j = 0; j < block; j++) {
                grid[i][j] = 4 * curr;
                grid[i + block][j] = 4 * curr + 1;
                grid[i][j + block] = 4 * curr + 2;
                grid[i + block][j + block] = 4 * curr + 3;
                curr++;
            }
        }

        // */
        for (int i = 0; i < N; i++) {
            int xor = 0;
            for (int j = 0; j < N; j++) {
                xor ^= grid[i][j];
            }
            out.println(xor);
        }
        for (int i = 0; i < N; i++) {
            int xor = 0;
            for (int j = 0; j < N; j++) {
                xor ^= grid[j][i];
            }
            out.println(xor);
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                out.print(grid[i][j] + " ");
            }
            out.println();
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