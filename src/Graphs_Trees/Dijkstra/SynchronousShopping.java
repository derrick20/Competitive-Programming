/**
 * @author derrick20
 * Nice Dijkstra with BitSet!! Used Big O to solve!
 */
import java.io.*;
import java.util.*;
import java.util.zip.Adler32;

public class SynchronousShopping {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[] shops = new int[N];
        // Store the bitsets of the fish available
        for (int i = 0; i < N; i++) {
            int T = sc.nextInt();
            int fish = 0;
            for (int j = 0; j < T; j++) {
                int type = sc.nextInt() - 1; // 0-index the fish types
                fish |= (1 << type);
            }
            shops[i] = fish;
        }
        ArrayList<Edge>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            adjList[u].add(new Edge(v, wt));
            adjList[v].add(new Edge(u, wt));
        }

        // Be sure to initialize all of the distMap with infinite distances
        HashMap<Integer, int[]> distMap = new HashMap<>();
        for (int bitset = 0; bitset < (1 << K); bitset++) {
            int[] arr = new int[N];
            Arrays.fill(arr, oo);
            distMap.put(bitset, arr);
        }

        // Dijkstra's with modified nodes (that have a bitset of what fish
        // have been captured)
        PriorityQueue<Shop> pq = new PriorityQueue<>();

        // Base case: Source node
        distMap.get(shops[0])[0] = 0;
        pq.add(new Shop(shops[0], 0, 0));
        while (pq.size() > 0) {
            Shop front = pq.poll();
            // Front's distance was updated earlier. We go through its
            // neighbors and update their distances
            for (Edge e : adjList[front.pos]) {
                // Added all new fish
                int updatedFish = front.fish | shops[e.v];

                // Try updating the distances
                int altDist = front.dist + e.wt;
                if (altDist < distMap.get(updatedFish)[e.v]) {
                    // If we can get to this fish amount at this node
                    // more easily, then do so!
                    distMap.get(updatedFish)[e.v] = altDist;
                    // This is worth exploring more!
                    pq.add(new Shop(updatedFish, e.v, altDist));
                }
            }
        }

        // Go through half of them, and see what has the best cost
        // Since we have two shoppers, we want to minimally get each
        // combination of fish so that it encompasses all of them

        // It might be true that combining 1100 and 0011 is actually
        // more expensive than 1110 and 0011, so we need to try all
        // 1000^2 pairs, which isn't gonna be a problem
        int ans = oo;
        for (int bitset = 0; bitset < (1 << K);  bitset++) {
            for (int bitset2 = 0; bitset2 < (1 << K); bitset2++) {
                // See if the combination gives us the full amount (111...)
                if ((bitset | bitset2) == (1 << K) - 1) {
                    // We have to get both shoppers to the end.
                    // Whichever takes more distance is the limiting factor
                    int limitingDist = Math.max(distMap.get(bitset)[N - 1], distMap.get(bitset2)[N - 1]);
                    ans = Math.min(ans, limitingDist);
                }
            }
        }
        out.println(ans);
        out.close();
    }

    static int N;
    static int oo = (int) 1e9;

    static class Shop implements Comparable<Shop> {
        int fish; // bitset
        int pos, dist;

        public Shop(int fishAdded, int position, int distance) {
            fish = fishAdded;
            pos = position;
            dist = distance;
        }

        public String toString() {
            return "Fish: " + Integer.toBinaryString(fish) + " " + pos + " " + dist;
        }

        public int compareTo(Shop s2) {
            return dist - s2.dist;
        }
    }

    static class Edge {
        int v, wt;
        public Edge(int adj, int weight) {
            v = adj; wt = weight;
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