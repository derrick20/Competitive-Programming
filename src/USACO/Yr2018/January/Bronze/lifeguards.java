/*
ID: d3rrickl
LANG: JAVA
PROG: Lifeguards
*/
import java.io.*;
import java.util.*;
public class lifeguards
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("lifeguards.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lifeguards.out")));
      
      int nums = Integer.parseInt(br.readLine());
      Interval[] array = new Interval[nums];
      for (int i = 0; i < nums; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         array[i] = new Interval(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
      }
      
      Arrays.sort(array, new Comparator<Interval>() {
			@Override
			public int compare(Interval a, Interval b) {
				Integer n = a.getLow();
				Integer m = b.getLow();
				return n.compareTo(m);
			}
		});
      
      int totalTime = 0;
      
      Stack<Interval> stack = new Stack<Interval>();
      stack.push(array[0]);
      for (int i = 1; i < nums; i++)
      {
         if (stack.peek().getHigh() > array[i].getLow() && stack.peek().getHigh() < array[i].getHigh())
         {
            Interval temp2 = stack.pop();
            Interval temp = new Interval(temp2.getLow(), temp2.getHigh());
            temp.setHigh(array[i].getHigh());
            stack.push(temp);
         }
         else
            stack.push(array[i]);
      }
      while (!stack.empty())
      {
         //System.out.println(totalTime);
         totalTime += stack.pop().length();
      }//*/
      
      // It's necessary to do this stack or some variation in order to find the sum of total length
      // You can do this easily by merging, since otherwise you'd have to do case by case whether the adjacent are overlapping or not,
      // and that would make it hard to know when to use Math.max or Math.min, i think this way was more clean
      // Then, we can do the technique of going through and finding the min gaps by seeing how much each interval actually contributes to the time
      // Since some contribute less due to overlapping, we find the amount of their interval that isn't overlapped, hence min(start(n+1), end(n)) - max(end(n-1), start(n))
      // This formula gives ONLY the part of it which is singular and not overlapped by others, it could be zero even
      // Then we keep track of this minimum gap while traversing, that way we subtract the minimum one from the total time at the end, Printing that!! NICEEE!!
      
      // Lucky save by doing the stack method after realizing you can't find the total length without merging, it's too messy I'd say


      int minGap = Math.min(array[1].getLow(), array[0].getHigh()) - array[0].getLow();
      // totalTime += minGap;
      for (int i = 1; i < nums - 1; i++) // FIRST and LAST must be dealt with differently, or else out of bounds error
      {
         // Interval bits and bobs
         int interval = Math.min(array[i+1].getLow(), array[i].getHigh()) - Math.max(array[i-1].getHigh(), array[i].getLow());

         if (interval < minGap)
            minGap = interval;
            
         if (array[i].getLow() >= array[i-1].getLow() && array[i].getHigh() <= array[i-1].getHigh())
            minGap = 0;
         
         // Total Time shenanigans
         /*
         if (array[i].getLow() < array[i-1].getHigh())
         {
            if (array[i].getHigh() > array[i+1].getLow())
               totalTime += array[i+1].getLow() - array[i].getLow();
            else
               totalTime += array[i].getHigh() - array[i].getLow();
         }
         else
         {
            if (array[i].getHigh() > array[i+1].getLow())
               totalTime += array[i+1].getLow() - array[i].getHigh();
            else
               totalTime += array[i].getHigh() - array[i].getLow();
         }
         */
      }
      // last one
      int lastGap = array[nums - 1].getHigh() - Math.max(array[nums - 2].getHigh(), array[nums - 1].getLow());
      //totalTime += array[nums - 1].getHigh() - Math.min(array[nums - 2].getHigh(), array[nums - 1].getLow());
         //System.out.println(totalTime);
      if (lastGap < minGap)
         minGap = lastGap;
      //*/
      pw.println(totalTime - minGap);
      pw.close();
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