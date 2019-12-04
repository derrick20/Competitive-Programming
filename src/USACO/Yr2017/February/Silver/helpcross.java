/*
 * @author derrick20
 * Wow bruhh this was really hard. Actually, it wasn't really an interval covering
 * problem, but rather, a greedy solution bag understanding problem. The
 * core of the idea is maintaining the best possible cow to grab at a given moment.
 * What this boils down to, is having all cows with start points less than/equal to a given
 * chicken time. Then, we want to inspect each of those in order of increasing end time, as
 * that would enable us to grab the greediest and most conservative interval in which to
 * help a cow. This broadens the sphere of capability for future questions. Although
 * the idiot Bean solution posted works, it skips over the concept of the optimal solution
 * and manually searches for the chicken that fits this constraint. Our order is much more natural
 * and logical. Rup's idea was clever, what I have is I look through the chickens, then keep
 * adding cows that may start before the chicken start time. This is equivalent to splicing the
 * cows and peppering in the chickens based on their start time. This is actually genius,
 * because it simplifies the solution bag idea, and gets rid of one while loop for maintaining
 * the invariatns. Although, the generalized technique still holds for my strategy!
 */
import java.io.*;
import java.util.*;
public class helpcross
{
   static FastScanner sc;
   static PrintWriter out;

   static void setupIO(String problem_name) throws Exception {
      sc = new FastScanner(new FileReader(problem_name + ".in"));
      out = new PrintWriter(new FileWriter(problem_name + ".out"));
   }

   static void setupIO() throws Exception {
      sc = new FastScanner(System.in);
      out = new PrintWriter(System.out);
   }

   public static void main(String[] args) throws Exception {
      setupIO();
      setupIO("helpcross");
      int C = sc.nextInt();
      int N = sc.nextInt();
      ArrayList<Integer> chickenTimes = new ArrayList<Integer>();
      ArrayList<Interval> cowTimes = new ArrayList<Interval>();
      
      for (int i = 0; i < C; i++) {
         chickenTimes.add(sc.nextInt());
      }
      
      for (int i = 0; i < N; i++) {
         int low = sc.nextInt(); //AHH BE CAREFUL OF YOUR STSSSS
         int high = sc.nextInt();
         cowTimes.add(new Interval(low, high, i));
      }
      // this sorts the cowTimes so we use the leftmost interval
      Collections.sort(cowTimes, Comparator.comparing(Interval::getLow).thenComparing(Interval::getHigh));
      
      Collections.sort(chickenTimes);

      // Last try lol!
      // Invariant: The front of the set must have an end >= time, and also time >= start of course
      // as we thought before
      // Set is sorted by earliest end time. Thus, invariant is the top of
      // the stack should be the answer for the current chicken.
      // Otherwise, it will be impossible for us.
      // Each time we try adding a chicken, we update our solution bag to hold this invariant
      PriorityQueue<Interval> stack = new PriorityQueue<>(Comparator.comparing(Interval::getHigh));
      // All we need is for the endpoint to be above us by any iota!

      int count = 0;
      int cow = 0;
      chickenTest: for (int time : chickenTimes) {
         // while there are still more cows left, let's try updating our solution stack
         cowTest: while (cow < N) {
            Interval cowInterval = cowTimes.get(cow);
            if (cowInterval.getLow() <= time) {
               stack.add(cowInterval);
               cow++;
            }
            else {
               // Once we cannot add anymore cows, we failed to
               // get things less than our current goal chicken time
               break cowTest;
            }
         }

         // We must keep maintaining the invariant, so that for the current
         // time we want to find the best interval to cover, we have the answer on the top
         while (stack.size() > 0 && stack.peek().getHigh() < time) {
            stack.poll();
            // Remove anything that stops too early, (from earlier attempts)
            // This is a safe operation because we are testing chickens in increasing
            // time order. Thus, if THIS chicken can't grab that interval, nothing later can either
         }

         // Now, we attempt to grab the solution from the top of our stack
         if (stack.size() > 0) {
            Interval used = stack.poll();
//            System.out.println(used);
            count++;
         }
      }

      // Attempt 3... years later!!
      /*int count = 0;
      int cow = 0;
      chickenTest: for (int c = 0; c < chickenTimes.size(); c++)
      { // then do another for loop through the cow array and see which matches at the soonest ending
         int time = chickenTimes.get(c);
         cowTest: while (true) {
            if (cow >= N) {
               break chickenTest;
            }
            Interval curr = cowTimes.get(cow);
            if ((curr.getLow() <= time && time <= curr.getHigh())) {
               // Ah, we finally found a good cow!
               count++;
               cow++; // Always move on to the next cow!
               break cowTest; // Let's try the next chicken now
            }
            else if (time < curr.getLow()) {
               // Once the cows have passed this chicken, there's no hope for this chicken!
               // Don't worry, because all the cows that we skipped
               // were strictly starting before our chicken. Thus,
               // it was valid to throw them out because they all ended before
               // this chicken (the earliest available). Thus, no matter what,
               // future chickens would have been even LESS ABLE to get this cow
               break cowTest; // Let's try the next chicken
            }
            else {
               cow++; // Only keep trying new cows if we didn't break
               // If we broke out, that last cow might still be valid for a later chicken!
            }
         }
      }*/

      /*//attempt #2
      int count = 0;
      for (int c = 0; c < chickenTimes.size(); c++)
      { // then do another for loop through the cow array and see which matches at the soonest ending
         boolean removed = false;
         int cow = 0;
         while (cowTimes.get(cow).getHigh() < chickenTimes.get(c))
         {
               cow++;
               count++;
               chickenTimes.remove(c);
               removed = true;
//               cowTimes.remove(n);
//               n = N;
         }
         if (!removed)
            chickenTimes.remove(c);
      }
      */
      /*
      int cow = 0;
      int count = 0;
      for (int chicken = 0; chicken < C; chicken++) // attempt to schedule each chicken
      {
         if (cow > N - 1)
            break;
         
        else if (chickenTimes[chicken] <= cowTimes[cow].getHigh() && chickenTimes[chicken] >= cowTimes[cow].getLow()) // then we are able to help them
         {
            cow++;
            count++;
         }
         else if (chickenTimes[chicken] > cowTimes[cow].getHigh())
         {
            cow++; // this means we have gone too far and that cow is done, can't be helped
            chicken--; //stay at this chicken see if it can help a different cow
         }// else, the chicken is less than the cow's start, so we move on
         else
         {
         }// 
      }*/
      out.println(count);
      out.close();
   }
   static class Interval {
      private int low, high, id;
      public Interval(int a, int b, int i)
      {
         low = a;
         high = b;
         id = i;
      }

      public int getID() {
         return id;
      }

      public int getLow()
      {
         return low;
      }

      public int getHigh()
      {
         return high;
      }

      public void setLow(int a)
      {
         low = a;
      }

      public void setHigh(int b)
      {
         high = b;
      }

      public int length()
      {
         return high - low;
      }

      public String toString()
      {
         return "(" + low + ", " + high + ")";
      }
   }

   static class FastScanner {
      BufferedReader br;
      StringTokenizer st;

      FastScanner(InputStream s) {
         br = new BufferedReader(new InputStreamReader(s));
      }

      FastScanner(FileReader s) {
         br = new BufferedReader(s);
      }

      String next() throws IOException {
         while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
         return st.nextToken();
      }

      String nextLine() throws IOException {
         return br.readLine();
      }

      double nextDouble() throws IOException {
         return Double.parseDouble(next());
      }

      int nextInt() throws IOException {
         return Integer.parseInt(next());
      }

      long nextLong() throws IOException {
         return Long.parseLong(next());
      }
   }
}

