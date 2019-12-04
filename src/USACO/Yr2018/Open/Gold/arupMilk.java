// Arup Guha
// 5/3/2018
// Solution to 2018 March USACO Gold Problem: Milking Order

import java.util.*;
import java.io.*;

public class arupMilk {

    public static int n;
    public static int k;
    public static int[][] list;

    public static void main(String[] args) throws Exception {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); //new FileReader("milkorder.in"));
        StringTokenizer tok = new StringTokenizer(stdin.readLine());
        n = Integer.parseInt(tok.nextToken());
        k = Integer.parseInt(tok.nextToken());
        list = new int[n][];

        // Read in the lists.
        for (int i=0; i<k; i++) {
            tok = new StringTokenizer(stdin.readLine());
            int size = Integer.parseInt(tok.nextToken());
            list[i] = new int[size];
            for (int j=0; j<size; j++)
                list[i][j] = Integer.parseInt(tok.nextToken())-1;
        }

        // Will store the result here.
        int[] res = null;

        int low = 0, high = k;

        // Run a binary search.
        while (low < high) {

            // Try using the first mid lists.
            int mid = (low+high+1)/2;
            int[] tmp = topSort(mid);

            if (tmp == null)
                high = mid-1;
            else {
                low = mid;
                res = tmp;
            }
        }
        System.out.println(low);
        // Build answer.
        StringBuffer sb = new StringBuffer();
        sb.append(res[0]);
        for (int i=1; i<n; i++)
            sb.append(" "+res[i]);

        // Write result.
        PrintWriter out = new PrintWriter(new FileWriter("milkorder.out"));
        out.println(sb);
        out.close();
        stdin.close();
    }

    public static int[] topSort(int nL) {

        ArrayList[] g = new ArrayList[n];
        for (int i=0; i<n; i++) g[i] = new ArrayList<Integer>();

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        int[] deg = new int[n];

        // Go through each list, adding edges.
        for (int i=0; i<nL; i++) {
            for (int j=0; j<list[i].length-1; j++) {
                g[list[i][j]].add(list[i][j+1]);
                deg[list[i][j+1]]++;
            }
        }

        // Set up the PQ for the top sort.
        for (int i=0; i<n; i++)
            if (deg[i] == 0)
                pq.offer(i);

        int[] res = new int[n];

        // Set up ordering.
        for (int i=0; i<n; i++) {

            if (pq.size() == 0) return null;

            // Put in ordering.
            int id = pq.poll();
            res[i] = id+1;

            // Subtract degrees, add in if anything gets to 0.
            for (int j=0; j<g[id].size(); j++) {
                deg[(Integer)g[id].get(j)]--;
                if (deg[(Integer)g[id].get(j)] == 0)
                    pq.offer((Integer)g[id].get(j));
            }
        }

        // Ta da!
        return res;
    }

}

class vertex implements Comparable<vertex> {

    public int id;
    public int deg;

    public vertex(int myid, int mydeg) {
        id = myid;
        deg = mydeg;
    }

    public int compareTo(vertex other) {
        if (this.deg != other.deg) return this.deg - other.deg;
        return this.id - other.id;
    }

}