/*
 * @author derrick20
 * The ABSOLUTE MOST SHAFT PROBLEM EVER
 * HAD TO SUBSTITUTE BINARY SEARCH FOR FLOORKEY
 */

import java.io.*;
import java.util.*;

public class dot {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        for (int c = 0; c < C; c++) {
            long S = sc.nextLong();
            int N = sc.nextInt();
//            arr = new ArrayList<>();
            TreeMap<Long, Integer> map = new TreeMap<>();
            long max = 0;
            for (int i = 0; i < N; i++) {
                long next = sc.nextLong();
                if (!map.containsKey(next)) {
                    map.put(next, 1);
                }
                else {
                    map.put(next, map.get(next) + 1);
                }
                max = Math.max(max, next);
            }
            int iter = 0;
            boolean failed = false;
            while (S <= max && map.size() > 0) {
//                int eatable = binarySearch(0, arr.size() - 1, S);
//                int eatable = bruteSearch(S); ///binarySearch(0, arr.size() - 1, S);
//                if (eatable == -1 || map.get(eatable) >= S) {
//                    failed = true;
//                    break;
//                }
                if (map.floorKey(S - 1) == null) {
                    failed = true;
                    break;
                }
                else {
                    long toEat = map.floorKey(S - 1); // get the biggest thing smlller than us
                    S += toEat;
                    map.put(toEat, map.get(toEat) - 1);
                    if (map.get(toEat) == 0) {
                        map.remove(toEat);
                    }
                    iter++;
                }

            }
            if (map.size() == 0 || failed) {
                out.println(-1);
            }
            else {
                out.println(iter);
            }
        }
        out.close();
    }
    static ArrayList<Long> arr;

    static int bruteSearch(long curr) {
        int pos = arr.size() - 1;
        while (pos >= 0) {
            if (curr > arr.get(pos)) {
                return pos;
            }
            pos--;
        }
        return -1;
    }

    static int binarySearch(int lo, int hi, long curr) {
        int ans = 0;
        while (lo <= hi) {
            // While the bounds haven't passed each other, keep going
            int mid = (hi - lo) / 2 + lo;
            if (arr.get(mid) < curr)  {
                // We know for CERTAIN, that this answer works
                // However, we want to try increasing more
                ans = mid;
                lo = mid + 1;
            }
            else {
                hi = mid - 1;
            }
        }
        return ans;
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
