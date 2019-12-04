/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;
/*
1
4 2 3
1 2 4 2
1 4 4 1
2 1 4

1
11 4 3
1 3 5 2
1 7 7 3
2 2 7
 */
public class pikango {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            int N = sc.nextInt();
            int K  = sc.nextInt();
            int Q = sc.nextInt();
            int[] arr = new int[N];
            Arrays.fill(arr, 1);
            SegmentTree segTree = new SegmentTree(arr);
            for (int q = 0; q < Q; q++) {
                int type = sc.nextInt();
                int left = sc.nextInt() - 1;
                int right = sc.nextInt() - 1;
//                System.out.println(segTree.display(1, 0));

                if (type == 1) {
                    int value = sc.nextInt();
                    segTree.updateRange(left, right, value);
//                    for (int x = left; x <= right; x++) {
//                        segTree.update(x, value);
//                        for (int k = 0; k < N; k++) {
//                            System.out.print(segTree.queryRange(k, k) + " ");
//                        }
//                        System.out.println();
//                    }
                }
                else {
                    int ans = segTree.queryRange(left, right);
                    out.println(ans);
                }
            }
        }
        out.close();
    }

    static class SegmentTree {
        int[] treeLeft;
        int[] treeRight;
        int[] treeGroups;
        int[] arr;
        int[] lazy;
        int length;

        public SegmentTree(int[] nums) {
            int k = (int) (Math.log((double) nums.length) / (Math.log(2.0))) + 1;
            arr = new int[(int) (Math.pow(2.0, k))];
            for (int i = 0; i < nums.length; i++) {
                arr[i] = nums[i];
            }
//            Arrays.fill(arr, 1);
            length = arr.length;
            treeLeft = new int[2 * length];
            treeRight = new int[2 * length];
            treeGroups = new int[2 * length];
//            for (int i = 0; i < 2 * length; i++) {
//                treeLeft[i] = 1;
//                treeRight[i] = 1;
//                treeGroups[i] = 1;
//            }
            lazy = new int[2 * length];
            buildTree(1, 0, length - 1); // This is top-down
        }

        public String display(int node, int level) {
            String toRet = "";
            if (node >= 2 * length)
                return "";
            toRet += display(2 * node + 1, level + 1); //recurse right
            for(int k = 0; k < level; k++)
                toRet += "\t\t\t\t";
            toRet += node + ": " + "(" + treeLeft[node] + ", " + treeGroups[node] + ", " + treeRight[node] + "), " + lazy[node] + "\n";
            toRet += display(2 * node, level + 1); //recurse left
            return toRet;
        }

        public void buildTree(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            // Kick this recursion off with the top node (1) which covers everything. Preorder formation
            if (l == r) {
                // Special case for painting, since we ignore pieces that
                // aren't mentioned
                if (arr[l] != 0) {
                    treeLeft[node] = arr[l];
                    treeRight[node] = arr[r];
                    treeGroups[node] = 1;
                }
            } else {
                int mid = (l + r) / 2;
                buildTree(leftChild, l, mid); // Left node
                buildTree(rightChild, mid + 1, r); // Right node
                treeLeft[node] = treeLeft[leftChild];
                treeRight[node] = treeRight[rightChild];
                treeGroups[node] = merge(treeGroups[leftChild], treeGroups[rightChild], leftChild, rightChild);
            }
        }

        public void updateRange(int start, int end, int value) {
            updateRangeHelper(1, 0, length - 1, start, end, value);
        }

        private void updateRangeHelper(int node, int l, int r, int start, int end, int value) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // disjoint cases or nonsensical cases
            if (l > r || l > end || r < start) {
                // don't do anything
                return;
            }
            else if (start <= l && r <= end) {
                // completely contained
                lazy[node] = value;
                pushDown(node, l, r);
                return;
            }
            else {
                // it is partially contained, so need to update things below it since it hasn't been fixed before (lazy)
                if (lazy[node] != 0) {
                    pushDown(node, l, r);
                }
                int mid = (l + r) / 2;
                // update left and right children, then combine it within
                // the parent
                updateRangeHelper(leftChild, l, mid, start, end, value);
                updateRangeHelper(rightChild, mid + 1, r, start, end, value);
                pullUp(node, l, r);
                // we might have update
                // d one side but not the other, so
                // in the case of min/max, our parent's value might be wrong
            }
        }

        public int queryRange(int start, int end) {
            return queryRangeHelper(1, 0, length - 1, start, end); //start off with the biggest segment
        }
// TODO OH SHIT MY LAZY VALUES AREN"T BEING UPDATED, JUST THE GROUP NUMBER FUUUUUU
        // Given some lazy value that has been added, you have to
        // push it into the lower children
        private void pushDown(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            treeGroups[node] = 1; // all one COLOR
            treeLeft[node] = lazy[node];
            treeRight[node] = lazy[node];
            // if it isn't a leaf node, we can push it into the lower levels
            if (l != r) {
                lazy[leftChild] = lazy[node];
                lazy[rightChild] = lazy[node];
            }
            lazy[node] = 0; // don't double count how many times the lazy values can be added to it
        }

        // At a given node, look at each of its children and look at their values
        // (with the lazy information) and update our current value
        private void pullUp(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            int mid = (l + r) / 2;
            treeGroups[node] = merge(treeGroups[leftChild], treeGroups[rightChild], leftChild, rightChild);
            treeLeft[node] = treeLeft[leftChild];
            treeRight[node] = treeRight[rightChild];
//            if (type.equals(SUM)) {
//                tree[node] = merge((mid - l + 1) * lazy[leftChild] + tree[leftChild], (r - (mid + 1) + 1) * lazy[rightChild] + tree[rightChild]);
//            }
//            else {
//                tree[node] = merge(lazy[leftChild] + tree[leftChild], lazy[rightChild] + tree[rightChild]);
//            }
        }

        private int queryRangeHelper(int node, int l, int r, int start, int end) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            // Completely outside of our segment's range. Right edge left of left edge, left edge right of right edge
            if (l > end || r < start) {
                // If outside the range, then return something that'll never contribute to the query answer
                return 0;
            }
            else if (start <= l && r <= end) {
                // Segment is contained
                // but, we must use lazy information still!!
                if (lazy[node] != 0) {
                    return 1;
                }
                else {
                    return treeGroups[node];
                }
            }
            else {
                // it is partially covered, so push lazy values downward
                pushDown(node, l, r); /// TODO NO LAZY LOL
                int mid = (l + r) / 2;
                int rightAmt = queryRangeHelper(rightChild, mid + 1, r, start, end);
                int leftAmt = queryRangeHelper(leftChild, l, mid, start, end);
                int ret = merge(leftAmt, rightAmt, leftChild, rightChild);
                pullUp(node, l, r);
                // Notice importantly that, after you'ved pushed to a portion of
                // the left child and right children z
                // pullUp(node, l, r); // NOTE THIS IS NOT NECESSARY
                // Since it is just a query, nothing below will change,
                // so there is no reason for the current node's value to be updated
                return ret;
            }
        }

        private int merge(int leftAmt, int rightAmt, int leftChild, int rightChild) {
            if (treeRight[rightChild] == 0 && treeLeft[leftChild] == 0) {
                return 0;
            }
            else {
                int groups = leftAmt + rightAmt;
                if (treeRight[leftChild] == treeLeft[rightChild]) {
                    groups--;
                }
                return groups;
            }
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
