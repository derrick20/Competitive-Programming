import java.io.*;
import java.util.*;

/*
3 3
1 3 5
q 0 2
u 1 2
q 0 2
 */
public class lazyPropagation {
    static int inf = (int) 1e9;
    public static void main(String args[]) throws Exception {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] nums = {1, 3, 5, 7, 9, 11};
        SegmentTree segtree = new SegmentTree(nums, "S");
        System.out.println(segtree.queryRange(1, 3));
//        System.out.println(segtree);
        segtree.updateRange(1, 3, 10);
        System.out.println(segtree.queryRange(1, 3));
//        System.out.println(segtree);

    }

    static class SegmentTree {
        int[] tree;
        int[] arr;
        int[] lazy;
        // Min type or sum type
        String MIN = "M";
        String SUM = "S";
        String type;
        int length;

        public SegmentTree(int[] nums, String type) {
            int k = (int) (Math.log((double) nums.length) / (Math.log(2.0))) + 1;
            arr = new int[(int) (Math.pow(2.0, k))];
            for (int i = 0; i < nums.length; i++) {
                arr[i] = nums[i];
            }
            length = arr.length;
            tree = new int[2 * length];
            lazy = new int[2 * length];
            this.type = type;
            buildTree(1, 0, length - 1); // This is top-down
        }


        public String toString() {
            String str = "";
            for (int i = 0; i < tree.length; i++) {
                str += i + " " + tree[i] + "\n";
            }
            return str;
        }

        public void buildTree(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            // Kick this recursion off with the top node (1) which covers everything. Preorder formation
            if (l == r) {
                tree[node] = arr[l];
            } else {
                int mid = (l + r) / 2;
                buildTree(leftChild, l, mid); // Left node
                buildTree(rightChild, mid + 1, r); // Right node
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        public void updateRange(int start, int end, int value) {
            updateRangeHelper(1, 0, length - 1, start, end, value);
        }


        private void updateRangeHelper(int node, int l, int r, int start, int end, int value) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // need to update things below it since it hasn't been fixed before (lazy)
            // Need to push (propagate this lazy value downward)
            if (lazy[node] != 0) {
                // this is how many nodes over which that lazy value would've been propagated
//                tree[node].sum += (r - l + 1) * lazy[node];
//                tree[node].min += lazy[node]; // However, the min will only change by that amount

                if (l != r) {
                    // that is, it is not a leaf
                    tree[leftChild] += lazy[node];
//                    tree[leftChild].min += lazy[node];
//
                    tree[rightChild] += lazy[node];
//                    tree[rightChild].min += lazy[node];
                }
                lazy[node] = 0; // We've pushed it down, so remove our laziness
            }

            // disjoint cases or nonsensical cases
            if (l > r || l > end || r < start) {
                return;
            }

            // full contained within range
            if (l >= start && r <= end) {
                // update sum and min
                if (type.equals(SUM))
                    tree[node] += (r - l + 1) * value;
                else
                    tree[node] += value;

                // not a leaf, then we can give its children lazy values
                // to update later
                if (l != r) {
                    lazy[leftChild] += value;
                    lazy[rightChild] += value;
                }
            } else {
                // it is partially contained
                int mid = (l + r) / 2;
                // update left and right children, then combine it within
                // the parent
                updateRangeHelper(leftChild, l, mid, start, end, value);
                updateRangeHelper(rightChild, mid + 1, r, start, end, value);
                tree[node] = merge(lazy[leftChild] + tree[leftChild], lazy[rightChild] +  tree[rightChild]);
            }
        }

        public void update(int pos, int value) {
            updateHelper(1, 0, length - 1, pos, value);
        }

        // Going top down
        // always call it with node = 1, l = 0, r = length - 1 (and then the pos and value desired
        private void updateHelper(int node, int l, int r, int pos, int value) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (l > pos || r < pos) {
            } else if (l == r) {
                tree[node] = value;
            } else {
                int mid = (l + r) / 2;
                updateHelper(leftChild, l, mid, pos, value);
                updateHelper(rightChild, mid + 1, r, pos, value);
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        public int queryRange(int i, int j) {
            return queryRangeHelper(1, 0, length - 1, i, j); //start off with the biggest segment
        }

        private int queryRangeHelper(int node, int l, int r, int start, int end) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // Completely outside of our segment's range. Right edge left of left edge, left edge right of right edge
            if (l > end || r < start) {
                if (type.equals(SUM))
                    return 0;
                else
                    return inf;
                // TODO MIGHT CHANGE FOR MIN VS SUM
            }

            if (lazy[node] != 0) {
                // we need to be sure to update things
                if (type.equals(SUM))
                    tree[node] += (r - l + 1) * lazy[node];
                else // type is MIN
                    tree[node] += lazy[node];

                if (l != r) {
                    lazy[leftChild] += lazy[node];
                    lazy[rightChild] += lazy[node];
                }
                lazy[node] = 0;
            }

            // Segment is contained
            if (start <= l && r <= end) {
                return tree[node];
            }

            int mid = (l + r) / 2;
            int right = queryRangeHelper(rightChild, mid + 1, r, start, end);
            int left = queryRangeHelper(leftChild, l, mid, start, end);
            int ret = merge(left, right);
            return ret;
        }

        private int merge(int left, int right) {
            if (type.equals(SUM))
                return left + right; //Math.min(left, right);
            else
                return Math.min(left, right);
        }
    }
}
