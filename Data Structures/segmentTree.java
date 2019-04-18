import java.io.*;
import java.util.*;
class SegmentTree {
    static int[] tree;
    static int[] arr;
    static int length;
/*
3 3
1 3 5
q 0 2
u 1 2
q 0 2
 */
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] nums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        SegmentTree segtree = new SegmentTree(nums);
        System.out.println(segtree);
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            if (st.nextToken().equals("q")) {
                int lo = Integer.parseInt(st.nextToken());
                int hi = Integer.parseInt(st.nextToken());
                System.out.println(lo + " " + hi);
                System.out.println(segtree.minRange(lo, hi));
            } else {
                int pos = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                segtree.update(1, 0, length-1, pos, value);
            }
        }
    }

    public SegmentTree(int[] nums) {
        int k = (int) (Math.log((double) nums.length)/(Math.log(2.0))) + 1;
        arr = new int[(int) (Math.pow(2.0, k))];
        for (int i = 0 ; i < nums.length; i++)
            arr[i] = nums[i];

        length = arr.length;
        tree = new int[2*length];

        buildTree(1,0, length-1); // This is top-down
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < tree.length; i++) {
            str += i + " " + tree[i] + "\n";
        }
        return str;
    }

    // Going top down
    public void update(int node, int l, int r, int pos, int value) {
        if (l > pos || r < pos) {
        }
        else if (l == r) {
            tree[node] = value;
        }
        else {
            int mid = (l + r) / 2;
            update(2 * node, l, mid, pos, value);
            update(2 * node + 1, mid + 1, r, pos, value);
            tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
        }
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
            tree[node] = merge(tree[2*node], tree[2*node + 1]);
        }
    }


    public int minRange(int i, int j) {
        return query(1, 0, length - 1, i, j); //start off with the biggest segment
    }

    private int query(int index, int lo, int hi, int i, int j) {
        if (lo > j || hi < i) {
            return 0;
        }
        // Segment is contained
        if (i <= lo && hi <= j) {
            return tree[index];
        }

        int mid = (lo + hi) / 2;
        int right = query(2 * index + 1, mid + 1, hi, i, j);
        int left = query(2 * index, lo, mid, i, j);
        int ret = merge(left, right);
        return ret;
    }

    private int merge(int left, int right) {
        return left + right; //Math.min(left, right);
    }
}