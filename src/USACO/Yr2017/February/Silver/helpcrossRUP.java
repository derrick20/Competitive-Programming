import java.util.*;
import java.io.*;

public class helpcrossRUP {

    public static void main(String[] args) throws Exception {

        // Read input.
        Scanner stdin = new Scanner(System.in);//new File("helpcross.in"));
        int n = stdin.nextInt();
        int m = stdin.nextInt();

        animal[] all = new animal[m+n];

        // Read in chickens first.
        for (int i=0; i<n; i++) {
            int t = stdin.nextInt();
            all[i] = new animal(t, t, false);
        }

        // Then cows.
        for (int i=n; i<n+m; i++) {
            int s = stdin.nextInt();
            int e = stdin.nextInt();
            all[i] = new animal(s, e, true);
        }

        // Sort all.
        Arrays.sort(all);
        for (int i = 0; i < all.length; i++) {
            System.out.println(all[i]);
        }
        // Ahh very clever: Sorting by the start times
        // allows us to already have a nice idea of what
        // cows are available for a given chicken. That is,
        // chickens will be interspersed among all the cows,
        // and all cows preceding a chicken (barring those that have
        // already been removed), will be available for us to help

        int res = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        // Go through all animal's start times.
        for (int i=0; i<all.length; i++) {

            // If it's a cow, add it to our list of cows waiting to get help from chickens.
            if (all[i].isCow)
                pq.offer(all[i].end);

                // We get a chicken!
            else {

                // Get rid of cows who are no longer available.
                while (pq.size() > 0 && pq.peek() < all[i].start) pq.poll();

                // This chicken can help a cow!
                if (pq.size() > 0) {
                    res++;

                    // We greedily remove the cow who is available the least.
                    pq.poll();
                }

            }
        }

        // Output the result.
//        PrintWriter out = new PrintWriter(new FileWriter("helpcross.out"));
//        out.println(res);
//        out.close();
        System.out.println(res);
        stdin.close();
    }
}

class animal implements Comparable<animal> {
    public int start;
    public int end;
    public boolean isCow;

    public animal(int s, int e, boolean b) {
        start = s;
        end = e;
        isCow = b;
    }

    public String toString() {
        return "(" + start + ", " + end + ")";
    }

    public int compareTo(animal other) {
        if (this.start != other.start)
            return this.start - other.start;
        if (this.isCow && !other.isCow) return -1;
        if (!this.isCow && other.isCow) return 1;
        return 0;
    }
}
/*
(336, 2841)
(795, 1365)
(1313, 3170)
(709, 3636)
(2305, 3230)
(4286, 5727)
(4043, 5793)
(4567, 6423)
(5403, 8054)
(6219, 6843)
(6652, 7408)
(7739, 9751)
(8094, 9633)
(8444, 11063)
(9676, 11044)
(10097, 10999)
(11434, 11812)
(12754, 13153)
(11087, 13895)
(13865, 15554)
(13317, 15809)
(14586, 15551)
(14481, 17156)
(15306, 15989)
(15434, 17798)
(15788, 17372)
(16124, 18019)
(16226, 18812)
(17084, 17411)
(17301, 17581)
(17467, 20068)
(18031, 18148)
(18097, 19868)
(17276, 20454)
(18440, 19169)
(18814, 20181)
(19497, 19850)
(19582, 20127)
38
 */