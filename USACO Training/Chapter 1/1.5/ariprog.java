/*
ID: d3rrickl
LANG: JAVA
PROG: ariprog
 */
import java.io.*;
import java.util.*;
public class ariprog
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("ariprog.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
      
      int N = Integer.parseInt(br.readLine()); // OH YESS 
      int M = Integer.parseInt(br.readLine());
      boolean[] list = new boolean[M*M*2 + 1];
      
      
      for (int i = 0; i <= M; i++) // create all possible bisquares p = 0, 1, 2,... M q = 0, 1, 2.. M
      {
         for (int j = i; j <= M; j++)
            list[i*i + j*j] = true;
      }
      //Arrays.sort(list); // switch to hashset perhaps speedier?
       // System.out.println(list);
      
      //Set<Integer> diff = new HashSet<Integer>();
      /*for (int i : list)
      {
         for (int j : list)
         {
            int possDiff = j - i;
            // System.out.println(list.get(j) + " " +  list.get(i));
            if (possDiff > 0)
               diff.add(possDiff);
         }
      }*/
      // System.out.println(diff);
      // int max = Collections.max(list);
      boolean none = true;
      for (int r = 1; r <= M*M*2; r++)//int j : diff) // go through all a values, or possible starting positions, but remember we need at least N to work with lol
      {
         for (int a = 0; a + r*(N-1) <= M*M*2; a++) // go through all possible b values b > 0 !!!!
         {
            boolean ret = true;
            for (int k = 0; k < N; k++) // now test it out for the full n length progression
            {
               if (!list[a + r*k] || a + r*k > M*M*2)
               {
                  ret = false;
                  k = N; // to break
               }
            }
            if (ret)
            {
               pw.println(a + " " + r);
               none = false;
            }
         }
      }
      // pw.println(list);
      if (none) // we found none
         pw.println("NONE");
      pw.close();
   }
}