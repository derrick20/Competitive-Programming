import java.io.*;
import java.util.*;
/*
 *
 * */

public class Sweets {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        Integer[] boys = new Integer[n];
        for (int i = 0; i < n; i++)
            boys[i] = sc.nextInt();

        Integer[] girls = new Integer[m];
        for (int i = 0; i < m; i++)
            girls[i] = sc.nextInt();
        Arrays.sort(boys, Collections.reverseOrder());
        Arrays.sort(girls, Collections.reverseOrder());

        long total = 0;
        // The biggest min is bigger than the smallest max. This is the worst case, but if this passes
        // Then all other cases will work. (Requires that all boys are less than girls
        if (boys[0] > girls[m-1]) {
            System.out.println("-1");
            System.exit(0);
        }
        //System.out.println(girls);
        //System.out.println(boys);

        // use all girls except last one (we want to use the biggest boy on these
        boolean bSat = false;
        int currGirl = 0;
        int currBoy = 0;
        for (int i = 0; i < m-1; i++) {
            int girl = girls[currGirl];
            currGirl++;
            total += girl;
            if (girl == boys[0]) {
                bSat = true;
            }
        }
        if (bSat) {
            total += boys[currBoy]; // the next layer can just be boys, however
            currBoy++;
            while (currBoy < n) {
                total += (long) m * boys[currBoy];
                currBoy++;
            }
        }
        else {
            total += boys[currBoy]; // the first boy's set is all girls, and one of his
            currBoy++;
            total += girls[currGirl]; // finish  the girls
            currGirl++;
            total += (long) (m - 1) * boys[currBoy];
            currBoy++;
            while (currBoy < n) {
                total += (long) m * boys[currBoy];
                currBoy++;
            }
        }
        System.out.println(total);

        /*boolean[] satisfied = new boolean[m]; // Check if each girl's max has been met
        for (int i = 0; i < n; i++) {
            int boy = boys[i];
            int num_sat = 0;
            boolean placedMin = false;
            for (int j = 0; j < m; j++) {
                int girl = girls[j];
                int placed = boy; // Try placing the minimum amount
                if (girl == boy)
                    placedMin = true;
                if (boy > girl) { // A min was greater than the max, so impossible to satisfy
                    System.out.println("-1");
                    System.exit(0);
                }
                // The girl has not gotten her max yet. AND the boy still has one person he gave the min (< n used)
                if (boy <= girl && !satisfied[j] && ((num_sat < m - 1) || placedMin)) {
                    placed = girl; // give her max
                    satisfied[j] = true;
                    num_sat++;
                }
                total += placed;
            }
        }
        System.out.println(total);*/
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