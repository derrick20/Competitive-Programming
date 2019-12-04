import java.io.*;
import java.io.*;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class TrickOrTreat {

    public static void main(String[] args) throws IOException{
//		BufferedReader br = new BufferedReader(new FileReader("/Users/derrick/IntelliJProjects/src/PClassic/TrickOrTreatIN.txt"));
        BufferedReader br = new BufferedReader(new FileReader("TrickOrTreatIN.txt"));

        //BufferedWriter bw = new BufferedWriter(new FileWriter("files/kgout.txt"));
        while (br.ready()) {
            String s = br.readLine();
            if (!s.trim().isEmpty()) {
                String[] data = s.split(" ");
                int numWizards = Integer.parseInt(data[0]);
                data = br.readLine().split(" ");
                int kL = Integer.parseInt(data[0]);
                int kR = Integer.parseInt(data[1]);
                int[][] candies = new int[numWizards][2];
                for (int i = 0; i < numWizards; i++) {
                    data = br.readLine().split(" ");
                    candies[i][0] = Integer.parseInt(data[0]);
                    candies[i][1] = Integer.parseInt(data[1]);
                }
                System.out.println(trickOrTreat(kL,kR,candies));
                //bw.write(kingsGameAns(kL,kR,candies)+"\n");
            }
        }
        br.close();
        //bw.close();
    }

    //DO NOT MODIFY ANY CODE ABOVE

    public static String trickOrTreat(int kL, int kR, int[][] candies) {
        ArrayList<Pair> sorted = new ArrayList<>();
        for (int i = 0; i < candies.length; i++) {
            sorted.add(new Pair(candies[i][0], candies[i][1]));
        }
//        Collections.sort(sorted);
        sortX(sorted);

        BigInteger prod = new BigInteger("" +  kL);
        BigInteger worst = BigInteger.ZERO;
        for (int i = 0; i < candies.length; i++) {
            BigInteger alt = prod.divide(new BigInteger("" + candies[i][1]));
            if (worst.compareTo(alt) < 0) {
                worst = alt;
            }
            prod = prod.multiply(new BigInteger("" + candies[i][0]));
        }
        return worst.toString();
    }

    static void sortX(ArrayList<Pair> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < arr.size()-1; j++) {
                Pair p1 = arr.get(j);
                Pair p2 = arr.get(j + 1);
                long maxA = Math.max(p2.b, p1.a * p1.b);
                long maxB = Math.max(p1.b, p2.a * p2.b);
                if (!(maxA < maxB)) {
                    Pair temp = p1;
                    arr.set(j, p2);
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    static class Pair implements Comparable<Pair> {
        long a, b;
        public Pair(long myA, long myB) {
            a = myA; b = myB;
        }
        public int compareTo(Pair p2) {
            long maxA = Math.max(p2.b, a * b);
            long maxB = Math.max(b, p2.a * p2.b);
            return (maxA - maxB) < 0 ? -1 : 1;
        }
    }

}
