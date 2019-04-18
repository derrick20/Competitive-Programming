/*
ID: d3rrickl
LANG: JAVA
PROG: sort3
 */
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
public class sort3
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("sort3.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
      
      int[] array = new int[Integer.parseInt(br.readLine())];
      int[] sorted = new int[array.length];
      for (int i = 0; i < array.length; i++)
      {
         int next =  Integer.parseInt(br.readLine());
         array[i] = next;
         sorted[i] = next;
      }
      Arrays.sort(sorted);
      
      int[][] positions = new int[4][4];
      for (int i = 0; i < array.length; i++)
      {
         positions[array[i]][sorted[i]]++; // so we see how many are some num's position, and we can figure out how many can be fixed
      }// with one swap, or 3 nums fixed with 2 swaps
      
      int oneAndTwo = Math.min(positions[1][2], positions[2][1]); // ONE and TWO
      int oneAndThree = Math.min(positions[1][3], positions[3][1]); // ONE AND THREE
      int twoAndThree = Math.min(positions[2][3], positions[3][2]); // TWO and THREE
      int oneTwoThree = Math.max(positions[1][2], positions[2][1]) - oneAndTwo; // note this is arbitrary, it works if we subtract oneAndThree etc.
      
      pw.println(oneAndTwo + oneAndThree + twoAndThree + 2*oneTwoThree);
      pw.close();
   }
}