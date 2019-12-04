/**
 * Derrick Liang
 * September 5, 2019
 * Zacharias Period 3
 */
import java.io.*;
import java.util.*;

public class Triangles {

    static int size = 50;
    static boolean[][] grid;

    public static void main(String args[]) throws Exception {
        grid = new boolean[size][size];
//        shiftedBresenham(0, 10, 1, 5);
//        shiftedBresenham(0, 0, 0, 10);
//        shiftedBresenham(0, 0, 10, 10);
        // todo: Make line work for negative slope
        CircleBresenham(23, 25, 7);
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[j][i] ? "@ " : "  ");
            }
            System.out.println();
        }
    }

    public static void CircleBresenham(int xC, int yC, int r) {
        /*
        // Somehow, this code does something slightly different for small cases,
        // (Check size 10) Rounding is weird
        int x = 0;
        int y = r;
        int p = 1-r;//(int) Math.round(.5 - r); // This is just a solid approximation of the correct value

        while (x <= y) {
            System.out.println(x + " " + y);
            paintPoint(xC, yC, x, y);
            if (p < 0) {
                p += 2*x+1;
            } else {
                y--;
                p += 2*(x-y)+1;
            }
            x++;
        } // */
        //*
        int xRel = 0;
        int yRel = r;
        // The discriminant function represents whether we're inside or outside
        // Negative if inside, positive if out
        // F = x^2 + y^2 - r^2
//        paintPoint(xC, yC, xRel, yRel);
        int F = 1-r;//(int) Math.round(1.25 - r);
        // Go until y = x;
//        xRel++;
        while (xRel <= yRel) {
//            System.out.println(xRel + " " + yRel);
            paintPoint(xC, yC, xRel, yRel);
            // This is the result of incrementing xRel on the discriminant
            // function
            // Negative, so continue
            // x+1 -> x+2, so we get a 2x + 3 = 2(x+1) + 1 change
            int changeFromX = 2 * (++xRel) + 1;
            if (F < 0) {
                F += changeFromX;
            }
            else {
                // We went outside the circle, so shift y down by one
                // this thus lowers the discriminant function down again
                // In this case, y -> y-1
                // So, (y-1/2)^2 -> (y-3/2)^2
                // This difference is y^2 - y + 1/4 -> y^2 - 3y + 9/4
                // This is just -2y + 2, or -2(y-1)
                // Combining these, we get the total change
                int changeFromY = -2 * (--yRel);
                F += changeFromX + changeFromY;
            }

        } // */
    }

    public static void paintPoint(int xC, int yC, int xRel, int yRel) {
        int[] signX = {1, 1, -1, -1};
        int[] signY = {1, -1, 1, -1};
        for (int quadrant = 0; quadrant < 4; quadrant++) {
            int xPos = xC + signX[quadrant] * xRel;
            int yPos = yC + signY[quadrant] * yRel;
            // Paint both sides of line y = x!
            grid[xPos][yPos] = true;
            grid[yPos][xPos] = true;
        }
    }

    /**
     * General idea:
     * We have a line y = dy/dx * x (we'll just set xi, yi as the new origin)
     * Let's start at (1/2, 1/2) to  serve as the initial point
     * Let F be a function which represents our point being below the true line if positive
     * and above the true line if negative.
     * F = dy/dx * x - currY
     * F_0 = dy/dx * 1/2 - 1/2
     * Now, we will scale all future operations by 2 * dx
     * F_0' = dy - dx.
     * Now, each time we step over 1 for x, this differential should increase by dy / dx,
     * or scaled up as 2 * dy.
     * If this brings us above 1/2, or scaled as dx, then the center of the current x's
     * pixel is below the line by too much, so we need to increment the y of the sweeping pixel
     *
     * Now, this only works because we know each increment of x can only increase the y by 1 or 0.
     * Thus, the driver acess will always catch any changes in the correct y to paint in the grid.
     * However, if the slope is greater than 1, we swap the axis and do the same thing with y.
     * F = dx/dy * y - currX
     * F_0 = dx/dy * 1/2 - 1/2
     * F_0' = dx - dy
     * This changes by 2 * dx each step, and whenever the gap between the line and the
     * current x is greater than 1/2, or dy, we step it up.
     *
     * We still must deal with negative slopes.
     */
    static void shiftedBresenham(int xi, int yi, int xf, int yf) {
        // swap so that x is increasing
        if (xi > xf) {
            int temp = xf;
            xf = xi;
            xi = temp;
            temp = yf;
            yf = yi;
            yi = temp;
        }
        int dx = xf - xi; // Guaranteed positive
        int dy = yf - yi; // May be negative

        int g = Math.abs(gcd(dx, dy)); // Divide by the positive part of each
        dx /= g;
        dy /= g;
        // Slope is less than 1
        if (Math.abs(dy) <= Math.abs(dx)) {
            int scaledOffset = dy - dx;
            int y = yi;
            for (int x = xi; x < xf; x++) {
                grid[x][y] = true;
                scaledOffset += 2 * dy;
                // If the delta exceeds 1/2, then move up the y.
                if (scaledOffset > dx) {
                    y++;
                    scaledOffset -= 2 * dx;
                }
                else if (scaledOffset < -dx) {
                    // For the negative version, everything within should be negated!
                    y--;
                    scaledOffset += 2 * dx;
                }
            }
        }
        else {
            // Flip every x for a y
            int scaledOffset = dx - dy;
            int x = xi;
            for (int y = yi; y < yf; y++) {
                grid[x][y] = true;
                scaledOffset += 2 * dx;
                if (scaledOffset > dy) {
                    x++;
                    scaledOffset -= 2 * dy;
                }
                else if (scaledOffset < -dy) {
                    x--;
                    scaledOffset += 2 * dy;
                }
            }
        }
    }

    static int gcd(int a, int b) {
        // a must be bigger than b. If it's not, this operation will just swap
        // them with no decrease!
        return b == 0 ? a : gcd(b, a % b);
    }
}