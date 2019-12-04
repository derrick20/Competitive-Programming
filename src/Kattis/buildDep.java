/*
 * @author derrick20
 * @problem Build Dependencies (Set 3, Problem A)
 * FAILS, need to do topo sort oops
 */
import java.io.*;
import java.util.*;

public class buildDep {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // All of this is just making a reversed adjList from the input LOL
        HashMap<String, ArrayList<String>> adjList = new HashMap<>();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String key = st.nextToken();
            key = key.substring(0, key.length()-1);

            ArrayList<String> starts = new ArrayList<>();
            while (st.hasMoreTokens()) {
                starts.add(st.nextToken());
            }

            for (String start : starts) {
                // We need to create it's neighbor list
                if (!adjList.containsKey(start)) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(key);
                    adjList.put(start, list);
                }
                else {
                    ArrayList<String> list = adjList.get(start);
                    list.add(key);
                }
            }
        }

        // System.out.println(adjList);

        String start = br.readLine();
        String path = "";
        ArrayList<String> queue = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(start);
        // Now bfs from the start node and track everything in the order we see them
        while (!queue.isEmpty()) {
            start = queue.remove(0);
            if (!visited.contains(start))
                path += start + "\n";
            visited.add(start);
            if (adjList.containsKey(start))
                for (String adj : adjList.get(start)) {
                    if (!visited.contains(adj))
                        queue.add(adj);
                }
        }
        System.out.print(path);
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