/*
 * @author derrick20
 * Tested on Codeforces Powerful Array Round 86 Div. 1 D
 */
import java.io.*;
import java.util.*;

public class MoAlgorithm {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int Q = sc.nextInt();
        // Definitively this is a BETTER SQUARE ROOT VALUE!! YAY
        size = (int) (N / Math.sqrt(Q)) + 1;
//        size = (int) Math.sqrt(N) + 1;
        arr = new int[N];
        int maxVal = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
            if (arr[i] > maxVal) {
                maxVal = arr[i];
            }
        }

        ArrayList<Query> queries = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            int l = sc.nextInt() - 1;
            int r = sc.nextInt() - 1;
            queries.add(new Query(i, l, r));
        }
        Collections.sort(queries);
        long[] answers = new long[Q];
        freqMap = new int[maxVal + 1];
        int left = 0;
        int right = 0;
        runningAns = arr[0];
        freqMap[arr[0]] = 1;
        for (Query q : queries) {
            int l = q.l;
            int r = q.r;

            while (left < l) {
                remove(left++);
            }
            while (left > l) {
                add(--left);
            }
            while (right < r) {
                add(++right);
            }
            while (right > r) {
                remove(right--);
            }
            answers[q.id] = runningAns;
        }
        for (int i = 0; i < answers.length; i++) {
            out.println(answers[i]);
        }
        out.close();
    }

    static int[] freqMap;
    static int[] arr;
    static int size;
    static long runningAns;

    static void add(int i) {
        int oldFreq = freqMap[arr[i]];
        runningAns += arr[i] * (2 * oldFreq + 1);
        freqMap[arr[i]]++;
    }

    static void remove(int i) {
        int oldFreq = freqMap[arr[i]];
        runningAns += arr[i] * (-2 * oldFreq + 1);
        freqMap[arr[i]]--;
    }

//    static void update(int val, int delta) {
//        if (freqMap.containsKey(val)) {
//            if (delta > 0) {
//                freqMap.put(val, freqMap.get(val) + 1);
//            }
//            else {
//                freqMap.put(val, freqMap.get(val) + 1);
//                if (freqMap.get(val) == 0) {
//                    freqMap.remove(val);
//                }
//            }
//        }
//        else {
//            if (delta > 0) {
//                freqMap.put(val, 1);
//            }
//            else {}
//        }
//    }

    static class Query implements Comparable<Query> {
        int l, r;
        int block;
        int id;

        public Query(int i, int ll, int rr) {
            id = i;
            l = ll;
            r = rr;
            block = l / size;
        }

        public int compareTo(Query q2) {
            if (block != q2.block) {
                return block - q2.block;
            }
            else {
                // We alternate from increasing to decreasing r
                // to further boost us
                return (r - q2.r) * (block % 2 == 0 ? 1 : -1);
            }
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}