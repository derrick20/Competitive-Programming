/*
ID: d3rrickl
LANG: JAVA
PROG: palsquare
 */
import java.io.*;
import java.util.*;
public class palsquare
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("palsquare.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
      
      int base = Integer.parseInt(br.readLine());
      ArrayList<String> list = new ArrayList<String>();
      for (int i = 1; i <= 300; i++)
      {
         String square = convert(i*i, base);
         if (square.equals(reverse(square)))
         {
            String original = convert(i, base);
            list.add(original + " " + square);
         }
      }
      
      for (int i = 0; i < list.size(); i++)
      {
         pw.println(list.get(i));
      }
      pw.close();
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
   
   public static String convert(int n, int base)
   {
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
            char bigDigit = (char) (digit + 55);
            str += bigDigit + "";
         }
         else
            str += digit + "";
         pow--;
      }
      return str;
   }
}