/*
ID: d3rrickl
LANG: JAVA
PROG: reststops
 */
import java.io.*;
import java.util.*;
public class reststops // ALWAYS USE LONGS WHEN YOU SEE THE INTS
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("reststops.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reststops.out")));
      StringTokenizer st = new StringTokenizer(br.readLine());
      int L = Integer.parseInt(st.nextToken());
      int N = Integer.parseInt(st.nextToken());
      int farmer = Integer.parseInt(st.nextToken());
      int bessie = Integer.parseInt(st.nextToken());
      long[] stops = new long[L+1]; // one-index
      
      for (int i = 0; i <= L; i++)
         stops[i] = 0; // just to start out with 0
      
      for (int i = 0; i < N; i++)
      {
         st = new StringTokenizer(br.readLine());
         stops[Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
         // this stores the tastiness at the position of the stop in the array
      }
      
      boolean[] toStop = new boolean[L+1];
      for (int i = 0; i <= L; i++)
         toStop[i] = false;
      // now we see which spots should be stopped at
      long max = 0;
      for (int i = L; i >= 1; i--) // AHH remember -- since from the back
      {
         if (stops[i] > max) // since all are set to 0,  this won't happen until we find a higher one
         {
            max = stops[i];
            toStop[i] = true;
         }
      }
      
      // now we know where we can stop, we just need to count up how much tastiness we can get. The greedy approach
      // is to stop as long as we can at each stop
      int netDiff = farmer - bessie; // how much time bessie gets at every meter, we keep adding this to our time available for eating
      long time = 0; // time starts at 1 because we are 1 off during the
      long tastiness = 0;
      for (int pos = 1; pos <= L; pos++)
      {
         time += netDiff; // this happens at the end because we gain time, but after we first (eat or not)
         if (toStop[pos])
         {
            tastiness += time * stops[pos];
            time = 0; // we use up time again
            System.out.println(tastiness);
         }
      }
      
      pw.println(tastiness);
      pw.close();
   }
}