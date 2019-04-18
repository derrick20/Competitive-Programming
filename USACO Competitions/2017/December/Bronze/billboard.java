/*
ID: d3rrickl
LANG: JAVA
PROG: Blocked Billboard
 */
import java.io.*;
import java.util.*;
public class billboard
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("billboard.in"));
      System.setOut(new PrintStream(new File("billboard.out")));
      
      int[] Rect1 = new int[4];
      int[] Rect2 = new int[4];
      int[] Bill = new int[4];
      
      StringTokenizer st1 = new StringTokenizer(br.readLine());
      for (int i = 0; i < 4; i++)
      {
         Rect1[i] = Integer.parseInt(st1.nextToken());
      }
      
      StringTokenizer st2 = new StringTokenizer(br.readLine());
      for (int i = 0; i < 4; i++)
      {
         Rect2[i] = Integer.parseInt(st2.nextToken());
      }
      
      StringTokenizer st3 = new StringTokenizer(br.readLine());
      for (int i = 0; i < 4; i++)
      {
         Bill[i] = Integer.parseInt(st3.nextToken());
      }
      
      int count = 0;
      for (int i = Rect1[0] + 1; i <= Rect1[2]; i++) // For the first rect overlap with bill
      {
         for (int j = Rect1[1] + 1; j <= Rect1[3]; j++)
         {
            if (i > Bill[0] && i <= Bill[2] && j > Bill[1] && j <= Bill[3])
               count++;
         }
      }
      
      for (int i = Rect2[0] + 1; i <= Rect2[2]; i++) // For the second rect overlap with bill
      {
         for (int j = Rect2[1] + 1; j <= Rect2[3]; j++)
         {
            if (i > Bill[0] && i <= Bill[2] && j > Bill[1] && j <= Bill[3])
            {
               count++;
            }
         }
      }
      
      int area1 = (Rect1[2] - Rect1[0]) * (Rect1[3] - Rect1[1]);
      int area2 = (Rect2[2] - Rect2[0]) * (Rect2[3] - Rect2[1]);
      int answer = area1 + area2 - count;
      
      System.out.println(answer);
   }
}