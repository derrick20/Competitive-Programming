import java.util.*;
import java.io.*;
public class cowpatibility
{ // WHY IS THIS OUT OF TIME!!!
   public static void main(String[] args) throws Exception
   {       
      BufferedReader br = new BufferedReader(new FileReader("cowpatibility.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
      long N = Integer.parseInt(br.readLine());
      Map<HashSet<Integer>, Integer> flavorMap= new HashMap<HashSet<Integer>, Integer>(); // Maps combos of flavors to how many cows have that combo
      long currTime = System.nanoTime();
      for (int i = 0; i < N; i++)
      {
         // read in the line of information
         StringTokenizer st = new StringTokenizer(br.readLine());
         int[] flavors = new int[5];
         for (int j = 0; j < 5; j++)
         {
            int flavor = Integer.parseInt(st.nextToken());
            flavors[j] = flavor;
         }
         // create all subsets of that flavor profile

         for (int j = 1; j < 32; j++)
         {
            HashSet<Integer> flavorSet = new HashSet<Integer>(); // for each of the combos (1, 2,... 12, 13.. 123, 124...) have a mapping
            for (int k = 0; k < 5; k++)
            {
               if ((j & (1 << k)) > 0) // there's a match between say 110 and 10 and 100, so those elements will be added
               {
                  flavorSet.add(flavors[k]); // that flavor is one of the subset
               }
            }
            if (flavorMap.containsKey(flavorSet)) // classic map manipulation
            {
               flavorMap.put(flavorSet, flavorMap.get(flavorSet)+1);
            }
            else
            {
               flavorMap.put(flavorSet, 1); // start it at 0, once we see a match, add 1 repeatedly
            }
         }
      }

      long[] values = {-1,0,0,0,0,0}; // 1-index, store the singletons, doublets, triplets, etc.

      for (HashMap.Entry<HashSet<Integer>, Integer> entry : flavorMap.entrySet())
      {
         if (entry.getValue() > 1) // we want pairs, so individuals are useless
         {
            int groupSize = entry.getKey().size();
            int cows = entry.getValue();
            values[groupSize] += cows*(cows-1)/2; // within those cows, how many pairs there are
         }
      }
      long total = N*(N-1)/2;
      int[] PIE = {0, 1, -1, 1, -1, 1};

      long matches = 0;
      for (int i = 1; i <= 5; i++)
      {
         matches += PIE[i]*values[i]; // add up the number of pairings within each possible group of subsets
         //System.out.println(matches);
      }
      System.out.println(total);
      for (long v : values)
         System.out.println(v);
      //System.out.println(flavorMap); //*/
      pw.println(total - matches);
      System.out.println((System.nanoTime() - currTime)/1.0e9);
      br.close();
      pw.close();

      /*Set<Integer> IDset = new HashSet<Integer>();
      ArrayList<ArrayList<Integer>> cowToID = new ArrayList<ArrayList<Integer>>();

      for (int i = 0; i < N; i++)
      {
         StringTokenizer st = new StringTokenizer(br.readLine());
         cowToID.add(i, new ArrayList<Integer>()); // populate with arraylists
         for (int j = 0; j < 5; j++)
         {
            int ID = Integer.parseInt(st.nextToken());
            cowToID.get(i).add(ID); // Cow 0 -> 1,2,3,4,5
            IDset.add(ID); // unique IDs
         }
      }
      
      
      for (int ID : IDset)
      {
         ArrayList<Integer> cows = new ArrayList<Integer>(); // array list of cows that have a certain ID
         for (int cow = 0; cow < N; cow++)
         {
            if (cowToID.get(cow).contains(ID)) // fine because only 5 values in array
            {
               cows.add(cow);
            }
         }
         IDtoCow.put(ID, cows); // we created a map from id to cows with that id
      }
      
      // Now, manually go through each id grouping, see if common and sum the number of compatible, then subtract from total
      int[][] compat = new int[N][N]; // cow grid
      int count = 0;
      for (int ID : IDset)
      {
         ArrayList<Integer> compatGroup = IDtoCow.get(ID);
         for (int i = 0; i < compatGroup.size() - 1; i++)
         {
            for (int j = i + 1; j < compatGroup.size(); j++)
            {
               int first = compatGroup.get(i);
               int second = compatGroup.get(j);
               
               if (!(compat[second][first] == 1 || compat[first][second] == 1))
               {
                  compat[first][second] = 1;
                  compat[second][first] = 1;
                  count += 1;
                  // pw.println(first + " " + second + " are compatible");
               }
               
            }
         }
      }*/
      //pw.println(total-count);
      //pw.println(count); //*/
      
   }
}

/*
import java.util.*;
import java.io.*;
public class dining
{    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader("dining.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));

    }
}
 */