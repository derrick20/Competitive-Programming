/*
 * @author derrick20
 * This was actually easy. Again, don't overcomplicate and try to force a solution with
 * DP. Using a simple greedy approach, and a bit of logic, we see that out of the two on the
 * left and the two on the right, one pair must be matching at least (or even 4). This can
 * be coded as 4 different cases  (+1 for a double match case), which means we just have left
 * and right pointers that shift over depending on what case was discovered. The one trick also
 * was with size 3 or 2, needed to hardcode the base case correctly.
 */
import java.io.*;
import java.util.*;

public class archaeology {

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s = sc.next();
        char[] arr = s.toCharArray();
        int goal = s.length() / 2;
        StringBuilder leftAns = new StringBuilder();
        StringBuilder rightAns = new StringBuilder();

        int left = 0;
        int right = s.length() - 1;
        while (right - left >= 3 && leftAns.length() + rightAns.length() < goal) {
            char l1 = arr[left];
            char l2 = arr[left + 1];
//            out.println(leftAns.toString() + rightAns.reverse());
//            rightAns.reverse();
            char r1 = arr[right];
            char r2 = arr[right - 1];
            if (l1 == r1) {
                left++;
                right--;
                leftAns.append(l1);
                rightAns.append(r1);

                if (l2 == r2) {
                    left++;
                    right--;
                    leftAns.append(l2);
                    rightAns.append(r2);
                }
            }
            else if (l2 == r2) {
                left += 2;
                right -= 2;
                leftAns.append(l2);
                rightAns.append(r2);
            }
            else if (l1 == r2) {
                left++;
                right -= 2;
                leftAns.append(l1);
                rightAns.append(r2);
            }
            else if (l2 == r1) {
                left += 2;
                right--;
                leftAns.append(l2);
                rightAns.append(r1);
            }
        }
        if (right - left < 3) {
            leftAns.append(arr[left]);
        }

        out.println(leftAns.toString() + rightAns.reverse());
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
