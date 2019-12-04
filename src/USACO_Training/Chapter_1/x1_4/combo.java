/*
ID: d3rrickl
LANG: JAVA
PROG: combo
 */
import java.io.*;
import java.util.*;
public class combo
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("combo.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
      
      int N = Integer.parseInt(br.readLine());
      int[] john = new int[3];
      int[] master = new int[3];
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      StringTokenizer st2 = new StringTokenizer(br.readLine());
      
      for (int i = 0; i < 3; i++)
      {
         john[i] = Integer.parseInt(st.nextToken());
         master[i] = Integer.parseInt(st2.nextToken());
      }
      int count = 0;
      for (int i = 1; i <= N; i++)
      {
         for (int j = 1; j <= N; j++)
         {
            for (int k = 1; k <= N; k++)
            {
               int[] attempt = {i, j, k};
               if (within(attempt, john, N) || (within(attempt, master, N)))
               {  
                  //for (int x : attempt)
                     //System.out.println(x);
                  count++;
               }
            }
         }
      }
      pw.println(count);
      pw.close();
   }
   
   public static boolean within(int[] attempt, int[] correct, int range)
   {
      boolean valid = true;
      for (int i = 0; i < 3; i++)
      {
         int distance = Math.max(attempt[i], correct[i]) - Math.min(attempt[i], correct[i]);
         if (!(Math.min(distance, range - distance) <= 2))
            valid = false;
      }
      return valid;
   }
}