/*
ID: d3rrickl
LANG: JAVA
PROG: Out of Place
*/
import java.io.*;
import java.util.*;
public class outofplace
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("outofplace.in"));
      System.setOut(new PrintStream(new File("outofplace.out")));
      
      int nums = Integer.parseInt(br.readLine());
      int[] array = new int[nums];
      int[] sorted = new int[nums];
      
      for (int i = 0; i < nums; i++)
      {
         int val = Integer.parseInt(br.readLine());
         array[i] = val;
         sorted[i] = val;
      }
      Arrays.sort(sorted);
      int swaps = 0;
      
      for (int i = 0; i < nums; i++)
         if (array[i] != sorted[i]) // well if n are out of place, the we fix the first (n-2) messed up by swapping the correct to each of the spots (n-2 total)
             swaps++; // Then, on the last 2, they must be the two wrong positions, so one swap is enough, so it's always n-1 swaps
      
      System.out.println(swaps-1); /// DAFUQ I JUST COPIED STACK EXCHNAGE LMAAOAOAOOO
   }
}