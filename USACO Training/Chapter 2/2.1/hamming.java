/*
ID: d3rrick1
LANG: JAVA
PROB: hamming
*/
import java.io.*;
import java.util.*;
public class hamming
{
   public static int B, N, D;
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("hamming.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter (new FileWriter("hamming.out")));
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      B = Integer.parseInt(st.nextToken());
      D = Integer.parseInt(st.nextToken());
      
      int current = 0; 
      ArrayList<Integer> list = new ArrayList<Integer>();
      list.add(current);
      current++;
      while (list.size() < N)
      {
         boolean check = true;
         for (int x : list)
         {
            if (distance(x, current) < D)
               check = false;
         }
         if (check)
            list.add(current);
         current++;
         //System.out.println(list);
      }
      for (int i = 1; i < N; i++)
      {
         pw.print(list.get(i-1));
         if (i % 10 == 0)
            pw.println();
         else
            pw.print(" ");// god damn this formatting is killing meeee
      }
      pw.println(list.get(N-1));// ALWAYS END IN  NEWLINE
      pw.close();
   }
   
   public static int distance(int a, int b) // basically we never have to work with any strings, keep them internally manipulated
   {
      String s1 = Integer.toBinaryString(Math.max(a,b));
      String s2 = Integer.toBinaryString(Math.min(a,b)); // since we are only looking for absolute distance, this is ok to pick the big/small for easy subtraction
      while (s2.length() < s1.length()) // B is the stored length for all of them
         s2 = "0" + s2; // SHOOT FATAL ERROR FOR TRYING TO LOOK SMART, this could've been avoided by separating variab le names more clearly likebig and small....
      int d = 0;
      for (int i = 0; i < s1.length(); i++)
      {
         if (s1.charAt(i) != s2.charAt(i))
            d++;
      }
      return d;
   }
}