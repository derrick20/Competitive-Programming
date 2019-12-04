/*
 * @author derrick20
 * Couldn't solve. Issue was that I didn't limit the search by only traversing
 * the size of the DELETE ARRAY. I was going through to 1e18
 */

import java.io.*;
import java.util.*;

public class pilesOfCandies {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int Q = sc.nextInt();
        while (Q-->0) {
            long[] nums = new long[3];
            for (int i = 0; i < 3; i++) {
                nums[i] = sc.nextLong();
            }
            Arrays.sort(nums);
            long diff = nums[1] - nums[0];
            long add = (nums[2] - diff) / 2;
            out.println(nums[1] + add);
        }
        out.close();
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
