/*
ID: d3rrickl
LANG: JAVA
PROG: pprime
 */
import java.io.*;
import java.util.*;
public class pprime
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("pprime.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      TreeSet<Integer> set = new TreeSet<Integer>();
      int[] extras = {2, 3, 5, 7, 11};
      for (int i = 0; i < extras.length; i++)
      {
         if (extras[i] >=  a && extras[i] <= b)
            set.add(extras[i]);
      }
      for (int left = 1; left <= 999; left++)
      {
         for (int mid = 0; mid <= 9; mid++)
         {
            StringBuffer right = new StringBuffer(left + "");
            right.reverse();
            int pal = Integer.parseInt(left + "" + mid + right);
            if (pal <= b && pal >= a)
            {
               //System.out.println(pal + " " + isPrime(pal));
               if (isPrime(pal))
               {
                  set.add(pal);
               }
            }
         }
      }
      Iterator it = set.iterator();
      while (it.hasNext())
      {
         pw.println(it.next());
      }
      pw.close();
   }     
   
   public static boolean isPrime(int n)
   {
      if ((n > 2 && n % 2 == 0) || n == 1)
         return false;
      for (int i = 3; i <= Math.sqrt(n); i += 2)
      {
         if (n % i == 0)
            return false;
      }
      return true;
   }
}