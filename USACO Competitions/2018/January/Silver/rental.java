/*
ID: d3rrickl
LANG: JAVA
PROG: Rental Service
 */
import java.io.*;
import java.util.*;
public class rental
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("rental.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rental.out"))); // ugh ok this competition was hard, come back to it eventually
                                                                                          // I think overall you did pretty well you just weren't there yet,
                                                                                          // you'll get better don't worry!! Good attempt thinking process

      // read in all the stuff
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int M = Integer.parseInt(st.nextToken());
      int R = Integer.parseInt(st.nextToken());
      
      int cows[] = new int[N];
      store stores[] = new store[M];
      Integer farms[] = new Integer[R];
      
      for (int i = 0; i < N; i++)
         cows[i] = Integer.parseInt(br.readLine());
      for (int i = 0; i < M; i++)
      {
         StringTokenizer st1 = new StringTokenizer(br.readLine());
         stores[i] = new store(Integer.parseInt(st1.nextToken()), Integer.parseInt(st1.nextToken()));
      }
      for (int i = 0; i < R; i++)
         farms[i] = Integer.parseInt(br.readLine());
      
      // calc the max profit
      long profit = 0;
      
      Arrays.sort(cows); // sorts the cows by most amount of milk
      
      Arrays.sort(stores, new Comparator<store>() { // sorts the stores by value per gallon IN REVERS
			@Override
			public int compare(store a, store b) {
				return b.price() - a.price(); // REVERSE ORDER, since it should be n.compareTo(m)
			}
		});
      
      Arrays.sort(farms);
      Collections.reverse(Arrays.asList(farms));
       
      /*for (int x : cows)
         System.out.print(x + " ");
      for (store x : stores)
         System.out.print(x + " ");
      for (int x : farms)
         System.out.print(x + " ");
      */ 
      int i = 0, j = 0, k = 0; // pointers of cows, stores, and farms
      
      while (i < N)
      {
         // System.out.println(profit);
         if (cowValue(cows[i], stores) < farms[k])
         {
            profit += farms[k];
            // System.out.println(farms[k] + " ");
            k++; // that farm is used up too
         }
         else
         {
            while (cows[i] > 0 && j < stores.length) // keep on getting more money from the cow, from more stores too
            {
               if (cows[i] > stores[j].quantity()) // the cow amount is more than that stores desire
               {
                  profit += stores[j].quantity() * stores[j].price(); // increase profit, using all the stores want for milk
                  cows[i] -= stores[j].quantity(); // used up some of its milk
                  j++; // move on a store
               }
               else
               {
                  profit += cows[i] * stores[j].price();  // if cow's milk was less than the store demand, then just take money it can get
                  stores[j].purchase(cows[i]);
                  cows[i] = 0; // basically used up all of cow, so exit
               }
            }
         }
         i++;
      }
      pw.println(profit);// abandon ship failed oops
      pw.close();
   }
   
   public static int cowValue(int cow, store[] stores)
   {
      int i = 0;
      int value = 0;
      while (cow > 0 && i < stores.length)
      {
         if (cow > stores[i].quantity())
         {
            value += stores[i].quantity() * stores[i].price();
            cow -= stores[i].quantity();
            i++;
         }
         else
         {
            value += cow * stores[i].price(); 
            cow = 0; // basically used up all of cow, so exit
         }
      }
      return value;
   }
   
}
class store
   {
      private int p, q;
      public store(int quantity, int price)
      {
         q = quantity;
         p = price;
      }
      
      public int price()
      {
         return p;
      }
      
      public int quantity()
      {
         return q;
      }
      
      public void purchase(int n)
      {
         q -= n;
      }
      
      public String toString()
      {
         return "Quantity: " + q + " Price: " + p;
      }
   }