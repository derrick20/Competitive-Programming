/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class pancakes {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }
            ArrayList<Stack<Integer>> stacks = new ArrayList<>();
            int ct = 0;
            for (int i = 0; i < N; i++) {
                if (arr[i] == 0) {
                    if (stacks.size() > 0)
                        stacks.remove(0);
                    else {
                        ct++;
                    }
                }
                else {
                    boolean pushed = false;
                    for (int j = 0; j < stacks.size(); j++) {
                        Stack<Integer> stack = stacks.get(j);
                        if (!pushed && stack.peek() >= arr[i]) {
                            stack.push(arr[i]);
                            pushed = true;
                        }
                    }
                    if (!pushed) {
                        Stack<Integer> stack = new Stack<>();
                        stack.add(arr[i]);
                        stacks.add(stack);
                        ct++;
                    }
                }
                Collections.sort(stacks, new Comparator<Stack<Integer>>() {
                    @Override
                    public int compare(Stack<Integer> o1, Stack<Integer> o2) {
                        return o1.peek() - o2.peek();
                    }
                });
            }

            out.println(ct);
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
