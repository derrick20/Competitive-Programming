/**
 ID: d3rrickl
 LANG: JAVA
 PROG: maze1
 */
import java.io.*;
import java.util.*;

public class maze1 {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("maze1", false);
        // STUPID INPUT READER BUHHHHHH
        BufferedReader br = new BufferedReader(new FileReader("maze1.in"));//System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());// sc.nextInt();
        int H = Integer.parseInt(st.nextToken());// sc.nextInt();
        ArrayList<Node>[][] adjList = new ArrayList[2 * H + 1][2 * W + 1];
        char[][] grid = new char[2 * H + 1][2 * W + 1];


        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int row = 0; row < 2 * H + 1; row++) {
            grid[row] = br.readLine().toCharArray();// sc.nextLine().toCharArray();
        }

        int oo = (int) 1e9;

        int[][] dist = new int[2 * H + 1][2 * W + 1];
        for (int row = 0; row < 2 * H + 1; row++) {
            Arrays.fill(dist[row], oo);
        }

        for (int row = 1; row < 2 * H + 1; row += 2) {
            for (int col = 1; col < 2 * W + 1; col += 2) {
                adjList[row][col] = new ArrayList<>();
                // Let the distances be 1, since technically we have
                // to move one more from here to get out.
                if (grid[row - 1][col] != '-') {
                    if (row == 1) {
                        // 1 up is the exit
                        pq.add(new Node(row, col, 1));
                        dist[row][col] = 1;
                    }
                    else {
                        adjList[row][col].add(new Node(row - 2, col, oo));
                    }
                }
                if (grid[row + 1][col] != '-') {
                    if (row == 2 * H - 1) {
                        // 1 down is the exit
                        pq.add(new Node(row, col, 1));
                        dist[row][col] = 1;
                    }
                    else {
                        adjList[row][col].add(new Node(row + 2, col, oo));
                    }
                }
                if (grid[row][col - 1] != '|') {
                    if (col == 1) {
                        // 1 over to the left is the exit
                        pq.add(new Node(row, col, 1));
                        dist[row][col] = 1;
                    }
                    else {
                        adjList[row][col].add(new Node(row, col - 2, oo));
                    }
                }
                if (grid[row][col + 1] != '|') {
                    if (col == 2 * W - 1) {
                        // 1 over to the right is the exit
                        pq.add(new Node(row, col, 1));
                        dist[row][col] = 1;
                    }
                    else {
                        adjList[row][col].add(new Node(row, col + 2, oo));
                    }
                }
            }
        }


        // Multi-source dijkstra's
        // Store minimum distance from node in r, c to on of the 2 exits.
        while (pq.size() > 0) {
            Node front = pq.poll();
            int row = front.r;
            int col = front.c;
            for (Node adj : adjList[row][col]) {
                int altDist = dist[row][col] + 1;
                if (altDist < dist[adj.r][adj.c]) {
                    dist[adj.r][adj.c] = altDist;
                    pq.add(new Node(adj.r, adj.c, altDist));
                }
            }
        }
        int worst = 0;
        for (int row = 1; row < 2 * H + 1; row += 2) {
            for (int col = 1; col < 2 * W + 1; col += 2) {
                if (dist[row][col] > worst && dist[row][col] != oo) {
                    worst = dist[row][col];
                }
            }
        }
        out.println(worst);
        out.close();
    }

    static class Node implements Comparable<Node> {
        int r, c;
        int dist;
        public Node(int row, int col, int distance) {
            r = row; c = col; dist = distance;
        }
        public int compareTo(Node n2) {
            return dist - n2.dist;
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