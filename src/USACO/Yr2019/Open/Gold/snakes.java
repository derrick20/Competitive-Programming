
import java.io.*;
import java.util.*;

public class snakes {

    static int[] snakes;
    static HashMap<Integer, Integer> valueToIndex;
    static ArrayList<Integer> values;
    static int[][][] dp;
    static int N, K;

    public static void DP(int i, int n, int v) {
        int last = values.get(v);
        if (last - snakes[i] < 0 || dp[i-1][n][v] == -1)
            dp[i][n][v] = -1;
        else
           // TODO I gave up dang it System.out.println(i+"hee "+n+" "+last+" "+(last-snakes[i]));
            dp[i][n][v] =  dp[i-1][n][v] + last-snakes[i];
        if (i < N-1) // Once we hit N-1, we can't progress any further
        {
            DP(i + 1, n, v); // Propagate, keeping the same value

            if (n > 0) // Once we run out of nets, we can't change anymore
                for (int value : values) { // Place all the values into those spots where we chose to CHANGE the net
                    int index = valueToIndex.get(value);
                    if (index != v) { // We have to change the net size!!
                        if (value - snakes[i] < 0 || dp[i - 1][n + 1][index] == -1)
                            dp[i][n][index] = -1;
                        else
                            dp[i][n][index] = dp[i - 1][n + 1][index] + value - snakes[i];
                        DP(i + 1, n - 1, index);
                    }
                }
        }
    } //*/

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//new FileReader("snakes.in"));//
        // PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        snakes = new int[N];
        values = new ArrayList<Integer>();
        valueToIndex = new HashMap<Integer, Integer>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            snakes[i] = Integer.parseInt(st.nextToken());
            values.add(snakes[i]);
        }

        for (int i = 0; i < values.size(); i++) {
            valueToIndex.put(values.get(i), i);
        }

        dp = new int[N][K + 1][values.size()]; // dp[current pos][num nets left][last value] -> min wasted space
        // Fill it up
        for (int i = 0; i < N; i++)
            for (int j = 0; j < K; j++)
                for (int k = 0; k < values.size(); k++)
                    dp[i][j][k] = 0;

        for (int v = 0; v < values.size(); v++) {
            int last = values.get(v);
            for (int i = 0; i < N; i++) {
                if (last >= snakes[i]) {
                    dp[i][K][v] += last - snakes[i];
                } else {
                    while (i < N) {
                        dp[i][K][v] = -1; // All of these values will fail, just go all the way up
                        i++;
                    }
                }
            }

            for (int nets = K-1; nets >= 0; nets--)
                if (last >= snakes[0]) {
                    dp[0][nets][v] = last - snakes[0];
                }
                else
                    dp[0][nets][v] = -1; // We simply can't pick up 10 snakes with a size 2 bag
        }
        for (int nets = K - 1; nets >= 0; nets--) {
            for (int v = 0; v < values.size(); v++) { // Place all the values into those spots where we chose to CHANGE the net
                DP(1, nets, v); // Kick it off with all the possible values
            /*
            for (int i = 0; i < N; i++) {
                for (int v = 0; v < values.size(); v++) { // Try all the possible values.
                    int value = values.get(v);
                    if (dp[i-1][nets+1][v] != -1) { // So we can remove a net and try all these new starting values
                        dp[i][nets][v]
                    }
                    if (last - snakes[i] >= 0) {
                        int newV = valueToIndex.get(snakes[i]); // Now, we have changed our "last" value to be the new snakes
                        dp[i][nets][newV]  = dp[i-1][nets+1][v] + 0; // New Net, we just changed our
                        dp[i][nets][v] =  dp[i-1][nets][v] + last-snakes[i]; // Old net, we keep using
                    } // Either coming from
                }

            }*/
            }
            for (int i = 0; i < N; i++) {
                for (int j = K; j >= 0; j--) {
                //int j = 0;
                    for (int k = 0; k < values.size(); k++)
                        if (dp[i][j][k] != -1)
                            System.out.println(i + " " + j + " " + values.get(k) + " " + snakes[i] + " " + dp[i][j][k]);
                }
            }
            //pw.close();
        }
    }
}

/*
6 2
7 9 8 2 3 2
 */