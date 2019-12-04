/*
ID: d3rrick1
LANG: JAVA
PROB: transform
*/
import java.io.*;
import java.util.*;
public class transform
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("transform.in"));
      System.setOut(new PrintStream(new File("transform.out")));
      
      int size = Integer.parseInt(br.readLine());
      char[][] input = new char[size][size];
      char[][] target = new char[size][size];
      for (int row = 0; row < size; row++)
      {
         String str = br.readLine();
         for (int col = 0; col < size; col++)
         {
            input[row][col] = str.charAt(col);
         }
      }
      for (int row = 0; row < size; row++)
      {
         String str = br.readLine();
         for (int col = 0; col < size; col++)
         {
            target[row][col] = str.charAt(col);
         }
      }
      //int value = 10;
      
      if (Arrays.deepEquals(cw(input), target)) {
            System.out.println(1);
        } else if (Arrays.deepEquals(cw(cw(input)), target)) {
            System.out.println(2);
        } else if (Arrays.deepEquals(cw(cw(cw(input))), target)) {
            System.out.println(3);
        } else if (Arrays.deepEquals(reflect(input), target)) {
            System.out.println(4);
        } else if ((Arrays.deepEquals(cw(reflect(input)), target)) ||
                  (Arrays.deepEquals(cw(cw(reflect(input))), target)) ||
                  (Arrays.deepEquals(cw(cw(cw(reflect(input)))), target))) {
            System.out.println(5);
        } else if (Arrays.deepEquals(input, target)) {
            System.out.println(6);
        } else {
            System.out.println(7);
        }
     /* if (cw(grid).equals(goal)) {
            System.out.println(1);
        } else if (cw(cw(grid)).equals(goal)) {
            System.out.println(2);
        } else if (cw(cw(cw(grid))).equals(goal)) {
            System.out.println(3);
        } else if (reflect(grid).equals(goal)) {
            System.out.println(4);
        } else if (reflect(cw(grid)).equals(goal) ||
                  (reflect(cw(cw(grid))).equals(goal)) ||
                  (reflect(cw(cw(cw(grid)))).equals(goal))) {
            System.out.println(5);
        } else if (grid.equals(goal)) {
            System.out.println(6);
        } else {
            System.out.println(7);
        }      System.out.println(value); // none of the transformations worked
      System.exit(0);
   }*/
   
   /*public static boolean equals(char[][], char[][] m)
   {
      for (int row = 0; row < len; row++)
      {
         for (int col = 0; col < len; col++)
         {
            if (matrix[row][col] != m[row][col])
               return false;
         }
      }
      return true;
   }*/
   
   }
  /* public static char get(int r, int c)
   {
      return matrix[r][c];
   }*/
   
   public static char[][] cw(char[][] array) // PERFORM a 90 degree CLOCKWISE rotation of the current matrix
   {
      char[][] array2 = new char[array.length][array.length];
      int shift = array.length - 1;
      for (int row = 0; row < array.length; row++)
      {
         for (int col = 0; col < array.length; col++)
         {
            array2[row][col] = array[col][shift - row];
         }
      }
      return array2;
   } // ooops you only need one rotation method 90degree, not 180/270 since it's repeated
      
    public static char[][] reflect(char[][] array) // reflect Horizontally MAKE VOID since you modify it
   {
      char[][] array2 = new char[array.length][array.length];
      int shift = array.length - 1;
      for (int row = 0; row < array.length; row++)
      {
         for (int col = 0; col < array.length; col++)
         {
            array2[row][shift - col] = array[row][col];
         }
      }
      return array2;
   }
   
    public static void findValue(int x, int y)
   {
      if (x == 0)
      {
         System.out.println(y);
         System.exit(0);
      }
      else
      {
         System.out.println(Math.min(x, y));
         System.exit(0);
      }
   }

}

class Matrix
{
   private char[][] matrix;
   private int len = 0;
   public Matrix(char[][] array)
   {
      matrix = array;
      len = array.length;
   }
   
   public void print()
   {
      for (int row = 0; row < len; row++)
      {
         for (int col = 0; col < len; col++)
         {
            System.out.print(matrix[row][col]);
         }
         System.out.println();
      }
   }
   
   public boolean equals(Matrix m)
   {
      for (int row = 0; row < len; row++)
      {
         for (int col = 0; col < len; col++)
         {
            if (matrix[row][col] != m.get(row, col))
               return false;
         }
      }
      return true;
   }
   
   public char get(int r, int c)
   {
      return matrix[r][c];
   }
   
   public static char[][] cw(char[][] array) // PERFORM a 90 degree CLOCKWISE rotation of the current matrix
   {
      char[][] array2 = new char[array.length][array.length];
      int shift = array.length - 1;
      for (int row = 0; row < array.length; row++)
      {
         for (int col = 0; col < array.length; col++)
         {
            array2[row][col] = array[col][shift - row];
         }
      }
      return array2;
   } // ooops you only need one rotation method 90degree, not 180/270 since it's repeated
      
   public static char[][] reflect(char[][] array) // reflect Horizontally MAKE VOID since you modify it
   {
      char[][] array2 = new char[array.length][array.length];
      int shift = array.length - 1;
      for (int row = 0; row < array.length; row++)
      {
         for (int col = 0; col < array.length; col++)
         {
            array2[row][shift - col] = array[row][col];
         }
      }
      return array2;
   }
   // don't need a combination one, you just use reflect then rotate
}