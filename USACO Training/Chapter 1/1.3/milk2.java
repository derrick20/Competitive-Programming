/*
ID: d3rrickl
LANG: JAVA
PROG: milk2
 */
import java.io.*;
import java.util.*;
public class milk2 // FINALLY WORKED I LOVE CS!!!!! YEEEEEE
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("milk2.in"));
      System.setOut(new PrintStream(new File("milk2.out")));// PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
      int nums = Integer.parseInt(br.readLine());
      //Interval[] intervals = new Interval[nums];
      ArrayList<Interval> list = new ArrayList<Interval>();
      for (int i = 0; i < nums; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         //intervals[i] = new Interval(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
         list.add(new Interval (Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
      }
      // Insertion sort first
      for (int i=1; i<nums; ++i)
        {
            Interval key = list.get(i);
            int j = i-1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && list.get(j).getLow() > key.getLow())
            {
                list.set(j+1, list.get(j));
                j = j-1;
            }
            list.set(j+1, key);
        }
      
      int j = 0;
      while (j < list.size()) // Merges overlapping intervals
      {
         int k = j + 1;
         //System.out.println(k);
         while (k < list.size())
         {
            if (list.get(k).getLow() <= list.get(j).getHigh()) // && list.get(k).getHigh() >= list.get(j).getLow()) // If intersection then merge
            {
               int min = Math.min(list.get(j).getLow(), list.get(k).getLow()); // YOU CAN'T JUST SET THE NEW ONE IT HAS TO BE THE MIN
               int max = Math.max(list.get(j).getHigh(), list.get(k).getHigh());
               list.set(j, new Interval(min, max));
               list.remove(k);
               //System.out.println(list.get(k - 1) + ", " + list.size());
            }
            else // if it deletes it, then stay at that k since everything is shifted down, otherwise, move on
               k++;
            /*for (int a = 0; a < list.size(); a++)
               System.out.println(list.get(a) + ", position: " + a + ", j, k: " + j + " " + k);
            //System.out.println(*/

         }
         j++;
      }
      int maxMilk = list.get(0).length(); //Searches for the max interval of milk and of gap
      int maxIdle = 0;
     /* for (int k = 0; k < list.size(); k++)
         System.out.println(list.get(k));
         //*/
      for (int k = 1; k < list.size(); k++)
      {
         if (list.get(k).length() > maxMilk)
            maxMilk = list.get(k).length();
         //System.out.println(maxMilk);
         int l = list.get(k).getLow();
         int h = list.get(k - 1).getHigh();
         if (l - h >= maxIdle)
         {
            maxIdle = l - h;
         }
      }
      System.out.println(maxMilk + " " + maxIdle);
   }
}
class Interval
{
   private int low, high;
   public Interval(int a, int b)
   {
      low = a;
      high = b;
   }

   public int getLow()
   {
      return low;
   }

   public int getHigh()
   {
      return high;
   }

   public void setLow(int a)
   {
      low = a;
   }

   public void setHigh(int b)
   {
      high = b;
   }

   public int length()
   {
      return high - low;
   }

   public String toString()
   {
      return low + " " + high;
   }
}