/*
ID: d3rrickl
LANG: JAVA
PROG: dualpal
 */
import java.io.*;
import java.util.*;
public class dualpal
{
   public static void main(String[] args) throws Exception
   {
   
      //for (int i = 2; i <= 15; i++)
      //   System.out.println(convert(12, i) + "");
      BufferedReader br = new BufferedReader(new FileReader("dualpal.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int S = Integer.parseInt(st.nextToken());
      
      ArrayList<Integer> list = new ArrayList<Integer>();
      for (int i = S + 1; i <= Integer.MAX_VALUE; i++)
      {
        // System.out.println("-----" +i+"-------");
         int count = 0;
         for (int j = 2; j <= 10; j++)
         {
            String val = convert(i, j);// DON't Get rid of zeroes
           // System.out.println(val);
            String pal = reverse(val);
            if (val.equals(pal) && val.charAt(0) != '0')// checking the front also implies checking the back
            {
               count++;
               //System.out.println(count);
            }
         }
         if (count >= 2)
         {
            list.add(i);
            // System.out.println("DONE " + i);
         }
         if (list.size() == N)
         {
            break;
         }
      }
      for (int i = 0; i < N; i++)
      {
         pw.println(list.get(i));
      }
      pw.close();//*/
   }
   
   public static String trimZeroes(String str)
   {
      int start = 0;
      int end = str.length() - 1;
      while (str.charAt(start) == '0')
      {
         start++;
      }
      while (str.charAt(end) == '0')
      {
         end--;
      }
      return str.substring(start, end + 1);
   }
   
   public static String reverse(String str)
   {
      String newStr = "";
      for (int i = str.length() - 1; i >= 0; i--)
      {
         newStr += str.charAt(i) + "";
      }
      return newStr;
   }
   
   public static String convert(int n, int base) // WTF THERE'S A BUILT IN CONVERT
   { // JUST USE INTEGER.TOSTRING FUAAAAA
      String str = "";
      int pow = (int) (Math.log(n) / Math.log(base));
      while (n >= 0 && pow >= 0)
      {
         //System.out.println(Math.log(n)/ Math.log(base));
         //System.out.println(Math.log(n));
         int amount = (int) Math.pow(base, pow);
         int digit = (int) (n / amount);
         n-= digit*amount;
         if (digit > 9)
         {
            char bigDigit = (char) (digit + '7');
            //System.out.println("OVER TEN" + bigDigit + "");
            str += bigDigit + "";
         }
         else
            str += digit + "";
         pow--;
      }
     // System.out.println(str);
      return str;
   }
}