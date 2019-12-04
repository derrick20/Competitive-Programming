/*
 * @author derrick20
 * Ouch. You tried a complex idea with adding the primes to edges as needed, but
 * you overcomplicated it. A weird statement with primes has no obvious algorithmic
 * approach, since primes are somewhat random. Let's take it over to pencil and paper
 * and let the math do more heavy lifting.
 * Why don't we be cheeky and just make it a loop at first, then add connections to hit the
 * desired mark? (I thought of this. But didn't take it further mathematically)
 * Logically, we should suspect that primes aren't spaced out that far. So, it should not be
 * too hard to just have one edge per node (making a big loop), then get the rest of the way there
 * by using attached edges. A simple way to do this is to attach it to the node N/2 away,
 * which ensures we never cause any interference (all degrees will be 2 or 3, mostly 2's obviously)
 */
import java.io.*;
import java.util.*;

public class primeGraph {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();

        boolean[] isPrime = new boolean[N * (N - 1) / 2 + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) {
                for (int j = i + i; j < isPrime.length; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        int[] nextPrime = new int[N * (N - 1) / 2 + 1];
        for (int i = 2; i < isPrime.length; i++) {
            for (int j = i; j < isPrime.length; j++) {
                // include itself
                if (isPrime[j]) {
                    nextPrime[i] = j;
                    break;
                }
            }
        }
        // Quick brute force to show that there is always a way to force
        // it to add to the right size by adding size 3 degrees
//        for (int i = 2; i < N; i++) {
//            System.out.println(i + " " + ((double) nextPrime[i] / i));
//        }

        out.println(nextPrime[N]);
        // Print the N edges involved in making a cycle
        for (int i = 1; i < N; i++) {
            out.println(i + " " + (i + 1));
        }
        out.println(N + " " + 1);

        // However, still need some more, to reach the nextPrime value
        for (int i = 1; i < nextPrime[N] - N + 1; i++) {
            out.println(i + " " + (i + (int) Math.ceil((double) N / 3)));
        }
        out.close();
//        int edges = 0;
//        TreeSet<Integer>[] adjList = new TreeSet[N + 1];
//        for (int i = 1; i < adjList.length; i++) {
//            adjList[i] = new TreeSet<>();
//        }
//        boolean allDegreePrime = false;
//        while (!isPrime[edges] && !allDegreePrime && edges < (N)*(N-1)/2) {
//            allDegreePrime = true;
//            // assume true, until proven false
//            for (int i = 1; i < adjList.length; i++) {
//                int degree = adjList[i].size();
//                if (!isPrime[degree]) {
//                    allDegreePrime = false;
//                }
//                for (int j = 1; j < adjList.length && j != i && !adjList[i].contains(j); j++) {
//                    int degree2 = adjList[i].size();
//                    // we want to add an edge if this edge isn't prime
//                    // OR if we have fixed everything to be prime, but we haven't gotten high enough
//                    // in our edge count yet
//                    if (!isPrime[degree2]|| (allDegreePrime && !isPrime[edges])) {
//                        adjList[i].add(j);
//                        adjList[j].add(i);
//                        edges++;
//                    }
//                }
//            }
//        }
//        if (edges > (N)*(N-1)/2) {
//            out.println(-1);
//        }
//            out.println(edges);
//            for (int i = 1; i < N + 1; i++) {
//                for (int j : adjList[i]) {
//                    if (i < j) {
//                        out.println(i + " " + j);
//                    }
//                }
//            }
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
