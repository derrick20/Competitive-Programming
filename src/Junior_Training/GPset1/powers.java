import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class powers {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        TreeMap<Long, Long> map = new TreeMap<>();
        long max = 0;
        for (int i = 0; i < N; i++) {
            long value = Long.parseLong(st.nextToken());
            if (!map.containsKey(value))
                map.put(value, (long) (1));
            else
                map.put(value, map.get(value) + 1);
            if (value > max)
                max = value;
        }

        long upper = 1;
        while (upper <= max)
            upper <<= 1; // We should never see a number bigger than this
//        for (long i = 1; i <= upper; i <<= 1) {
//            System.out.println(i);
//        }
        long ct = 0;
        for (long x : map.keySet()) {
            for (long i = 1; i <= upper; i <<= 1) {
                if (map.containsKey(i - x)) {
                    if (i-x == x)
                        ct += map.get(x)*(map.get(x)-1); // Since we don't want to count itself
                    else
                        ct += map.get(x)*map.get(i-x); // we can form it differently using different versions of x
                }
            }
        }
        // Lesson learned, casting LONGS is really important. In general, if you use longs for one thing,
        // USE LONGS EVERYWHERE
        out.println(ct / 2);
        out.close();
    }
}
