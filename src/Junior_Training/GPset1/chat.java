// package Junior_Training.GPset1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

public class chat {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        String[] arr = new String[N];
        for (int i = 0; i < N; i++)
            arr[i] = br.readLine();


        HashSet<String> seen = new HashSet<>();
        for (int i = N-1; i >= 0; i--) {
            if (!seen.contains(arr[i])) {
                System.out.println(arr[i]);
                seen.add(arr[i]);
            }
        }
    }
}