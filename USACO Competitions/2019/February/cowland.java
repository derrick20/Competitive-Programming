/*
ID: d3rrickl
LANG: JAVA
PROG: sort
*/
import java.io.*;
import java.util.*;

public class cowland
{
    public static int[] tour;
    public static boolean[] visited;
    public static int index = 0;
    public static ArrayList<Integer>[] adjList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //new FileReader("cowland.in"));
        System.out.println(new File("test.txt"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int[] values = new int[N+1]; // everything 1-indexed

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++)
            values[i] = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[N+1]; // points index i to list of j other indices
        for (int i = 0; i < N+1; i++)
        {
            adjList[i] = new ArrayList<Integer>(); // fill er up
        }
        for (int i = 0; i < N-1; i++) // add all the edges
        {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int neighbor = Integer.parseInt(st.nextToken());
            adjList[node].add(neighbor);
            adjList[neighbor].add(node);
        }

        for (int i = 0; i < N+1; i++)
            System.out.println(adjList[i]); // WORKS

        tour = new int[2*N-1]; // euler tour is length 2N-1 guaranteed
        visited = new boolean[N+1];

        eulerTour(1);
        for (int x : tour)
            System.out.print(x + " "); // works!!

        // crap well my idea was to find the LCA of each pair then do the xor through the path, using xor(root to a) + xor(root to b) - 2*xor(root to LAC)
        // but i think that's still too slow

        // query time

        br.close();
        pw.close();
    }



    public static void eulerTour(int parent) // somehow updating the index must be global, makes some sense i guess
    {
        tour[index] = parent;
        index++;
        visited[parent] = true;

        for (int child : adjList[parent])
        {
            if (!visited[child])
            {
                eulerTour(child);
                tour[index] = parent; // after
                index++;
            }
        }
    }
}