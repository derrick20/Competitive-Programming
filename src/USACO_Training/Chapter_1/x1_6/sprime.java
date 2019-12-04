/*
ID: d3rrickl
LANG: JAVA
PROG: sprime
 */
import java.io.*;
import java.util.*;
public class sprime
{
   public static ArrayList<Integer> list;
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("sprime.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
      
      int N = Integer.parseInt(br.readLine());
      list = new ArrayList<Integer>();
      superprime(N);
      for (int i = 0; i < list.size(); i++)
      {
         pw.println(list.get(i));
      }
      pw.close();
   }
   public static void superprime(int n) throws Exception
      {        
         recur(2, n); //try leading 2, 3, 5, 7, i.e. all the single-digit primes
         recur(3, n); 
         recur(5, n);
         recur(7, n);
      }
       private static void recur(int k, int n)
      {
         if (k < Math.pow(10, n) && k > Math.pow(10, n - 1) && isPrime(k) == true) {
            list.add(k);
         }
         else if (k > Math.pow(10, n) || isPrime(k) == false) {
         }
         else
         {
            recur(10*k + 1, n);
            recur(10*k + 3, n);
            recur(10*k + 7, n);
            recur(10*k + 9, n);
         }
      }
       public static boolean isPrime(int n)
      {
         if (n == 2 || n == 3)
            return true;
         if (n % 2 == 0 || n % 3 == 0 || n <= 1) // this eliminated 2/3 of the work so not bad.
            return false;
         for (int i = 6; i < Math.sqrt(n) + 1; i += 6) // remember how to do this??!?
         {
            if (n % (i + 1) == 0 || n % (i - 1) == 0)
               return false;
         }
         return true;
      }
}