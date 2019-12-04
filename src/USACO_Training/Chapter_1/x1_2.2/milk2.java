/*
ID: d3rrickl
LANG: JAVA
PROG: milk2
 */
import java.io.*;
import java.util.*;

public class milk2 {
   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new FileReader("milk2.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
      
      int nums = Integer.parseInt(br.readLine());
      Interval[] array = new Interval[nums];
      
      for (int i = 0; i < nums; i++) {
         StringTokenizer st = new StringTokenizer(br.readLine());
         array[i] = new Interval(st.nextToken(), st.nextToken());
      }
      
   }
}

class Interval {
   private int low;
   private int high;
   
   public Interval(int l, int h) {
      low = l;
      high = h;
   }
   
   public int getLow() {
      return low;
   }
   
   public int getHigh() {
      return high;
   }
}