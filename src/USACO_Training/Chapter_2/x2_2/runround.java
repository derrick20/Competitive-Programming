/*
ID: d3rrickl
LANG: JAVA
PROG: runround
 */
// Ok, I was being rly stupid and  i left in an infinite loop that was taking so long i got it right naively
import java.io.*;
import java.util.*;

public class runround {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader br = new BufferedReader(new FileReader("runround.in")); //new InputStreamReader(System.in));
//        PrintWriter out = new PrintWriter(new FileWriter("runround.out"));
        String M = br.readLine();
        Integer[] arr = new Integer[M.length()];
        int i = 0;
        for (char c : M.toCharArray()) {
            arr[i++] = Character.getNumericValue(c);
        }
        int N = Integer.parseInt(M)+1; // can't be that number
        while (!isValid(N)) {
            N++;
        }
        System.out.println(N); //findNextSolution(arr));
//        out.close();
    }

    public static boolean isValid(int N) {
        String str = N+"";
        if (str.contains("0"))
            return false;
        int len = str.length();
        Integer[] arr = new Integer[len];

        int i = 0;
        for (char c : str.toCharArray()) {
            arr[i++] = Character.getNumericValue(c);
        }

        if (new HashSet<Integer>(Arrays.asList(arr)).size() < len)
            return false;
        int[] visited = new int[len];
        int pos = 0;
        for (i = 0; i < len; i++) {
            visited[pos] = 1;
            pos = (pos + arr[pos]) % len;
            if (i == len-1) {
                return pos == 0; // must return to original
            }
            // in other cases, we DON'T want to repeat
            if (visited[pos] == 1) {
                return false;
            }
        }
        return false;
    }

    /* Wayyy overcomplicated it lmao
    public static int findNextSolution(Integer[] arr) {
        int len = arr.length;
        int pos = 0;
        int prevPos = 0;
        int[] visited = new int[len];
        HashSet<Integer> used = new HashSet<Integer>(Arrays.asList(arr));
        // do the process until you've done each digit
        for (int i = 0; i < len; i++) {
            visited[pos] = 1; // move on from that, so it's visiterd
            prevPos = pos;
            pos = (prevPos + arr[prevPos]) % len; // jump up based on arr value
            if (i == len-1) {
                arr[prevPos] = (len - prevPos) % len; // it must be that, when added to the current pos,
                // it will go back to 0
                break;
            }
            if (visited[pos] == 1) {
                int original =  arr[prevPos];
                // either looped to early, duplicate number, or not bigger than original
                while (visited[pos] == 1 || (new HashSet<Integer>(Arrays.asList(arr))).size() != len || arr[prevPos] < original) {
                    arr[prevPos]++;
                    pos = (prevPos + arr[prevPos]) % len; // need mods to wrap around
                }
                if (visited[pos] == 1 || (new HashSet<Integer>(Arrays.asList(arr))).size() != len || arr[prevPos] < original) {
                    i--;
                    arr[prevPos] = original;
                    pos = prevPos;
                    prevPos = (prevPos - arr[pos] + len) % len;
                }
            }
        }
        int ret = 0;
        for (int i = 0; i < len; i++) {
            //System.out.println(arr[i]);
            ret += arr[i];
            if (i != len - 1)
                ret *= 10;
        }
        return ret;
    } // */

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