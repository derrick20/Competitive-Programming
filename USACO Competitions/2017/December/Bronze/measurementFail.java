/*
ID: d3rrickl
LANG: JAVA
PROG: 
 */
import java.io.*;
import java.util.*;
public class measurementFail
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
      
      int nums = Integer.parseInt(br.readLine());
      
      Cow[] herd1 = new Cow[101]; // Take advantage of the fact that array has numbered indices
      
      for (int i = 0; i < nums; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         int day = Integer.parseInt(st.nextToken());
         String name = st.nextToken();
         String temp = st.nextToken();
         
         int change = Integer.parseInt(temp.substring(1));
         if (temp.charAt(0) == '-')
            change *= -1;            
         
         herd1[day] = new Cow(name, change);
      }
      ArrayList<Cow> herd = new ArrayList<>(Arrays.asList(herd1)); // remove that useless day value, just need to know the chronological order
      herd.removeAll(Collections.singleton(null)); // <---- yep
      
      
      ArrayList<Cow> prev = new ArrayList<Cow>();
      
      prev.add(herd.get(0));
      int count = 0;
      for (int i = 1; i < nums; i++)
      {
         ArrayList<Cow> current = new ArrayList<>(prev);
         String newCow = herd.get(i).getName(); // gets the next cow's name
         
         boolean seen = false;
         
         for (int j = 0; j < i; j++) // goes through all of the cows in CURRENT, and sees if any of them already have this name
         {
            if (newCow.equals(current.get(j).getName()))
            {
               int currentMilk = current.get(j).getGal();
               Cow c = new Cow(current.get(j).getName(), currentMilk + herd.get(i).getGal());
               
               current.set(j, c); // sets the milk amount to it's current PLUS the newCow's milk amount;
               seen = true;
               break;
            }
         }
         
         
         if (seen == false)
         {
            current.add(herd.get(i));
         }  
         
         if (isDifferent(findMax(prev), findMax(current))) // goes through and sees if they are diffferent between each day
         {
            count++;
            System.out.println("I'm Different");
         }
         prev = current;
      }
      
      pw.println(count);
      pw.close();
   }
   
   public static boolean isDifferent(ArrayList<Cow> before, ArrayList<Cow> after) // BEFORE AND AFTER ARE SORTED WITHIN THIS METHOD
   {
      /*if (before.size() < after.size())
         return true;
      else // if (before.size() == after.size()) // before CANNOT be >after size
      {*/
         //before.arrayList.sort(Comparator.comparing(Cow::getName)); TOO HARD
         // after.arrayList.sort(Comparator.comparing(Cow::getName));;
         Collections.sort(before, new CustomComparator());
         Collections.sort(after, new CustomComparator());
         
         //Collections.sort(before.arrayList, (c1, c2) -> c1.getName().compareTo(c2.getName()));
         //Collections.sort(after.arrayList, (c1, c2) -> c1.getName().compareTo(c2.getName()));
         
         for (int i = 0; i < after.size(); i++)
         {
            
            if (before.get(i).getGal() != after.get(i).getGal()) // if they are different at one spot, they are different, if everywhere they are the same, the are NOT different
               return true;
            return false;
         }
         return false;
      //}
   }
   
   public static ArrayList<Cow> findMax(ArrayList<Cow> cows) // may have trouble with the fact I'm  considering different cow sizes it should be that all already exist but it might not cause problmems
   {
      ArrayList<Cow> maxes = new ArrayList<Cow>();
      maxes.add(cows.get(0)); // have to account for multiple cows tying too!!
      for (int i = 1; i < cows.size(); i++) // goes through the curernt arraylist of cows
      {
         if (cows.get(i).getGal() > maxes.get(0).getGal())
         {
            maxes = new ArrayList<Cow>(); // basically reset it because all the other ones were beaten
            maxes.add(cows.get(i));
         }
         else if (cows.get(i).getGal() == maxes.get(0).getGal())
         {
            maxes.add(cows.get(i)); // add another one with the same milk value/
         }
      }
      return maxes; // either one or a bunch of cows with same milk value
   }
}

class Cow
{
   private String name;
   private int gal;
   public Cow(String n, int g) // Amount?
   {
      name = n;
      gal = g;
   }
   
   public String getName()
   {
      return name;
   }
   
   public int getGal()
   {
      return gal;
   }
   
   public void setGal(int x) // Don't ever have to change, just have to add
   {
      gal = x;
   }
   
   public int compareTo(Cow c)
   {
      return getName().compareTo(c.getName());
   }
   
   public String toString()
   {
     return name + " " + gal;
   }
}

class CustomComparator5 implements Comparator<Cow>
{
   @Override
   public int compare(Cow c1, Cow c2)
   {
      return c1.compareTo(c2);
   }
}