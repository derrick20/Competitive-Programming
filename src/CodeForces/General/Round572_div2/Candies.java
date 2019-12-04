import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 Candies
 * @derrick20
 */

public class Candies {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        SegmentTree segTree = new SegmentTree(arr);
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int lo = sc.nextInt()-1;
            int hi = sc.nextInt()-1;
            int[] ans = segTree.getAns(lo, hi);
            out.println(ans[1]);
        }

        out.close();
    }


    static class SegmentTree {
        int[] tree;
        int[] treeAns;
        int[] arr;
        int length;

        public SegmentTree(int[] nums) {
            int k = (int) (Math.log((double) nums.length)/(Math.log(2.0))) + 1;
            arr = new int[(int) (Math.pow(2.0, k))];
            for (int i = 0 ; i < nums.length; i++)
                arr[i] = nums[i];

            length = arr.length;
            tree = new int[2*length];
            treeAns = new int[2*length];

            buildTree(1,0, length-1); // This is top-down
        }

        public void buildTree(int node, int l, int r) {
            // Kick this recursion off with the top node (1) which covers everything. Preorder formation
            if (l == r) {
                tree[node] = arr[l];
            }
            else {
                int mid = (l+r) / 2;
                buildTree(2*node, l, mid); // Left node
                buildTree(2*node + 1, mid + 1, r); // Right node
                int[] parts = merge(tree[2*node], tree[2*node + 1], treeAns[2*node], treeAns[2*node+1]);
                tree[node] = parts[0];
                treeAns[node] = parts[1];
            }
        }


        public int[] getAns(int i, int j) {
            return query(1, 0, length - 1, i, j); //start off with the biggest segment
        }

        private int[] query(int index, int lo, int hi, int i, int j) {
            // Completely outside of our segment's range. Right edge left of left edge, left edge right of right edge
            if (lo > j || hi < i) {
                return new int[] {0, 0};
            }
            // Segment is contained
            if (i <= lo && hi <= j) {
                return new int[] {tree[index], treeAns[index]};
            }

            int mid = (lo + hi) / 2;
            int[] right = query(2 * index + 1, mid + 1, hi, i, j);
            int[] left = query(2 * index, lo, mid, i, j);
            // Combine the counts of all three, and the merging step
            return merge(left[0], right[0], left[1], right[1]);
        }

        private int mergeValues(int left, int right) {
            return left + right;
        }

        private int[] merge(int left, int right, int left2, int right2) {
            int ct = left2+right2;
            if (left + right >= 10) {
                ct++;
            }
            return new int[] {(left + right) % 10, ct}; //Math.min(left, right);
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

