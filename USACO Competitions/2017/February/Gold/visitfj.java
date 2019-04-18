/*
ID: d3rrickl
LANG: JAVA
PROG: visitfj
 */
import java.io.*;
import java.util.*;
public class visitfj
{
   public static int[][] arr;
   public static int timeStep;
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("visitfj.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("visitfj.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      arr = new int[N][N];
      timeStep = Integer.parseInt(st.nextToken());
      
      pw.println(dfs(0, 0, 0, 0));
      pw.close();
   }     
   
   public static long dfs(int x, int y, int step, long total) // oops you have to use DIJKSTRA'S
   {
      if (x == arr[0].length - 1 && y == arr.length - 1)
         return 0; // finished
      else if (x < 0 || x >= arr[0].length || y < 0 || y >= arr.length)
         return Long.MAX_VALUE;
      else
      {
         total += timeStep;
         if (step != 0 && step % 3 == 0)
            total += arr[x][y];
         step++;
         return total + Math.min(Math.min(dfs(x+1, y, step, total), dfs(x-1, y, step, total)), Math.min(dfs(x, y+1, step, total), dfs(x, y-1, step, total)));
      }
   }
}