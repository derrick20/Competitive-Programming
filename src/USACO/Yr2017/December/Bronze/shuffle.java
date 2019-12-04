/*
ID: d3rrickl
LANG: JAVA
PROG: shuffle
 */
import java.io.*;
import java.util.*;
public class shuffle
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
      
      int n = Integer.parseInt(br.readLine());
      int[] shuffle = new int[n + 1];
      int[] nums = new int[n + 1]; // actual value to be rearranged 
      
      StringTokenizer st1 = new StringTokenizer(br.readLine());
      for (int i = 1; i < n + 1; i++)
      {
         shuffle[i] = Integer.parseInt(st1.nextToken());
      }
      
      StringTokenizer st2 = new StringTokenizer(br.readLine());
      for (int i = 1; i < n + 1; i++)
      {
         nums[i] = Integer.parseInt(st2.nextToken()); // BE CAREFUL ABOUT 1-INDEX
      }
      
      int[] inverse = new int[n + 1];
      for (int i = 1; i < n + 1; i++)
      {
         inverse[shuffle[i]] = i;
      }
      // for (int x : inverse)
//          System.out.println(x);
      
      int[] original = Shuffle(inverse, Shuffle(inverse, Shuffle(inverse, nums)));
      for (int i = 1; i < n + 1; i++)
      {
         pw.println(original[i]);
      }
      
      pw.close();
   }
   
   public static int[] Shuffle(int[] function, int[] values)
   {
      System.out.println(values.length);
      int[] shuffled = new int[values.length];
      for (int i = 1; i < values.length; i++)
      {
         shuffled[function[i]] = values[i];
      }
      return shuffled;
   }
}