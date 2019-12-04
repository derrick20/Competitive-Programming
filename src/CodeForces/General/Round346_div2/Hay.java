import java.io.*;
import java.util.*;
/*
 * CodeForces Round 346 Div 2 Polycarp and Hay
 * Ok BRUH THIS WAS SO STUPID
 * THE ONLY THING HOLDING ME BACK WAS PRINTWRITER GAHHHH !$I!_$_!JPANV:ALXNZ<
 * However, priority queue also slowed it down quite a bit. Log(N) for removal, AND NLog(N) for construction i think
 * Carrying around big integers is bad. Rather, carry booleans
 * A crucial error early on was in the UnionFind structure, updating things BEFORE unioning, just to ensure it's legal
 * Also, repeatedly adding large integers and dividing longs is bad, so convert it into small countable
 * integers, and use those to keep track of things.
 * @derrick20
 */

public class Hay {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextLong();
        boolean[][] visited = new boolean[N][M];
        UnionFind dsu = new UnionFind(N*M);
        HayBale[] bales = new HayBale[N*M];
        res = new boolean[N][M];
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int hay = sc.nextInt();
                bales[i * M + j] = new HayBale(i, j, hay);
                arr[i][j] = hay;
            }
        }
        Arrays.sort(bales);
        boolean possible = false;
        for (int x = 0; x < M*N; x++) {
            // get the biggest haybale. The intuition is that we can keep getting the biggest one
            // out and seeing if we can set the connected component of it to this value (the lowest
            // of all previous, guaranteed by our processing order. Keep building down until it's possible)
            HayBale b = bales[x];
            visited[b.x][b.y] = true;
            int id = M * b.x + b.y; // use this to uniquely represent each haybale
            // Now, try to see if we can connect it to any components.
            // In fact, it may even serve to bridge between two currently separate components
            for (int i = 0; i < 4; i++) {
                int x2 = b.x + dx[i];
                int y2 = b.y + dy[i];
                if (inBounds(x2, y2) && visited[x2][y2]) {
                    int id2 = M * x2 + y2;
                    dsu.union(id, id2);
                    // System.out.println(id + " " + id2 + " " + dsu.componentSize(id));
                }
            }

            // First of all, it must be that using multiple of this size hay bale can add to the desired
            // number. Don't even think about trying to get enough hay bales if this isn't true!
            if (K % b.h == 0) {
                // I think a tricky case here is that, if you happen to merge two components
                // that have a big size, then you might go over K, so we need to check in case that happens
                // Before, we assumed it was only increasing by increments of 1!
                if ((long) dsu.componentSize(id) >= K / b.h) {
                    // System.out.println(dsu.componentSize(id) + " " + b.h);
                    value = b.h;
                    total = K / b.h;
                    dfs(b.x, b.y);
                    possible = true;
                    break;
                }
            }
        }
        if (possible) {
            out.println("YES");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    out.print((res[i][j] ? value : 0) + " ");
                }
                out.println();
            }
        }
        else {
            out.println("NO");
        }
        out.close();
    }
    static boolean[][] res;
    static int[][] arr;
    // technicallyyyy our x and y's are reversed. x's are up and down, y's are left and right, but it
    // doesn't matter as long as it searches everything consistently internally. Printing will work still
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int value;
    static long total;
    static int N, M;
    static long K;

    static boolean inBounds(int x, int y) {
        return 0 <= x && x <= N-1 && 0 <= y && y <= M-1;
    }

    static void dfs(int x, int y) {
        if (total == 0 || res[x][y]) {
            return;
        }
        total--;
        res[x][y] = true; // mark it, and simultaneously make it so that it is how we want to print it
        for (int i = 0; i < 4; i++) {
            int x2 = x + dx[i];
            int y2 = y + dy[i];
            // Make sure it's inBounds, unmarked, and also, that it has enough hay to be chopped off
            // A slight inefficiency perhaps is that we didn't store which haybales themselves are valid in the DSU
            // but that'd be complicated, so we just research a little bit to find it. Not really that bad i think!
            if (inBounds(x2, y2) && !res[x2][y2] && arr[x2][y2] >= value) {
                dfs(x2, y2);
            }
        }
    }

    static class HayBale implements Comparable<HayBale> {
        int x, y, h;
        public HayBale(int x, int y, int h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        public int compareTo(HayBale hayBale) {
            return hayBale.h - this.h; // we want bigger first in this case
        }
    }

    static class UnionFind {
        // total number of nodes, number of components
        int size, components;
        // the parent of a node
        int[] id;
        // Size of the component
        int[] sz;

        public UnionFind(int total) {
            this.size = components = total;
            id = new int[size];
            sz = new int[size];

            for (int i = 0; i < size; i++) {
                // each node is singular, so it is its own root, and size = 1
                id[i] = i;
                sz[i] = 1;
            }
        }

        // Find the root of a node
        public int find(int node) {
            int root = node;
            // the root of the tree will have an id of itself,
            // so continue climbing up until we reach that point
            while (id[root] != root) {
                root = id[root];
            }

            // path compression: the idea is that, for each
            // of the nodes starting from the node climbing up to the root,
            // we want quick access to the proper parent at the top to save
            // time in the future
            while (node != root) {
                int curr = node;
                node = id[node]; // go up one level, then, after using this information, replace it with
                // the updated info acquired by the find process above
                id[curr] = root;
            }
            return root;
        }

        public void union(int node1, int node2) {
            // Shoot, big error here. Need to update everything up along its parent chain,
            // then we can union them. This ensures no unupdated nodes along the chain
            int root1 = find(node1);
            int root2 = find(node2);
            // They are already in the same component, so break
            if (root1 == root2) {
                return;
            }
            // by convention, merge the smaller into the bigger
            else if (sz[root1] < sz[root2]) {
                id[root1] = root2; // point root1 to root2
                sz[root2] += sz[root1];
                // don't worry about cascading information about
                // the root down the chain. The next time we call
                // find(something in group), it will convert everything in the chain
            }
            else {
                // root1 absorbs root2's component
                id[root2] = root1;
                sz[root1] += sz[root2];
                // interestingly, the size of the smaller component will never be accessed again,
                // since the roots are all fed into the larger component!
            }
            components--;
        }

        // if two nodes have the same, root, then their roots are the same
        public boolean connected(int node1, int node2) {
            return find(node1) == find(node2);
        }

        public int componentSize(int node) {
            return sz[find(node)];
        }

        public int size() {
            return this.size;
        }

        public int components() {
            return this.components;
        }
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
