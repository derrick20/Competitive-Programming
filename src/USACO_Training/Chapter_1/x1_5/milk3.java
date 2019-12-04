/*
ID: d3rrickl
LANG: JAVA
PROG: milk3
 */
import java.io.*;
import java.util.*;
public class milk3
{
   public static boolean[][] visited = new boolean[21][21];
   public static int aCap, bCap, cCap, total; // keep track of capacities
   public static boolean[] milkList = new boolean[21]; // bad you could be clevererTreeSet<Integer> milkList = new TreeSet<Integer>();
   
   /*public void pour(int from, int to)
   {
      if (from > to)
      {
         
      }
   }*/ // maybe Nahh
   public static void dp(int b, int c)
   {
      if (total == b + c)
      {
         //System.out.println(b + " " + c);
         milkList[c] = true;
      }
      if (!visited[b][c])
      {
         int a = total - b - c;
         //System.out.println(a + " " + b + " " + c);
         // AHH no if statements, because you can still pour into someone even if you have less than them
            int cToA = Math.min(aCap - a, c);
            visited[b][c] = true; // set it true so you don't revisit it during the DP
            dp(b, c - cToA); // either 1: pour until a is full, or 2: pour till c is empty
            visited[b][c] = false;
         
            int aToC = Math.min(cCap - c, a); // how much c can gain versus how much a can lose
            visited[b][c] = true;
            dp(b, c + aToC);
            visited[b][c] = false;

            int bToA = Math.min(aCap - a, b);
            visited[b][c] = true;
            dp(b - bToA, c);
            visited[b][c] = false;
         
            int aToB = Math.min(bCap - b, a);
            visited[b][c] = true;
            dp(b + aToB, c);
            visited[b][c] = false;
         
         //now do it for poured from c to b
            int cToB = Math.min(bCap - b, c);
            visited[b][c] = true; // set it true so you don't revisit it during the DP
            dp(b + cToB, c - cToB); // either 1: pour until abis full, or 2: pour till c is empty
            visited[b][c] = false;
         
            int bToC = Math.min(cCap - c, b);
            visited[b][c] = true; // set it true so you don't revisit it during the DP
            dp(b - bToC, c + bToC); // either 1: pour until abis full, or 2: pour till c is empty
            visited[b][c] = false;
      }
   }
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("milk3.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      aCap = Integer.parseInt(st.nextToken());
      bCap = Integer.parseInt(st.nextToken());
      cCap = Integer.parseInt(st.nextToken());
      total = cCap;
      for (int i = 0; i < 21; i++)
      {
         for (int j = 0; j < 21; j++)
            visited[i][j] = false;
      }
      
      dp(0, cCap);
      // switch to the clever boolean array, fewer comparisons than treeSet Iterator it = milkList.iterator();
      String str = "";
      /*while (it.hasNext())
      {
         str += it.next() + " ";
      }*/
      for (int i = 0; i < 21; i++)
      {
         if (milkList[i])
            str += i + " ";
      }
      pw.println(str.substring(0, str.length() - 1));// fuck it
      pw.close();
   }
}