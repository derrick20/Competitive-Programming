import java.io.*;
import java.util.*;
/*
 * HackerEarth Gudi Trapped in the Room (Easy)
 * ok a few HUGE mistakes: 1-indexed string, USE THE TEST CASE and UNDERSTAND EXACTLY
 * Second, clockwise means you steal length-shift from the end and put it at the front. Not just shift from the end!,
 * It's because you are taking it from the end, and substring(length-shift) gives you that substring
 * Third, learned that NON recursive is way better...
 * Fourth, keep in mind that the clockwise rotation needn't be less than the length of the string!
 * It was shifting 6 and length = 2, so obviously yours broke. Better to implement EXACTLY
 * what they mean, but here we just modded it
 * */

public class Spells {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        while (N-->0) {
            String curr = sc.next();
            A = sc.nextInt();
            H = sc.nextInt() % curr.length();
            visited = new TreeSet<>();
            dfs(curr);
            System.out.println(visited.pollFirst());
        }
    }
    static int H, A;
    static TreeSet<String> visited;

    public static void dfs(String start) {
        Stack<String> stack = new Stack<>();
        stack.add(start);
        visited.add(start);
        while (!stack.isEmpty()) {
            String curr = stack.pop();
            String x = curr.substring(curr.length() - H) + curr.substring(0, curr.length() - H); // rotate it arround
            String y = "";
            for (int i = 0; i < curr.length(); i++) {
                char c = curr.charAt(i);
                int val = Integer.parseInt(c + "");
                if ((i+1) % 2 == 0)
                    val = (val + A) % 10;
                y += val;
            }
            if (!visited.contains(x)) {
                visited.add(x);
                stack.add(x);
            }
            if (!visited.contains(y)) {
                visited.add(y);
                stack.add(y);
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