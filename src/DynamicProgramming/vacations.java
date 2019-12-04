import java.util.*;
import java.io.*;
public class vacations {
    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] sports = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
            sports[i] = Integer.parseInt(st.nextToken());

        int[][] possible = {{0}, {0, 1}, {0, 2}, {0, 1, 2}};
        // dp
        int memo[][] = new int[N][3];
        int min[] = new int[N];

        for (int sport : possible[sports[0]]) {
            if (sport == 0) {
                memo[0][sport] = 1;
                min[0] = 1;
            }
            else {
                memo[0][sport] = 0;
                min[0] = 0;
            }
        }

        for (int i = 1; i < N; i++) {
            int dayMin = 100;
            memo[i][0] = 1 + min[i-1];
            dayMin = Math.min(dayMin, memo[i][0]);

            if ((sports[i] & 1) == 1) { // sport 1 is possible
                int minRest = 100;
                for (int prevSport : possible[sports[i-1]]) {
                    minRest = Math.min(minRest, memo[i-1][prevSport] + (prevSport == 1 ? 1 : 0));
                }
                memo[i][1] = minRest;
                dayMin = Math.min(dayMin, memo[i][1]);
            }

            if ((sports[i] >> 1 & 1) == 1) { // sport 1 is possible
                int minRest = 100;
                for (int prevSport : possible[sports[i-1]]) {
                    minRest = Math.min(minRest, memo[i-1][prevSport] + (prevSport == 2 ? 1 : 0));
                }
                memo[i][2] = minRest;
                dayMin = Math.min(dayMin, memo[i][2]);
            }
            min[i] = dayMin;
        }

        System.out.println(min[N-1]);
    }
}