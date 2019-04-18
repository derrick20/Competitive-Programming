/*
ID: d3rrickl
LANG: JAVA
PROG: lemonade
*/
import java.io.*;
import java.util.*;
public class lemonade
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("lemonade.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lemonade.out")));
      
      int nums = Integer.parseInt(br.readLine());
      ArrayList<Integer> array = new ArrayList<Integer>();
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < nums; i++)
      {
         array.add(Integer.parseInt(st.nextToken()));
      }
      Collections.sort(array);
      Collections.reverse(array);
      
      int count = 0;
      while (array.get(count) >= count)
      {
         count++;
         if (count >= array.size())
            break;
      }
      pw.println(count);
      pw.close();
   }
}