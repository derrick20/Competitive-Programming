// package Junior_Training.GPset1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class asphalt {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] h = new int[N + 1];
        int[] v = new int[N + 1];
        Arrays.fill(h, 0);
        Arrays.fill(v, 0);
        //int[] ret = new int[N+1];
        for (int i = 1; i < N*N+1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (h[x] == 0 && v[y] == 0) {
                System.out.print(i + " ");
                h[x] = 1;
                v[y] = 1;
            }
        }
        System.out.println();
    }
}
