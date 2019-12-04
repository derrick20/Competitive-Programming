/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class travel {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            String str = sc.next();
            ArrayList<Pair> arr = new ArrayList<>();
            int index = 0;
            for (char c : str.toCharArray()) {
                arr.add(new Pair(Character.getNumericValue(c), index++));
            }
            Collections.sort(arr, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if (o1.val - o2.val != 0) {
                        // higher value first
                        return -(o1.val - o2.val);
                    }
                    else {
                        // lower index first
                        return o1.index - o2.index;
                    }
                }
            });
            ArrayList<Pair> ans = new ArrayList<>();
            while (ans.size() < K) {
                Pair biggest = arr.remove(arr.size() - 1);
                ans.add(biggest);
            }
            Collections.sort(ans, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    return o1.index - o2.index;
                }
            });

            for (Pair p : ans) {
                out.print(p.val);
            }
            out.println();
        }
        out.close();
    }

    static class Pair {
        int val;
        int index;

        public Pair(int val, int index) {
            this.val = val;
            this.index = index;
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
