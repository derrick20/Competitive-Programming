/*
ID: d3rrickl
LANG: JAVA
PROG: numtri
 */
import java.io.*;
import java.util.*;
public class numtri
{
   public static int N;
   public static int[][] triangle;
   public static int[][] vals;
   
   public static int dfs(int i, int j)
   {
      //another solution
      for (int x = N - 2; x >= 0; x--) // HAHAH the memoized way is 0.588 for the last case, this is 0.644 LMAO
      {
         for (int y = 0; y <= x; y++)
         {
            triangle[x][y] = triangle[x][y] + Math.max(triangle[x + 1][y], triangle[x + 1][y + 1]);
         }
      }
      return triangle[0][0];
      /* this is one solution
         if (vals[i][j] != -1)
         {
            return vals[i][j];
         }
         else if (i < N-1)
         {
            return vals[i][j] = triangle[i][j] + Math.max(dfs(i + 1, j), dfs(i + 1, j + 1));
         }
         else
            return triangle[i][j]; // because we are at the bottom, so that's all we can return
      */
   }
   
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("numtri.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
      
      N = Integer.parseInt(br.readLine());
      triangle = new int[N][N];
      vals = new int[N][N];
      for (int i = 0; i < N; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         for (int j = 0; j <= i; j++)
         {
            triangle[i][j] = Integer.parseInt(st.nextToken());
         }
      } // triangle filled up left to right like a right triangle
      for (int i= 0; i < N; i++)
      {
         for (int j = 0; j < N; j++)
            vals[i][j] = -1;
            //System.out.print(vals[i][j] + " ");
        // System.out.println();
      }
      pw.println(dfs(0, 0));
      /*for (int i= 0; i < N; i++)
      {
         for (int j = 0; j < N; j++)
            System.out.print(vals[i][j] + " ");
         System.out.println();
      }*/
      pw.close();
   }     
}