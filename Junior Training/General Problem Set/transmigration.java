import java.io.*;
import java.util.*;

public class transmigration {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());


        // Interestingly, this all doesn't work, and you need to use everything as integers, otherwise
        // you get 34.999999 and it rounds incorrectly
        // Here is the correct, (Commented FAILS)
        // NEW RULES: always work with integers, avoid mixing doubles and ints!!

        int K = Integer.parseInt(st.nextToken().substring(2));

        TreeMap<String, Integer> skillMap = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String skill = st.nextToken();
            int level = Integer.parseInt(st.nextToken());
            if (K*level/100 >= 100) // Otherwise, they are forgotten. They may be added back later during transmigration
                skillMap.put(skill, K*level/100);

        }

        for (int i = 0; i < M; i++) {
            String skill = br.readLine();
            if (!skillMap.containsKey(skill)) // Only new skills added.
                skillMap.put(skill, 0);
        }

        out.println(skillMap.size());
        for (Map.Entry<String, Integer> entry : skillMap.entrySet()) {
            out.println(entry.getKey() + " " + entry.getValue());
        }
        out.close();

        /*double K = Double.parseDouble(st.nextToken());

        TreeMap<String, Double> skillMap = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String skill = st.nextToken();
            int level = Integer.parseInt(st.nextToken());
            if (K*level >= 100) // Otherwise, they are forgotten. They may be added back later during transmigration
                skillMap.put(skill, (double) (K*level));

        }

        for (int i = 0; i < M; i++) {
            String skill = br.readLine();
            if (!skillMap.containsKey(skill)) // Only new skills added.
                skillMap.put(skill, 0.0);
        }

        out.println(skillMap.size());
        for (Map.Entry<String, Double> entry : skillMap.entrySet()) {
            out.println(entry.getKey() + " " + entry.getValue().intValue());
        }
        out.close();*/
    }
}
