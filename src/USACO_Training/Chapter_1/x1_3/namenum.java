/*
ID: d3rrick1
LANG: JAVA
PROB: namenum
*/
import java.io.*;
import java.util.*;
public class namenum
{
   public static void main(String[] args) throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader("dict.txt"));
      BufferedReader br2 = new BufferedReader(new FileReader("namenum.in"));
      System.setOut(new PrintStream(new File("namenum.out")));
      
      String[] list = new String[5000];
      int i = 0;
      String str;
      while ((str = br.readLine())!=null && str.length()!=0)
      {
         list[i] = str;
         i++;
      }
      
      long code = Long.parseLong(br2.readLine());
      String solutions = "";
      i = 0;
      while (list[i] != null)
      {
         //if (Math.pow(10, list[j].length()) <= code && Math.pow(10, list[j].length() + 1) >= code)
         if (encode(list[i]) == code)
         {
            solutions += list[i] + "\n";
         }
         i++;
      }
      if (solutions.equals(""))
      {
         solutions = "NONE" + "\n";
      }
      System.out.print(solutions);
      System.exit(0);
   }
   public static long encode(String str)
   {
      String out = "";
      char[] array = str.toCharArray();
      for (char c : array)
      {
         switch (c)
         {
            case 'A':
               out += "2";
               break;
            case 'B':
               out += "2";
               break;
            case 'C':
               out += "2";
               break;
            case 'D':
               out += "3";
               break;
            case 'E':
               out += "3";
               break;
            case 'F':
               out += "3";
               break;
            case 'G':
               out += "4";
               break;
            case 'H':
               out += "4";
               break;
            case 'I':
               out += "4";
               break;
            case 'J':
               out += "5";
               break;
            case 'K':
               out += "5";
               break;
            case 'L':
               out += "5";
               break;
            case 'M':
               out += "6";
               break;
            case 'N':
               out += "6";
               break;
            case 'O':
               out += "6";
               break;
            case 'P':
               out += "7";
               break;
            case 'R':
               out += "7";
               break;
            case 'S':
               out += "7";
               break;
            case 'T':
               out += "8";
               break;
            case 'U':
               out += "8";
               break;
            case 'V':
               out += "8";
               break;
            case 'W':
               out += "9";
               break;
            case 'X':
               out += "9";
               break;
            case 'Y':
               out += "9";
               break;
         }
      }   
      return Long.parseLong(out);
   }
}