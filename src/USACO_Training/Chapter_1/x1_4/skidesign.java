/*
ID: d3rrickl
LANG: JAVA
PROG: skidesign
 */
import java.io.*;
import java.util.*;
public class skidesign
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("skidesign.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
      
      int N = Integer.parseInt(br.readLine());
      int[] hills = new int[N];
      
      for (int i = 0; i < N; i++)
      {
         hills[i] = Integer.parseInt(br.readLine());
      }
      Arrays.sort(hills);
      int highest = Math.max(0, hills[N-1] - 17); // last position, since it is the max, going further would do nothing to decrease cost, only exclude more from the range
      int lowest = hills[0]; // lowest possible starting point is first, since going lower would only make it cost more to move things within it
      
      int minCost = Integer.MAX_VALUE;
      for (int i = lowest; i <= highest; i++)
      {
         int cost = 0;
         for (int j = 0; j < N; j++)
         {
            int dif = 0;
            if (hills[j] < i)
               dif = i - hills[j]; // difference
            else if (hills[j] > i + 17)
               dif = i + 17 - hills[j];
            cost += dif*dif;
         }
         if (cost < minCost)
            minCost = cost;
      }
      
      pw.println(minCost);
      pw.close();
   }
}