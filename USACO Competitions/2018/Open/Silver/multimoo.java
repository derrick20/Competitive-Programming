/*
ID: d3rrickl
LANG: JAVA
PROG: multimoo
*/
import java.io.*;
import java.util.*;
public class multimoo
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
      
      HashSet<Integer> list = new HashSet<Integer>();
      for (int i = 0; i < nums; i++)
      {
         for (int j = 0; j < nums; j++)
         {
            list.add(g[i][j]);
         }
      }
      visited = new boolean[g.length][g.length];
      for (boolean[] row : visited)
         Arrays.fill(row, false);
      
      int max = 0;
      int max2 = 0;
      for (int r = 0; r < nums; r++)
      {
         for (int c = 0; c < nums; c++)
         {
            int id1 = g[r][c];
            //System.out.println(id1);
            int val = fillAndCount(g, r, c, id1, id1);
            if (val > max)
               max = val;
               
            for (int id2 : list)
            {
               if (id1 != id2)// && !used[id1][id2])
               {
                  found = false;
                  int val2 = fillAndCount(g, r, c, id1, id2);
                  if (val2 > max2 && found)
                     max2 = val2;
                  if (max2 >= nums*nums / 2)
                     break;
                  for (boolean[] row : visited)
                      Arrays.fill(row, false);
               }
            }
         }
      }
      pw.println(max);
      pw.println(max2);
      pw.close();
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
   
   public static int fillAndCount(int[][] g, int r, int c, int a, int b)
   {
      if (r < 0 || r > g.length - 1 || c < 0 || c > g[0].length - 1)
         return 0;
      else
      {
         if ((g[r][c] == a || g[r][c] == b) && !visited[r][c])
         {
            if (g[r][c] == b)
               found = true;
            // System.out.println("r " + r + "c + " + c + "val " + g[r][c]);
            //int temp = g[r][c];
            //g[r][c] = -1;
            visited[r][c] = true;
            int ret = 1 + fillAndCount(g, r-1, c, a, b) + fillAndCount(g, r+1, c, a, b) + fillAndCount(g, r, c+1, a, b) + fillAndCount(g, r, c-1, a, b);
            //g[r][c] = temp;
            return ret;
         }
         else 
            return 0;
      }//*/
   }
}

// non recursive way
      /*int max = 0;
      for (int r = 0; r < g.length; r++)
      {
         for (int c = 0; c < g[0].length; c++)
         {
            int total = 0;
            if ((g[r][c] == a || g[r][c] == b) && g[r][c] != -1)
            {
               Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
               ArrayList<Integer> list = new ArrayList<Integer>();
               list.add(r);
               list.add(c);
               queue.add(list);
               
               while (!queue.isEmpty())
               {
                  ArrayList<Integer> l = queue.remove();
                  if (!(l.get(0) < 0 || l.get(0) > g.length - 1 || l.get(1) < 0 || l.get(1) > g[0].length - 1))
                  {
                     if (l.get(0) != -1 && l.get(1) != -1 && (g[l.get(0)][l.get(1)] == a || g[l.get(0)][l.get(1)] == b))
                     {
                        int temp = g[l.get(0)][l.get(1)];
                        g[l.get(0)][l.get(1)] = -1;
                        total++;
                        
                        queue.add(new ArrayList<Integer>(Arrays.asList(r-1, c)));
                        queue.add(new ArrayList<Integer>(Arrays.asList(r+1, c)));
                        queue.add(new ArrayList<Integer>(Arrays.asList(r, c-1)));
                        queue.add(new ArrayList<Integer>(Arrays.asList(r, c+1)));
                        g[l.get(0)][l.get(1)] = temp;
                     }
                  }
               }
            }
            System.out.println(total);
            if (total > max)
               max = total;
         }
      }
      return max;*/
