/*
ID: d3rrickl
LANG: JAVA
PROG: helpcross
 */
import java.io.*;
import java.util.*;
public class helpcross
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("helpcross.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("helpcross.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int C = Integer.parseInt(st.nextToken());
      int N = Integer.parseInt(st.nextToken());
      ArrayList<Integer> chickenTimes = new ArrayList<Integer>();
      ArrayList<Interval> cowTimes = new ArrayList<Interval>();
      
      for (int i = 0; i < C; i++)
      {
         chickenTimes.add(Integer.parseInt(br.readLine()));
      }
      
      for (int i = 0; i < N; i++)
      {
      
         st = new StringTokenizer(br.readLine());
         int low = Integer.parseInt(st.nextToken()); //AHH BE CAREFUL OF YOUR STSSSS
         int high = Integer.parseInt(st.nextToken());
         cowTimes.add(new Interval(low, high));
      }
      // this sorts the cowTimes so we use the leftmost interval
      Collections.sort(cowTimes, new Comparator<Interval>() {
			@Override
			public int compare(Interval a, Interval b) {
				Integer n = a.getLow();
				Integer m = b.getLow();
				return n.compareTo(m);
			}
		}); 
      
      Collections.sort(chickenTimes);
      
      //attempt #2
      int count = 0;
      for (int c = 0; c < chickenTimes.size(); c++)
      { // then do another for loop through the cow array and see which matches at the soonest ending
         boolean removed = false;
         int cow = 0;
         while (cowTimes.get(cow).getHigh() < chickenTimes.get(c))
         {
               cow++;
               count++;
               chickenTimes.remove(c);
               removed = true;
               cowTimes.remove(n);
               n = N;
         }
         if (!removed)
            chickenTimes.remove(c);
      }
      
      /*
      int cow = 0;
      int count = 0;
      for (int chicken = 0; chicken < C; chicken++) // attempt to schedule each chicken
      {
         if (cow > N - 1)
            break;
         
        else if (chickenTimes[chicken] <= cowTimes[cow].getHigh() && chickenTimes[chicken] >= cowTimes[cow].getLow()) // then we are able to help them
         {
            cow++;
            count++;
         }
         else if (chickenTimes[chicken] > cowTimes[cow].getHigh())
         {
            cow++; // this means we have gone too far and that cow is done, can't be helped
            chicken--; //stay at this chicken see if it can help a different cow
         }// else, the chicken is less than the cow's start, so we move on
         else
         {
         }// 
      }*/
      pw.println(count);
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