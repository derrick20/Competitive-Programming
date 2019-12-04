/*
 * @author derrick20
 */

import java.io.*;
import java.sql.SQLOutput;
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
public class pikangoREDO {

    public static void main(String[] args) throws Exception {
//        File f = new File("TEEHEE.txt");
//        System.out.println(f.getAbsolutePath());
//        Scanner sc = new Scanner(new FileReader("/Users/derrick/IntelliJProjects/src/UCF/Contest3/pikango.in"));
//        PrintWriter out = new PrintWriter(new FileWriter("pikango.out"));
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        while (C-->0) {
            int N = sc.nextInt();
            int K  = sc.nextInt();
            int Q = sc.nextInt();
            SegmentTree segTree = new SegmentTree(N);
            for (int q = 0; q < Q; q++) {
                int type = sc.nextInt();
                int left = sc.nextInt() - 1;
                int right = sc.nextInt() - 1;
//                System.out.println(segTree.display(1, 0));

                if (type == 1) {
                    int value = sc.nextInt();
                    segTree.updateRange(left, right, value);
                }
                else {
                    long ans = segTree.queryRange(left, right);
                    out.println(ans);
                }
            }
        }
        out.close();
    }

    static class SegmentTree {
        // keep track of the lo and hi bounds controlled of a certain node
        int[] lo, hi;
        int[] treeLeft, treeRight, treeGroups;
        int[] lazy;
        int N;

        public SegmentTree(int N) {
            this.N = N;
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // Store the color on the left, right
            // Store number of distinct groups within a range
            treeLeft = new int[4 * N + 1];
            treeRight = new int[4 * N + 1];
            treeGroups = new int[4 * N + 1];
            lazy = new int[4 * N + 1];
            // we had to give up that 0th cell of memory
            // because there's no easy way to go down from it
            buildTree(1, 0, N - 1);
        }

        public void buildTree(int node, int l, int r) {
            if (l == r) {
                treeLeft[node] = 1;
                treeRight[node] = 1;
                treeGroups[node] = 1;
                lo[node] = l;
                hi[node] = r;
            }
            else {
                lo[node] = l;
                hi[node] = r;
                int mid = (l + r) / 2;
                buildTree(2 * node, l, mid);
                buildTree(2 * node + 1, mid + 1, r);
                pullUp(node);
            }
        }

        public String display(int node, int level) {
            String toRet = "";
            if (node >= 2 * N)
                return "";
            toRet += display(2 * node + 1, level + 1); //recurse right
            for(int k = 0; k < level; k++)
                toRet += "\t\t\t\t";
            toRet += node + ": " + "(" + treeLeft[node] + ", " + treeGroups[node] + ", " + treeRight[node] + "), " + lazy[node] + "\n";
            toRet += display(2 * node, level + 1); //recurse left
            return toRet;
        }

        // update a node with its children's new information, as a result
        // of potential changes due to recursion down in each subtree
        // Must use the lazy information
        // ONE CRUCIAL ISSUE IS THAT LEAVES WON'T BE CORRECT
        //
        public void pullUp(int node) {
            int leftLeft = treeLeft[2 * node];
            int rightRight = treeRight[2 * node + 1];

            treeLeft[node] = leftLeft;
            treeRight[node] = rightRight;

            int leftRight = treeRight[2 * node];
            int rightLeft = treeLeft[2 * node + 1];

            int leftGroups = treeGroups[2 * node];
            int rightGroups = treeGroups[2 * node + 1];
            int total = leftGroups + rightGroups;
            if (leftRight == rightLeft) {
                total--;
            }
            treeGroups[node] = total;
        }

        // transfer the lazy information stored at a node down to its children
        public void pushDown(int node) {

            // we gotta do a bunch of value updates (3 for each child)
            int color = lazy[node];
            lazy[2 * node] = color;
            lazy[2 * node + 1] = color;

            treeGroups[2 * node] = 1;
            treeGroups[2 * node + 1] = 1;

            treeLeft[2 * node] = color;
            treeLeft[2 * node + 1] = color;

            treeRight[2 * node] = color;
            treeRight[2 * node + 1] = color;

            lazy[node] = 0;
        }

        // Always begin the updating with the root of the tree
        public void updateRange(int l, int r, int val) {
            updateRangeHelper(1, l, r, val);
        }

        // Update a range [l, r] by val
        // Recursively walk down to our children:
        // Either we are completely covered, disjoint, or partially covered
        // KEY POINT: We don't use a mid to break apart the range,
        // since we need to preserve the information about the range.
        // What we ARE splitting is our node into its children, and
        // we'll individually decide which of those children are useful.
        public void updateRangeHelper(int node, int l, int r, int val) {
            if (r < lo[node] || hi[node] < l) {
                // we are completely disjoint with the goal range
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // Complete covering, let's be lazy
                treeGroups[node] = 1; // only 1 color, so one group
                treeLeft[node] = val;
                treeRight[node] = val;
                lazy[node] = val;
            }
            else {
                // Partial covering, forced to recurse down.
                // We may have lazy values from previous updates, so
                // we must propagate it downward
                if (lazy[node] != 0) {
                    pushDown(node);
                }
                // Recurse on children
                updateRangeHelper(2 * node, l, r, val);
                updateRangeHelper(2 * node + 1, l, r, val);

                // After recursion, we may have pending updates from children
                // affecting this node
                pullUp(node);
            }
        }

        public long queryRange(int l, int r) {
            return queryRangeHelper(1, l, r);
        }

        // Query the value along a range [l, r]
        // Where we are currently at a certain node and will
        // recurse downward - either being disjoint, contained, or partially
        // covered.
        public int queryRangeHelper(int node, int l, int r) {
            if (r < lo[node] || hi[node] < l) {
                // It's disjoint, so return a value that will not affect our
                // ultimate answer
                return -1;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // We must apply any lazy values that had been placed here earlier
                if (lazy[node] != 0) {
                    return 1; // only 1 group
                }
                else {
                    return treeGroups[node];
                }
            }
            else {
                // First, propagate any pending lazy values
                if (lazy[node] != 0) {
                    pushDown(node);
                }

                // Partial covering, so we must recurse
                // and gather information from the depths
                int leftResult = queryRangeHelper(2 * node, l, r);
                int rightResult = queryRangeHelper(2 * node + 1, l, r);

                // Technically, this pullUp can be called IMMEDIATELY after
                // pushing down, since the values aren't actually changing,
                // it's just that we lost our lazy value, (and our value no longer
                // reflects the true rangeQuery value that node represents)
                pullUp(node);

                if (leftResult == -1) {
                    return rightResult;
                }
                if (rightResult == -1) {
                    return leftResult;
                }

                // If both values are in range, we need to check the border and subtract 1 if matching
                int ans = leftResult + rightResult;
                int leftRight = treeRight[2 * node];
                int rightLeft = treeLeft[2 * node + 1];
                if (leftRight == rightLeft) {
                    ans--;
                }
                return ans;
                // The lazy values will have been included already
//                return merge(leftResult, rightResult);
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

        String nextLine() throws IOException {
            return br.readLine();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}