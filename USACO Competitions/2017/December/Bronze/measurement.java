/*
ID: d3rrickl
LANG: JAVA
PROG: 
 */
import java.io.*;
import java.util.*;
public class measurement
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
      
      int nums = Integer.parseInt(br.readLine());
      Event[] array = new Event[nums];
      for (int i = 0; i < array.length; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         int day = Integer.parseInt(st.nextToken());
         String name = st.nextToken();
         String amount = st.nextToken();
         array[i] = new Event(day, name, amount);
      }
      Arrays.sort(array, new CustomComparator());
      
      String current = "111";
      String changed = "111";
      int count = 0;
      Event B = new Event(0, "B", "+7");
      Event E = new Event(0, "E", "+7");
      Event M = new Event(0, "M", "+7");
   
      for (int i = 0; i < 100; i++)// represents a certain day
      {  
         for (int j = 0; j < array.length; j++)// on a given day, modify all the cows which change values that day, checking all events
         {
            if (array[j].getDay() == i) 
            {
               switch (array[j].getName())
               {
                  case "Bessie":
                     B.changeAmount("+" + array[j].getAmount());
                     break;
                  case "Elsie":
                     E.changeAmount("+" + array[j].getAmount());
                     break;
                  case "Mildred":
                     M.changeAmount("+" + array[j].getAmount());
                     break;
               }
            }
         }
         changed = findHighest(B, E, M); // after getting ALL events of a certain day, check the changed
         if (!changed.equals(current))
         {
            current = changed;
            count++;
         }
        /*System.out.println(i + " B " + B.getAmount());
         System.out.println(i + " E " + E.getAmount());
         System.out.println(i + " M " + M.getAmount());
         System.out.println(i + " Current " + current);
         System.out.println(i + " Changed " + changed);//*/
         //System.out.println(count);
      }
      pw.println(count);
      pw.close();
   }
   
   public static String findHighest(Event a, Event b, Event c)
   {
      Event[] values = new Event[3];
      values[0] = a;
      values[1] = b;
      values[2] = c;
      String str = "";
      
      boolean aMax = true;
      boolean bMax = true;
      boolean cMax = true;
      for (Event e : values)
      {
         if (a.getAmount() < e.getAmount())
            aMax = false;
      }
      for (Event e : values)
      {
         if (b.getAmount() < e.getAmount())
            bMax = false;
      }
      for (Event e : values)
      {
         if (c.getAmount() < e.getAmount())
            cMax = false;
      }
      str += aMax ? "1" : "0";
      str += bMax ? "1" : "0";
      str += cMax ? "1" : "0";
      return str;
   }
}
class Event
{
   private int myDay;
   private String myName;
   private int myAmount;
   public Event(int day, String name, String amount)
   {
      myDay = day;
      myName = name;
      myAmount = Integer.parseInt(amount.substring(1));
      if (amount.charAt(0) == '-')
         myAmount *= -1;
   }
   public int getDay()
   {
      return myDay;
   }
   
   public String getName()
   {
      return myName;
   }
   
   public int getAmount()
   {
      return myAmount;
   }
   
   public void changeAmount(String amount)
   {
      int change = Integer.parseInt(amount.substring(1));
      if (amount.charAt(0) == '-')
         change *= -1;
      myAmount += change;
   }
}

class CustomComparator implements Comparator<Event>
{
   @Override
   public int compare(Event e1, Event e2)
   {
      return e1.getDay() - e2.getDay();
   }
}
class CustomComparator2 implements Comparator<Event>
{
   @Override
   public int compare(Event e1, Event e2)
   {
      return e1.getAmount() - e2.getAmount();
   }
}