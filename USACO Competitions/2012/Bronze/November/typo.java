/*
ID: d3rrickl
LANG: JAVA
PROG: milk2
 */
import java.io.*;
import java.util.*;
public class typo
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("typo.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("typo.out")));
      String str = br.readLine();
      
      int l = 0;
      int r = 0; // left and right unbalanced parentheses counters
      ArrayList<Character> list = new ArrayList<Character>();
      for (char c : str.toCharArray()) // clever way to use for each loop
      {
         list.add(c);
      }
      
      for (int i = 0; i < list.size(); i++)
      {
         if (list.get(i) == '(' && l >= r) // there are more ( than ), and ( is increasing
         {
            l++;
         }
         else if (list.get(i) == ')' && l > r) // there are more ( than ) and there is a ), so we can remove one
         {
            l--;
         }
         else if (list.get(i) == ')')
         {
            r++;
         }
         System.out.println(r);
         System.out.println(l);
      }
      int total = r + l;
      
      pw.println(total);
      pw.close();
   }
}
      