/*
ID: d3rrickl
LANG: JAVA
PROG: milk
 */
import java.io.*;
import java.util.*;
public class milk
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("milk.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int M = Integer.parseInt(st.nextToken());
      
      farm[] farms = new farm[M];
      for (int i = 0; i < M; i++)
      {
         StringTokenizer st2 = new StringTokenizer(br.readLine());
         farms[i] = new farm(Integer.parseInt(st2.nextToken()), Integer.parseInt(st2.nextToken()));
      }
      Arrays.sort(farms);

      int cost = 0;
      int pos = 0;
      while (N > 0)
      {
         int amount = Math.min(N, farms[pos].quantity);
         N -= amount;
         cost += amount * farms[pos].price;
         pos++;
        // System.out.println(amount + " " + cost);
      }
      pw.println(cost);
      pw.close();
   }
   static class farm implements Comparable<farm> {
		public int quantity, price;
		public farm(int a, int b) {
			price = a;
			quantity = b;
		}
		public int compareTo(farm f) {
			return price - f.price;
		}
	}
}