/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MallardExpedition {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] dist = new long[N];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = sc.nextLong();
        }
        char[] type = sc.next().toCharArray();

        // Step through, maximizing stamina. If not enough before a lava,
        // then tread in place
        long stamina = 0;
        long scaledExtraGrass = 0;
        long time = 0;
        boolean seenWater = false;
        for (int i = 0; i < N; i++) {
            if (type[i] == 'W') {
                stamina += dist[i];
                time += 3 * dist[i];
                seenWater = true;
            }
            else if (type[i] == 'G') {
                stamina += dist[i];
                time += 5 * dist[i];
                scaledExtraGrass += 2 * dist[i];
            }
            else if (type[i] == 'L') {
                // Have to travel this no matter what by flying
                time += 1 * dist[i];
                if (dist[i] < stamina) {
                    stamina -= dist[i]; // flying
                }
                else {
                    // We tread in place for the extra dist needed to get
                    // enough stamina
                    if (seenWater) {
                        time += 3 * (dist[i] - stamina);
                    }
                    else {
                        time += 5 * (dist[i] - stamina);
                    }
                    stamina = 0;
                }
            }
            // We can never use more grass than the stamina / 2,
            // since that would lose 2 * that amount before getting to lava
            scaledExtraGrass = Math.min(scaledExtraGrass, stamina);
        }

        time -= (5 - 1) * scaledExtraGrass / 2;
//        time += 1 * extraGrass;

        // In case not enough grass to use the last stamina bit
        time -= (3 - 1) * (stamina - scaledExtraGrass) / 2;
//        time += 1 * (stamina / 2 - extraGrass);
        out.println(time);
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