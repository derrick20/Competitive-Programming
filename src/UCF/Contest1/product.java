/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class product {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        // TODO LOLOLOLOL THIS WORKSSSS
        int maxSize = (int) 1.2e7;
        int[] nums = new int[(int) maxSize + 1];
        for (int i = 2; i < nums.length; i++) {
            nums[i] = i;
        }
        int[] ct = new int[(int) maxSize + 1];
        HashMap<Integer, TreeSet<Integer>> numsWithF = new HashMap<>();
        for (int i = 2; i < nums.length; i++) {
            // If it has factors it can use to divide out other numbers (it is not a subset of all prior factors)
            if (nums[i] > 1) {
                for (int j = 2 * i; j < nums.length; j += i) {
                    // If it has remaining factors, keep dividing
                    if (nums[j] > 1) {
                        // That number now loses one factor of number i
                        nums[j] /= nums[i];
                        ct[j]++;
                    }
                }
                // Now, that number has lost its factor as well (but we do it
                // at the end because we needed it to process prior numbers)
                nums[i] = 1;
                ct[i]++;
            }
        }
        for (int i = 2; i < nums.length; i++) {
            if (!numsWithF.containsKey(ct[i])) {
                TreeSet<Integer> set = new TreeSet<>();
                set.add(i);
                numsWithF.put(ct[i], set);
                // The first time we achieve a certain prime count,
                // put this into the map
            } else {
                numsWithF.get(ct[i]).add(i);
            }
        }

        int C = sc.nextInt();
//        System.out.println(numsWithF);
        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int F = sc.nextInt();
            TreeSet<Integer> set = numsWithF.get(F);
            out.println(set.ceiling(N));
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
