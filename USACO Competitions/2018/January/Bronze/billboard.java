/*
ID: d3rrickl
LANG: JAVA
PROG: Blocked Billboard II
 */
import java.io.*;
import java.util.*;
public class billboard
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("billboard.in"));
      System.setOut(new PrintStream(new File("billboard.out")));
      
      StringTokenizer st1 = new StringTokenizer(br.readLine());
      StringTokenizer st2 = new StringTokenizer(br.readLine());
      
      int a1 = Integer.parseInt(st1.nextToken());
      int b1 = Integer.parseInt(st1.nextToken());
      int a2 = Integer.parseInt(st1.nextToken());
      int b2 = Integer.parseInt(st1.nextToken());
      
      int x1 = Integer.parseInt(st2.nextToken());
      int y1 = Integer.parseInt(st2.nextToken());
      int x2 = Integer.parseInt(st2.nextToken());
      int y2 = Integer.parseInt(st2.nextToken());
      
      int area = 0;
      if (y2 >= b2 && y1 <= b1 && x2 >= a2 && x1 <= a1)
         area = 0;
      else if (y2 >= b2 && y1 <= b1 && x1 <= a2 && x1 >= a1 && x2 >= a2)
            area = (b2-b1) * (x1-a1);
      else if (y2 >= b2 && y1 <= b1 && x2 <= a2 && x2 >= a1 && x1 <= a1)
            area = (b2-b1) * (a2-x2);
      else if (x1 <= a1 && x2 >= a2 && y1 <= b2 && y1 >= b1 && y2 >= b2)
            area = (y1-b1) * (a2-a1);
      else if (x1 <= a1 && x2 >= a2 && y2 <= b2 && y2 >= b1 && y1 <= b1)
            area = (b2-y2) * (a2-a1);
      else
         area = (b2-b1) * (a2-a1);
         
         
      /*
      if (y2 >= b2 && y1 <= b1 && x2 >= a2 && x1 <= a1) ?? /DAFUQ rearranging da if statements fixed it WHYY!!
         area = 0;
      else if (y2 >= b2 && y1 <= b1)
      {
         if (x1 <= a2 && x1 >= a1 && x2 >= a2)
            area = (b2-b1) * (x1-a1);
         else if (x2 <= a2 && x2 >= a1 && x1 <= a1)
            area = (b2-b1) * (a2-x2);
      }
      else if (x1 <= a1 && x2 >= a2)
      {
         if (y1 <= b2 && y1 >= b1 && y2 >= b2)
            area = (y1-b1) * (a2-a1);
         else if (y2 <= b2 && y2 >= b1 && y1 <= b1)
            area = (b2-y2) * (a2-a1);
      }
      else
         area = (b2-b1) * (a2-a1);
      */
      System.out.println(area);
   }
}