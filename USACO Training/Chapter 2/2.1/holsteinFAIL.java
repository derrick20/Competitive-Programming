/*
ID: d3rrick1
LANG: JAVA
PROB: holstein
*/
import java.io.*;
import java.util.*;
public class holsteinFAIL {
   public static int[] chosen; // the selected combination of feeds, which converts to vitas
   public static int[][] feeds; // the feeds and how many vitamins each contains
   public static int[] needed; // the needed number of each vitamin type
   public static int[] vitas; // the amount of vitamins in the current feed combination

   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new FileReader("holstein.txt"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));

      int V = Integer.parseInt(br.readLine());
      needed = new int[V];
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int i = 0; i < V; i++) {
         needed[i] = Integer.parseInt(st.nextToken());
      }

      int G = Integer.parseInt(br.readLine());
      chosen = new int[G]; // G is the number of types of feeds
      vitas = new int[V];
      feeds = new int[G][V]; // for each row, it represents a different type of feed, and along the row represents the vitamins
      for (int i = 0; i < G; i++) {
         StringTokenizer st2 = new StringTokenizer(br.readLine());
         for (int j = 0; j < V; j++) {
            feeds[i][j] = Integer.parseInt(st.nextToken());
         }
      }
   }
}
   /*
   public static void df() // repeatedly tests and changes the number in the chosen and vitas, if it can minimize the total number of chosen, and the ranking of chosen  
   {
      boolean done = true;
      for (int i = 0; i < needed.length; i++)
      {
         if (vitas[i] < needed[i])
            done = false;
      }
      if (done)
         return;
      else
      {
         for (int i = 0; i < G; i++)
         {
            chosen[i]++;
            for (int j = 0; j < 
         }
      }
   }
}

4
100 200 300 400
3
50   50  50  50
200 300 200 300
900 150 389 399*/