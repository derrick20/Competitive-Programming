package Junior_Training.src_CF_A;
import java.io.*;
import java.util.*;

public class antondanik {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
        br.readLine();
        String s = br.readLine();
        int D = s.replace("A", "").length();
        int A = s.length() - D;
        if (A == D) {
            pw.println("Friendship");
        }
        else if (A > D) {
            pw.println("Anton");
        }
        else if (A < D) {
            pw.println("Danik");
        }
        br.close();
        pw.close();

    }
}