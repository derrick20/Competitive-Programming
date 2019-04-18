/*
ID: d3rrickl
LANG: JAVA
PROG: crypt1
 */
import java.io.*;
import java.util.*;
public class crypt1
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("crypt1.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
      
      int nums = Integer.parseInt(br.readLine());
      ArrayList<Integer> list = new ArrayList<Integer>(nums);
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < nums; i++)
      {
         list.add(Integer.parseInt(st.nextToken()));
      }
      
      // oops time for triple for loop !!!!
      ArrayList<Integer> threeDigits = new ArrayList<Integer>();
      for (int i = 0; i < nums; i++)
      {
         int hund = list.get(i);
         if (hund != 0)
         {
            for (int j = 0; j < nums; j++)
            {
               int tens = list.get(j);
               for (int k = 0; k < nums; k++)
               {  
                  int ones = list.get(k);
                  threeDigits.add(100 * hund + 10 * tens + ones);
               }
            }
         }
      }
      
      ArrayList<Integer> twoDigits = new ArrayList<Integer>();
      for (int j = 0; j < nums; j++)
      {
         int tens = list.get(j);
         for (int k = 0; k < nums; k++)
         {  
            int ones = list.get(k);
            twoDigits.add(10 * tens + ones);
         }
      }
      
      int count = 0;
      for (int three : threeDigits)
      {
         for (int two : twoDigits)
         {
            String product = two * three + "";
            
            if (product.length() == 4 && String.valueOf(two / 10 * three).length() == 3 && String.valueOf(two % 10 * three).length() == 3 
               && valid(two / 10 * three, list) && valid(two % 10 * three, list) && valid(two * three, list)) // the top line checks that the hidden *** ***** parts are the correct length
            {  // the second line checks that the lines *** *** ****  only use the allowed digits
              // System.out.println(two + " * " + three + " = " + product);
             //  System.out.println(solution && product.length() == 4 ? "AYYAYYAYAYAYAYAYAYAY" : "Nope");
               count++;
            }
         }
      }
      pw.println(count);
      pw.close();
   }
   
   public static boolean valid(int x, ArrayList<Integer> allowed)
   {
      String s = String.valueOf(x);
      boolean validity = true;
      for (int i = 0; i < s.length(); i++)
      {
         if (!allowed.contains(Integer.parseInt(s.substring(i, i + 1))))
            validity = false;
      }
      return validity;
   }
}