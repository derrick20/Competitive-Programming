/*
 * @author derrick20
 * YAYHOOO MY GREEDY CLEVERNESS WORKED!!! I WAS STUCK ON THE STUPID TREEMAP NOT
 * TAKING IN DUPLICATE PRICE POINTS!!!!!
 * OK, key insights here:
 * Think about the optimal strategy, and carefully design the components of the
 * program. An interesting observation, I don't have a strong proof but it seems intuitive
 * to me is that when you look at a new cow, it can either be sold for a Maximum Value
 * based on the current availability of buyers, or it can be rented out. However,
 * there are some number of cows remaining that can also be rented out, so we want to ensure
 * that its selling value is less than the WORST rent value. Why the worst? We always look at
 * the worst case when deciding when to switch over, since this means that it is strictly better
 * to just rent this guy out (his milk production is too lackluster to even exceed the worst rental
 * price). By the solution bag principle, since our stack is monotonic, then all future
 * cows will only have a worse position, as they have even more lackluster milk production. After
 * this point, we know for certain everyone must just be immediately rented out.
 *
 * Stupid Nick Wu, trying to brute force using prefix sums. My way worked, the only issue was
 * that multiple prices weren't being accounted for correctly when reading in information about the
 * cow prices!
 */
import java.io.*;
import java.util.*;

public class rentalREDO {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupTesting() {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupTesting();
        setupIO("rental");

        int N = sc.nextInt();
        int M = sc.nextInt();
        int R = sc.nextInt();
        int[] cows = new int[N];
        for (int i = 0; i < N; i++) {
            cows[i] = sc.nextInt();
        }
        Arrays.sort(cows);

        TreeMap<Integer, Integer> quantityOfPrice = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < M; i++) {
            int quantity = sc.nextInt();
            int price = sc.nextInt();
            if (quantityOfPrice.containsKey(price)) {
                quantityOfPrice.put(price, quantityOfPrice.get(price) + quantity);
            }
            else {
                quantityOfPrice.put(price, quantity);
            }
        }

        int[] rental = new int[R];
        for (int i = 0; i < R; i++) {
            rental[i] = sc.nextInt();
        }
        Arrays.sort(rental);

        int cow = N - 1;
//        long[] maxProfit = new long[N + 1];
        // Stores the max value from selling the first i cows;
//        maxProfit[0] = 0;
//        int index = 1;
        long ans = 0;
        while (cow >= 0 && quantityOfPrice.size() > 0) {
            long maxVal = 0;
            int quantity = cows[cow];
            while (quantity > 0 && quantityOfPrice.size() > 0) {
                Map.Entry<Integer, Integer> e = quantityOfPrice.firstEntry();
                int currPrice = e.getKey();
                int currQuantity = e.getValue();
                if (quantity > currQuantity) {
                    // we have enough so buy all their stock, avoid overflow!
                    quantity -= currQuantity;
                    maxVal += (long) currPrice * currQuantity;
                    quantityOfPrice.pollFirstEntry();
                }
                else {
                    // We ran out here, so use what we can and update the map
                    maxVal += (long) quantity * currPrice;
                    quantityOfPrice.put(currPrice, currQuantity - quantity);
                    quantity = 0;
                }
            }
//            System.out.println(maxVal);
//            System.out.println(quantityOfPrice);

//            maxProfit[index] = maxProfit[index - 1] + maxVal;
//            index++;
//            cow--;

            // If there are more cows then rentals it's always favorable to sell them
            // If there aren't enough cows, it might be favorable if they can sell for a lot
            // So if neither is true, break, and rent the rest out

            // If there are fewer cows than rentals, we in the worst case give this
            // cow the cowth worst rental fee and see if it would make more to sell its milk
            if (cow + 1 > rental.length || maxVal > rental[R - 1 - cow]) {
                // Let's use that cow then
                ans += maxVal;
                // After this, the cow will be entirely used up
                cow--;
            }
            else {
                break;
            }
            //*/
        }

//        long[] maxRent = new long[R + 1];
//        maxRent[0] = 0;
//        for (int i = 1; i <= R; i++) {
//            maxRent[i] = maxRent[i - 1] + rental[i - 1];
//        }

//        System.out.println(Arrays.toString(maxProfit));
//        System.out.println(Arrays.toString(maxRent));
//        long best = 0;
//        // try all combinations of distributing cows into rentals and selling
//        for (int i = 0; i <= N; i++) {
//            long left = maxProfit[i];
//            long right = maxRent[R] - maxRent[Math.min(R, i)];
//            // N - i remaining cows, so use the top of those
//            long value = maxProfit[i] + maxRent[R] - maxRent[Math.max(0, R - (N - i))];
//            best = Math.max(best, value);
//        }
        int rentOffer = R - 1;
        while (cow >= 0 && rentOffer >= 0) {
            ans += rental[rentOffer--];
            cow--;
        }
        out.println(ans);
        out.close();
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