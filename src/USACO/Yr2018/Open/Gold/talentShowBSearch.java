/*
 * @author derrick20
 * @problem TalentShow USACO 2018 Gold Open
 */
import java.io.*;
import java.util.*;

public class talentShowBSearch {

    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        Scanner sc = new Scanner(new FileReader("talent.in"));// new InputStreamReader(System.in)); //
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
        int N = sc.nextInt();
        int minWeight = sc.nextInt();
        int[] weight = new int[N];
        int[] value = new int[N];
        int totalTalent = 0;
        for (int i = 0; i < N; i++) {
            int wt = sc.nextInt();
            int val = sc.nextInt();
            weight[i] = wt;
            value[i] = val;
            totalTalent += val;
        }

        // Store, for a given talent, what is the smallest weight used to achieve it?
        int[] dp = new int[totalTalent + 1];
        Arrays.fill(dp, (int) 1e9);
        dp[0] = 0;
        // the min weight to produce 0 talent is 0. However,
        // it is necessary to make everything else big because we can only BUILD
        // from 0
        double bestRatio = 0;
        for (int cow = 0; cow < N; cow++) {
            // look at each cow, and go DOWN THROUGH THE dp array, updating the
            // best weight achievable BEHIND US. This countercurrent motion ensures no repeated
            // counting of a single cow's talents
            int contribution = value[cow];
            int wt = weight[cow];
            for (int talent = totalTalent; talent >= 0; talent--) {
                int boostedTalent = contribution + talent;
                int boostedWeight = dp[talent] + wt;
                // with the min weight of a certain talent,
                // is it possible to get a smaller boostedWeight for a boostedTalent
                if (boostedTalent <= totalTalent) {
                    // If we are in bounds,
                    if (boostedWeight < dp[boostedTalent]) {
                        // and the attempted team has a better weight value
                        // then update it!
                        // Initially, when everything is 1 billion, this cannot
                        // happen except for from the base cases, building outward
                        dp[boostedTalent] = boostedWeight;

                        // At the same time, we can be more efficient by constantly
                        // checking if we had enough weight first of all, and
                        // second of all had a better talent/weight ratio!
                        double ratio = (double) boostedTalent / boostedWeight;
                        if (ratio > bestRatio && boostedWeight >= minWeight) {
                            bestRatio = ratio;
                        }
                    }
                }
            }
        }

        out.println((int) (1000 * bestRatio));
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