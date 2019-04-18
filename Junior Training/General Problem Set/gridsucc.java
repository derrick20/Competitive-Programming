//package Junior_Training.GPset1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
// This freaking works, but i can't submit it ヽ(ಠ_ಠ)ノ
public class gridsucc {
    public static void main(String[] args) throws Exception {
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        int T = Integer.parseInt(sc.nextLine()); //br.readLine());
        int[][] g = new int[3][3];

        for (int t = 0; t < T; t++) {
            for (int i = 0; i < 3; i++) {
                String str = sc.nextLine();
                for (int j = 0; j < 3; j++) {
                    g[i][j] = Integer.parseInt(str.substring(j, j+1));
                }
            }
            System.out.println(solve(g));
        }
    }

    public static int solve(int[][] g) {
        boolean done = true;

        // Initial check in case it's already all zeroes
        for (int r = 0 ; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (g[r][c] == 1) {
                    done = false; // THere is more to be done
                }
            }
        }

        int ct = -1; // If we immediately break, then -1. If ,
        while (!done) {
            int[][] g2 = new int[3][3];
            done = true; // assert this, and see if it still has ones left
            for (int r = 0 ; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    change(g2, g, r, c);
                    if (g2[r][c] == 1) {
                        done = false; // THere is more to be done
                    }
                }
            }
            g = g2; // Let g be g2, and then next time, we'll refresh g2
            ct++;
        }
        return ct;
    }

    public static void change(int[][] g2, int[][] g, int r, int c) {
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            int r2 = r + dx[i];
            int c2 = c + dy[i];
            if (r2 >= 0 && r2 < 3 && c2 >= 0 && c2 < 3) {
                sum += g[r2][c2];
            }
        }
        g2[r][c] = sum % 2;
    }
}