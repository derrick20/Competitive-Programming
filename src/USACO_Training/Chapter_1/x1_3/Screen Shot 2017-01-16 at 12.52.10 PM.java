/*
*ID: d3rrick1
*LANG: JAVA
*PROB: Milking Cows
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
      char[][] array = new char[size][size];
      char[][] array2 = new char[size][size];
      for (int row = 0; row < size; row++)
      {
         String str = br.readLine();
         for (int col = 0; col < size; col++)
         {
            array[row][col] = str.charAt(col);
         }
      }
      for (int row = 0; row < size; row++)
      {
         String str = br.readLine();
         for (int col = 0; col < size; col++)
         {
            array2[row][col] = str.charAt(col);
         }
      }
      
      Matrix grid = new Matrix(array); 
      Matrix goal = new Matrix(array2);
      
      int value = 0;
      grid.rotate(); // 90
      if (grid.equals(goal));
      {
         value = findValue(value, 1); /// something's wrong with the find Value part
      }   
      grid.rotate(); // 180
      if (grid.equals(goal));
      {
         value = findValue(value, 2);
      }
      grid.rotate(); // 270
      if (grid.equals(goal));
      {
         value = findValue(value, 3);
      } 
      grid.rotate();
      grid.reflect(); // back to normal, but reflected
      if (grid.equals(goal));
      {
         value = findValue(value, 4);
      } 
      grid.rotate();
      if (grid.equals(goal)); // 90
      {
         value = findValue(value, 5);
      } 
      grid.rotate(); // 180
      if (grid.equals(goal));
      {
         value = findValue(value, 5);
      }  
      grid.rotate(); // 270
      if (grid.equals(goal));
      {
         value = findValue(value, 5);
      }  
      grid.rotate(); // back to original
      if (grid.equals(goal)); // we wanted to find the minimum transformation value so we put this after all the combination ones
      {
         value = findValue(value, 6);
      } 
      if (!grid.equals(goal))
      {
         value = findValue(value, 7);
      }
      System.out.println(value); // none of the transformations worked
      System.exit(0);
   }
   
   public static int findValue(int x, int y)
   {
      if (x == 0)
         return y;
      else
         return Math.min(x, y);
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
   
   public void rotate() // PERFORM a 90 degree CLOCKWISE rotation of the current matrix
   {
      char[][] array = new char[len][len];
      int shift = len - 1;
      for (int row = 0; row < len; row++)
      {
         for (int col = 0; col < len; col++)
         {
            array[row][col] = matrix[col][shift - row];
         }
      }
      matrix = array;
   } // ooops you only need one rotation method 90degree, not 180/270 since it's repeated
      
   public void reflect() // reflect Horizontally MAKE VOID since you modify it
   {
      char[][] array = new char[len][len];
      int shift = len - 1;
      for (int row = 0; row < len; row++)
      {
         for (int col = 0; col < len; col++)
         {
            array[row][shift - col] = matrix[row][col];
         }
      }
      matrix = array;
   }
   // don't need a combination one, you just use reflect then rotate
}