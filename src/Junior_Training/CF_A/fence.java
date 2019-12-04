
/*
ID: d3rrickl
LANG: JAVA
PROG: Sleepy Cow Sorting
*/
package Junior_Training.src_CF_A;
import java.io.*;
import java.util.*;

public class fence {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        int width = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int height = Integer.parseInt(st.nextToken());
            if (height > h)
                width += 2;
            else
                width++;
        }
        pw.println(width);
        br.close();
        pw.close();
    }
}