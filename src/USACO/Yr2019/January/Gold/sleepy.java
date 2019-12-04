/*
ID: d3rrickl
LANG: JAVA
PROG: Sleepy Cow Sorting
 */
import java.io.*;
import java.util.*;


public class sleepy
{
   public static class Node
   {
      Node l, r;
      int num;
      int small;
      public Node(int n)
      {
         num = n;
      }
   }
   public static int x = 0;
   public static int insert(Node node, Node ins)
   {
      if (ins.num < node.num)
      {
         // ins.small = node.small;
         node.small++;
         if (node.l == null)
         {
            node.l = ins;
            return x; //ins.small;
         }
         return insert(node.l, ins);
      }
      else
      {
         // ins.small = node.small + 1;
         x += node.small+1;
         if (node.r == null)
         {
            node.r = ins;
            return x;//ins.small;
         }
         return insert(node.r, ins);
      }
   }

   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("sleepy.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
//       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//       PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
      // On the spot redo
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int[] cows = new int[N];
      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
      {
         cows[i] = Integer.parseInt(st.nextToken());
      }

      Node root;
      int rind = N-2;
      int numsorted = 1;
      int min = cows[cows.length-1];
      // keep moving until we EXIT the increasing end sequence
      while (cows[rind] < min)
      {
         min = cows[rind];
         rind--;
         numsorted++;
      }
      ArrayList<Integer> printList = new <Integer>ArrayList();
      printList.add(cows[rind] - 1); // The root node is guaranteed to only have lessThanMe values, nothing else
      // First node at the right to start it off
      int befval = rind;
      root = new Node(cows[rind]);
      numsorted++;
      rind--;

      // Now, repeatedly use inclusion-exclusion to calculate the total moves
      while (numsorted < N)
      {
         int lessThanMe = cows[rind] - 1; // By definition
         int unsorted = befval - rind; // We have to jump over this many to get into the sorted region
         int unsortedAndLess = insert(root, new Node(cows[rind]));
         // System.out.println(cows[rind] + " "+ lessThanMe + " " + unsorted + " " + unsortedAndLess);
         printList.add(lessThanMe + unsorted - unsortedAndLess);
         x = 0;
         rind--;
         numsorted++;
      }
      pw.println(printList.size());
      for (int i = printList.size() - 1; i >= 0; i--)
      {
         if (i == 0)
            pw.print(printList.get(i));
         else
            pw.print(printList.get(i)+" ");
      }
      pw.close();
      // read in all the stuff
      /*
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      LinkedList<Integer> ll = new LinkedList<Integer>();
      int[] array = new int[N];
      boolean sorted = true;
      st = new StringTokenizer(br.readLine());
      for (int i = 0; i < N; i++)
      {
         int next = Integer.parseInt(st.nextToken());
         ll.addLast(next);
         array[i] = next;
         if (i != 0 && array[i] < array[i-1])
         {
            sorted = false;
         }
      }
      
      if (sorted)
      {
         pw.println(0); // no moves needed,
         pw.close();
         System.exit(0);
      }
      
      /*
      Iterator<Integer> it = ll.iterator();
         while (it.hasNext())
         {
            int next = it.next();
            System.out.println(next);
         }
      //
      int stepsBack = 0;
      ArrayList<Integer> moves = new ArrayList<Integer>();
      int lastFront = -1;
      do
      {
         int front = ll.poll();
         if (lastFront == -1)
         {
            int prev = 1000000;
            while (true)
            {
               int next = ll.get(N - stepsBack - 2); // start from where we left off. We keep monotonically expanding that end increasing sequence until a full sorted
                  // either it stops being decreasing, or we've just exceeded 
            
               if (next > prev || front > next) // we've found where to insert
               {
                  ll.add(N - stepsBack - 1, front); // insert it, but 0-indexed remember
                  break;
               }
               prev = next;
               stepsBack++;
            }
         }
         else if (lastFront != -1 && lastFront > front) // then we keep pushing back and expanding increasing sequence
         {
            int prev = 1000000;
            while (true)
            {
               int next = ll.get(N - stepsBack - 2); // start from where we left off. We keep monotonically expanding that end increasing sequence until a full sorted
                  // either it stops being decreasing, or we've just exceeded 
            
               if (next > prev || front > next) // we've found where to insert
               {
                  ll.add(N - stepsBack - 1, front); // insert it, but 0-indexed remember
                  break;
               }
               prev = next;
               stepsBack++;
            }
         }
         else if (lastFront != -1 && lastFront < front)
         {
            int prev = 0;
            while (true)
            {
               int next = ll.get(N - stepsBack - 2); // start from where we left off. We keep monotonically expanding that end increasing sequence until a full sorted
                  // either it stops being decreasing, or we've just exceeded 
                  
               if (next < prev || front > next) // we've found where to insert
               {
                  ll.add(N - stepsBack - 1, front); // insert it, but 0-indexed remember
                  break;
               }
               prev = next;
               stepsBack--;
            }
         }
         lastFront = front;
         Iterator<Integer> it = ll.iterator();
         /*while (it.hasNext())
         {
            int next = it.next();
            System.out.println(next);
         }
         System.out.println(stepsBack);
         int stepsForward = N - stepsBack - 1;
         moves.add(stepsForward);
      } while (ll.getFirst() != 1); // in case it starts at one. We can sort in less than a cycle always though
      
      pw.println(moves.size());
      String ret = "";
      for (int move : moves)
         ret += move + " ";
      pw.println(ret.substring(0, ret.length() - 1));*/
      //pw.close();
   }
}