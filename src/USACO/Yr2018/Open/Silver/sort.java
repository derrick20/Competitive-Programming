/*
ID: d3rrickl
LANG: JAVA
PROG: sort
*/
import java.io.*;
import java.util.*;
public class sort
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("sort.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort.out")));
      
      int nums = Integer.parseInt(br.readLine());
      int[] array = new int[nums];
      for (int i = 0; i < nums; i++)
      {
         int next = Integer.parseInt(br.readLine());
         array[i] = next;
      }
      HashMap<Integer, Integer> original = new HashMap<Integer, Integer>();
      HashMap<Integer, Integer> sorted = new HashMap<Integer, Integer>();
      for (int i = 0; i < nums; i++)
      {
         original.put(array[i], i);
      }
      Arrays.sort(array);
      for (int i = 0; i < nums; i++)
      {
         sorted.put(array[i], i);
      }
      Iterator it = original.keySet().iterator();
      int max = 0;
      while (it.hasNext())
      {
         int key = (int) it.next();
         int dist = original.get(key) - sorted.get(key);
         if (dist >= max )
            max = dist;
      }
      pw.println(max + 1);
      pw.close();
   }     
   
   private static void swap(int[] array, int a, int b)
   {
      int temp = array[b];
      array[b] = array[a];
      array[a] = temp;
   }  
}