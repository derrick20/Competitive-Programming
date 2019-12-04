/*
ID: d3rrickl
LANG: JAVA
PROG: frac1
 */
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
public class frac1
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("frac1.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
      
      int N = Integer.parseInt(br.readLine());
      Map<Double, String> fracs = new TreeMap<Double, String>();
      DecimalFormat df = new DecimalFormat("#");
      for (double num = N; num >= 1; num--)
      {
         for (double denom = num + 1; denom <= N; denom++)
         {  
            fracs.put((double) (num/denom), df.format(num) + "/" + df.format(denom));
            //System.out.println((double) (num/denom));
         }
      }
      pw.println("0/1");
      for (double fraction : fracs.keySet())
      {
         pw.println(fracs.get(fraction));
      }
      pw.println("1/1");
      pw.close();
   }     
}