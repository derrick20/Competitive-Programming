
import java.io.*;
import java.util.*;

public class walk {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("walk.in"));// new InputStreamReader(System.in)); //
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("walk.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int x = K-1;
        int y = N;
        int val = -12*(7*x+4*y) + 2019201997;
        pw.println(val);
        pw.close();
    }
}
