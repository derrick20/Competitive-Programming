/*
ID: d3rrickl
LANG: JAVA
PROG: milk2
 */
import java.io.*;
import java.util.*;
public class cowfind
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("cowfind.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowfind.out")));
      
      String str = br.readLine();
      int total = 0; // possibilites for left hoof
      //int right = 0; // possibilities for right hoof afterward we multiply these to get total ways
      //String leftStr = str;
      //String rightStr = str;
      
      int hind = 0;
      for (int i = 1; i < str.length(); i++)
      {
         if (str.substring(i - 1, i + 1).equals("(("))
            hind++;
         else if (str.substring(i - 1, i + 1).equals("))"))
            total += hind;
      }
      
      /*while (str.contains("((")) TOO SLOW
      {
         // left++;
         int pos = str.indexOf("((");
         String rightStr = str.substring(pos + 1); // finds all right )) beyond that first left hoof, then add that to total
         //System.out.println(str);
         while (rightStr.contains("))"))
         {
            total++;
            //System.out.println(rightStr);
            rightStr = rightStr.substring(rightStr.indexOf("))") + 1); //so that it doesn't recount that one, but it also moves on
         }
         str = str.substring(pos + 1);
      }*/
      pw.println(total);
      pw.close();
   }
}