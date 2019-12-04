import java.io.*;
import java.util.*;
/*
 * CodeForces Round 346 Div 2 Qualifying Contest
 * Wow you really can't use the 800x100000 sized array, an 8-fold decrease is the  difference i guess
 * Sorting pairs is way better then...
 * @derrick20
 */

public class QualifyingContest {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        ArrayList<Pair>[] scores = new ArrayList[M+1];
        for (int i = 1; i <= M; i++) {
            scores[i] = new ArrayList<>();
        }
        for (int i = 0; i < N; i++) {
            String name = sc.next();
            int group = sc.nextInt();
            int score = sc.nextInt();
            scores[group].add(new Pair(name, score));
        }

        for (int i = 1; i <= M; i++) {
            Collections.sort(scores[i], Comparator.reverseOrder());
            if (scores[i].size() > 2 && scores[i].get(1).compareTo(scores[i].get(2)) == 0) {
                System.out.println("?");
            }
            else {
                System.out.println(scores[i].get(0).name + " " + scores[i].get(1).name);
            }
        }
        /*ArrayList<String>[][] scores = new ArrayList[M+1][801];
        for (int i = 1; i <= M; i++) {
            for (int j = 0; j <= 800; j++) {
                scores[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < N; i++) {
            String name = sc.next();
            int group = sc.nextInt();
            int score = sc.nextInt();
            scores[group][score].add(name);
        }
        for (int i = 1; i <= M; i++) {
            ArrayList<String> ans = new ArrayList<>();
            for (int s = 800; s >= 0; s--) {
                int len = scores[i][s].size();
                int myLen = ans.size();
                if (len == 0)
                    continue;
                else if (myLen == 0 && len == 2) {
                    ans = scores[i][s];
                    System.out.println(ans.get(0) + " " + ans.get(1));
                    break;
                }
                else if (myLen == 0 && len == 1) {
                    // get the 1st place
                    ans.add(scores[i][s].get(0));
                }
                else if (myLen == 1) {
                    if (len == 1) {
                        // found the second place, exit!
                        ans.add(scores[i][s].get(0));
                        System.out.println(ans.get(0) + " " + ans.get(1));
                        break;
                    }
                    else if (len >= 2) {
                        // it's a tie for 2nd place
                        System.out.println("?");
                        break;
                    }
                }
                else if (len >= 3) {
                    // it's a tie for 1st place
                    System.out.println("?");
                    break;
                }
            }
        }
        */
    }

    static class Pair implements Comparable<Pair> {
        String name;
        int score;

        public Pair(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int compareTo(Pair p2) {
            return this.score - p2.score;
        }
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
