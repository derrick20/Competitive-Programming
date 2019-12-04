/*
 * @author derrick20
 * STUPID mistake: Although the arrivals will be processed correctly (we
 * are removing the earliest thing and latest thing, as assumed by the
 * array deque structure), we do not correctly open up new rooms during
 * the departure of people. The arrival processing relied on the assumption
 * that the deque would hold the invariant of the leftmost open room
 * being at the left, and the same for the right. In order to maintain
 * this invariant when opening up new rooms, we need to ensure that the
 * room numbers that are open are in increasing order. Thus, the solution bag
 * structure just needs to be sorted, and allow for removal and additions in quick
 * time. This exactly fits the description of a tree set, NOT A DEQUE
 * (We don't have duplicates, so this works fine)
 */
import java.io.*;
import java.util.*;

public class Hotelier {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//1100000100
        int N = sc.nextInt();
        int[] arr = new int[10];
        String in = sc.next();
        TreeSet<Integer> openRooms = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            openRooms.add(i);
        }
        for (char c : in.toCharArray()) {
            if (c == 'L') {
                // Room is now taken
                openRooms.pollFirst();
            }
            else if (c == 'R') {
                openRooms.pollLast();
            }
            else {
                // Add a new room to be available
                int pos = Character.getNumericValue(c);
                openRooms.add(pos); // It will automatically get added
                // in the correct location so as to maintain the sorted invariant
            }
        }
        Arrays.fill(arr, 1);
        for (int room : openRooms) {
            arr[room] = 0;
        }
        for (int i = 0; i < arr.length; i++) {
            out.print(arr[i]);
        }
        out.println();

        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
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

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}