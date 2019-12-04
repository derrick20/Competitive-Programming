/*
ID: d3rrickl
LANG: JAVA
PROG: castle
 */
import java.io.*;
import java.util.*;
public class castle
{
   public static node[][] nodes; // NEXT TIME USING 1-INDEXED LOL
   public static boolean[][] visited;
   public static ArrayList<Integer> coloredRooms = new ArrayList<Integer>();
   public static int[] dx = {0, -1, 0, 1};
   public static int[] dy = {-1, 0, 1, 0}; // west, north, east, then south
   public static int size, M, N;
   
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("castle.in"));
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
      
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      nodes = new node[M][N];
      visited = new boolean[M][N];
      
      for (int i = 0; i < M; i++)
      {
         st = new StringTokenizer(br.readLine());
         for (int j = 0; j < N; j++)
         {
            nodes[i][j] = new node(i, j, Integer.parseInt(st.nextToken()));
         }
      }
      
      int color = 0;
      for (int i = 0; i < M; i++)
      {
         for (int j = 0; j < N; j++)
         {
            if (!visited[i][j])
            { 
               size = 0;
               dfs(i, j, color); // color corresponds to the index in coloredRooms
               coloredRooms.add(size);
               color++;
               //System.out.println(color);
            }
         }
      }
      
      int maxArea = -1;
      int rPos = -1;
      int cPos = -1;
      String direction = "";
      for (int col = 0; col <= N - 1; col++) // check from the first column, and see the walls in all spots, since West is prioritized
      { 
         for (int row = M - 1; row >= 0; row--) // this increments down by 1 so eventually we use the northmost as the second determining Factor
         {
            int combinedArea;
            if (row >= 1)// Next we have to check the north wall before the east wall Cells, being careful about bounds            
            {
               int southColor = nodes[row][col].getColor();
               int northColor = nodes[row - 1][col].getColor();
               combinedArea = coloredRooms.get(southColor) + coloredRooms.get(northColor);
               if (southColor != northColor && combinedArea > maxArea) // north south is different
               {
                  maxArea = combinedArea;
                  rPos = row;
                  cPos = col;
                  direction = "N";
               }
            }
            if (col <= N - 2)
            {
               int westColor = nodes[row][col].getColor(); // notice we are basing off of south and west!!
               int eastColor = nodes[row][col + 1].getColor();
               combinedArea = coloredRooms.get(westColor) + coloredRooms.get(eastColor);
               if (westColor != eastColor && combinedArea > maxArea) // north south is different
               {
                  maxArea = combinedArea;
                  rPos = row;
                  cPos = col;
                  direction = "E";
               }
            }
         }
      }
      pw.println(coloredRooms.size());
      pw.println(Collections.max(coloredRooms));
      pw.println(maxArea);
      rPos++;
      cPos++;
      pw.println(rPos + " " + cPos + " " + direction);
      pw.close();
   }       
   
   public static void dfs(int r, int c, int color)
   {
      if (r < 0 || r >= M || c < 0 || c >= N || visited[r][c])
         return;
      else
      {
         visited[r][c] = true;
         nodes[r][c].setColor(color);
         size++;
         int direction = nodes[r][c].getWalls();
         // System.out.println(r + " " + c);
         for (int i = 0; i < 4; i++)
         {
            if (direction % 2 == 0)
            {
               //System.out.println(direction);
               dfs(r + dx[i], c + dy[i], color);
            } 
            direction /= 2; // each time we right bitwise shift the direction code
         }
           //? ? visited[r][c] = false;
      }
   }
}

class node
{
   private int walls;
   private int color;
   private int row, col;
      
   public node(int r, int c, int w)
   {
      row = r;
      col = c;
      walls = w;
   }
   
   public int getColor()
   {
      return color;
   }
      
   public void setColor(int c)
   {
      color = c;
   }
      
   public int getWalls()
   {
      return walls;
   }
   
   public String toString()
   {
      return color + " ";
   }
}