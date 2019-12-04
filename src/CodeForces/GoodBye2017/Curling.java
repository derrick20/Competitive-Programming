import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 Curling
 * @derrick20
 * Argh had to look at a hint, my approach was  binary search but didn't account for other constraining balls
 * Shoot, also forgot the equality case and when all of them just don't intersect. Be more careful
 */

public class Curling {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        R = sc.nextDouble();
        x = new double[N];
        y = new double[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextDouble();
            // check all previous circles, see if any are close enough to constrain us
            // before we hit the ground
            y[i] = R; // AHH forgot to set them all as a lower bound of R above!!!!
            for (int j = 0; j < i; j++) {
                double deltaX = (x[i] - x[j]);
                // NEED equality case since tangency counts as sticking!
                if (Math.abs(deltaX) <= 2*R) {
                    // We need to put our y such that it is ABOVE this one at the very least
                    // That is, we may find something that constrains us to be even higher, so keep maxing our height
                    // we solve for the delta Y
                    double deltaY = Math.sqrt(4*R*R - deltaX*deltaX);
                    // See if being ABOVE y[j] by that much is the most constraining yet
                    // Notice the other solution would be subtracting deltaY
                    y[i] = Math.max(y[i], y[j] + deltaY);
                }
            }
        }

        for (double yval : y) {
            System.out.print(yval + " ");
        }
    }
    static double[] x;
    static double[] y;
    static double R;

    /*// Using the index, we can get 3 parameters, the prev x and y, and this x
    // We are searching for this point's y such that distance is minimized, without circles intersecting
    static double binarySearch(int index, double ylo, double yhi) {
        double x1 = x[index - 1];
        double y1 = y[index - 1];
        double x2 = x[index];

        double d = Math.abs(dist(x1, y1, x2, yhi) - 2 * R); // just upper bound it
        while (true) {
            double ypivot = ylo + (yhi - ylo) / 2;
            double newD = Math.abs(dist(x1, y1, x2, ypivot) - 2 * R);
            if (newD < d) {
                // this is a good direction, so shrink yhi
                yhi = ypivot;
            }
            else {
                ylo = ypivot; // we went too far, so move y up a bit
            }
            if (Math.abs(newD - d) <= 1e-6) // If we've stopped improving to some error, then done
                return ypivot;
            else { // else, keep trying
                d = newD;
            }
        }
    }*/

    static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}

