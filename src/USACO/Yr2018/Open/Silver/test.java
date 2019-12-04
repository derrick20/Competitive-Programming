/*
ID: d3rrickl
LANG: JAVA
PROG: multimoo
*/
import java.io.*;
import java.util.*;
public class test
{
   public static boolean found = false;
   public static int[][] g;
   public static boolean[][] visited;
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("multimoo.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("multimoo.out")));
      
      int nums = Integer.parseInt(br.readLine());
      g = new int[nums][nums];
      for (int i = 0; i < nums; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         for (int j = 0; j < nums; j++)
         {
            int next = Integer.parseInt(st.nextToken());
            g[i][j] = next;
         }
      }
      
      for (int r1 = 0; r1 < g.length; r1++)
                  {
                     for (int c1 = 0; c1 < g[0].length; c1++)
                     {
                        System.out.print(g[r1][c1]);
                     }
                     System.out.println();
                  }
      visited = new boolean[3][3];
      for (boolean[] row : visited)
         Arrays.fill(row, false);
      System.out.println(fillAndCount( 0, 2, 1, 9));
      
   }
   
   public static void print(int[][] array)
   {
      for (int r1 = 0; r1 < array.length; r1++)
      {
         for (int c1 = 0; c1 < array[0].length; c1++)
         {
            System.out.print(array[r1][c1] + " ");
         }
         System.out.println();
      }
   }
   public static int[][] copy(int[][] array)
   {
      int[][] copy = new int[array.length][array.length];
      for (int r1 = 0; r1 < array.length; r1++)
      {
         for (int c1 = 0; c1 < array[0].length; c1++)
         {
            copy[r1][c1] = array[r1][c1];
         }
      }
      return copy;
   }
   
   public static int fillAndCount(int r, int c, int a, int b)
   {
      if (r < 0 || r > g.length - 1 || c < 0 || c > g[0].length - 1)
         return 0;
      else
      {
         if ((g[r][c] == a || g[r][c] == b) && !visited[r][c])
         {
            if (g[r][c] == b)
               found = true;
             System.out.println("r " + r + "c " + c + "val " + g[r][c]);
            visited[r][c] = true;
            int ret = 1 + fillAndCount(r-1, c, a, b) + fillAndCount( r+1, c, a, b) + fillAndCount( r, c+1, a, b) + fillAndCount(r, c-1, a, b);
            return ret;
         }
         else 
            return 0;
      }//*/
   }
}
