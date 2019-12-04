/*
ID: d3rrickl
LANG: JAVA
PROG: barn1
 */
import java.io.*;
import java.util.*;
public class barn1
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("barn1.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int M = Integer.parseInt(st.nextToken());
      int S = Integer.parseInt(st.nextToken());
      int C = Integer.parseInt(st.nextToken());
      
      if (M >= C) // LMAO DUMLAO you can just use this hack in case you have more boards than cows you just do one per, best case, the other one is good too
      {
         pw.println(C);
         pw.close();
         System.exit(0);
      }
      
      Integer[] cows = new Integer[C];
      Integer[] gaps = new Integer[C-1];
      
      cows[0] = Integer.parseInt(br.readLine());
      for (int i = 1; i < C; i++)
      {
         cows[i] = Integer.parseInt(br.readLine());
      }
      Arrays.sort(cows);
      for (int i = 1; i < C; i++)
      {
         gaps[i - 1] = cows[i] - cows[i - 1];
      }

      Arrays.sort(gaps, Collections.reverseOrder());
      
      for (int x : gaps)
         System.out.println(x);
      
      int stalls = Collections.max(Arrays.asList(cows)) - Collections.min(Arrays.asList(cows)) + 1;
      for (int i = 0; i < M - 1; i++) // for x boards, there must be x-1 gaps
      {
         stalls -= gaps[i] - 1; // subtract one because the gap is the difference, but not the area in between
      }
      
      pw.println(stalls);
      pw.close();
   }
}