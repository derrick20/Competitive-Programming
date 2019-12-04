/*
 * @author derrick20
 * @problem UVa 10313 Pay the Price
 * Main issue was inputting until no more lines. Used bufferedReader.ready(), for some reason
 * while (line != null) didn't work correctly. Also, the edge case of 0 dollars made with 0 coins was
 * supposedly 1 way to achieve this. Not sure...
 */
import java.io.*;
import java.util.*;

public class price {

    static long[][][] memo = new long[301][301][301];
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (br.ready()) {
            line = br.readLine();
            String[] s = line.split(" ");
            int[] a = new int[s.length];
            /*for (int i = 0; i < s.length; i++) {
                System.out.println(i + " " + s[i]);
            }*/
            for (int i = 0; i < s.length; i++)
                a[i] = Integer.parseInt(s[i]);
            long ans = 0;
            switch (a.length) {
                case 1:
                    for (int i = 0; i <= a[0]; i++) {
                        ans += solve(a[0], 1, i);
                    }
                    break;
                case 2:
                    for (int i = 0; i <= a[1]; i++) {
                        ans += solve(a[0], 1, i);
                    }
                    break;
                case 3:
                    for (int i = a[1]; i <= a[2]; i++) {
                        ans += solve(a[0], 1, i);
                    }
                    break;
            }
            System.out.println(ans);
        }
    }

    public static long solve(int amt, int denom, int coins) {
        // too many coins by pigeonhole OR our denom is too big
        if (amt == 0 && coins == 0) { // This is just given :/
            return 1;
        }
        if (coins == 0 || amt < coins || denom > amt / coins) {
            return 0;
        }
        if (amt == coins || coins == 1) {
            return 1; // only one way to pay this sum: all coins of value 1. OR one coin of this value
        }
        if (memo[amt][denom][coins] != 0) {
            return memo[amt][denom][coins];
        }
        long used = solve(amt - denom , denom, coins - 1); // use this denomination, and continue to next state
        long unused = solve(amt, denom + 1, coins); // Don't use this denomination
        return memo[amt][denom][coins] = used + unused;
    }
}
