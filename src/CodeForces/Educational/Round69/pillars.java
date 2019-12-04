/*
 * @author derrick20
 * Idea: Simulate it with some optimizations. Start at the biggest one,
 * then try taking the left or right one. The key mistake that kept causing
 * time out was that I wasn't incrementing right (decrementing is wrong)
 * Be more confident in the time complexity calculation you might have determined
 * from your prior analysis. Stick to that as your guide for accuracy and solution strength
 * Don't doubt random things like slight differences. If there's a loop, it should be O(N)
 * as long as you aren't jumping backward somehow!!
 */
import java.io.*;
import java.util.*;

public class pillars {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        int mid = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            if (arr[i] == N) {
                mid = i;
            }
        }
        int left = mid - 1;
        int right = mid + 1;
        int top = N;
        while (top > 1) {
            int prev = top;
            if (left >= 0 && arr[left] == top - 1) {
                top--;
                left--;
            }
            if (right < N && arr[right] == top - 1) {
                top--;
                right++;
            }
            if (top == prev) {
                // no change, so break
                break;
            }
        }
        if (top == 1) {
            out.println("YES");
        }
        else {
            out.println("NO");
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
