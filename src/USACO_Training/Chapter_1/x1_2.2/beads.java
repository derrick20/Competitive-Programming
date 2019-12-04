/*
ID: d3rrickl
LANG: JAVA
PROG: beads
 */
import java.io.*;
import java.util.*;

public class beads { // hmm this it too tricky work on later??
	private static BufferedReader bf;

	public static void main(String[] args) throws Exception	{
		bf = new BufferedReader(new FileReader("beads.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
		
		int nums = Integer.parseInt(bf.readLine());
		String str = bf.readLine();
      String s = str + str;
		
      ArrayList<Integer> list = new ArrayList<Integer>();
      for (int i = 0; i < nums; i++) {
         char color = s.charAt(i); // tracks current color
         if (color == 'w') {} // if white do nothing, add it to length later
         else { // either does red or blue, but it doesn't specify, so we do it until we've changed color once and finished that length
            int j = i;
            int count = 0;
            while (count < nums && (s.charAt(j) == color || s.charAt(j) == 'w')) {
               count++;
               j++;
            }
            color = s.charAt(j);
            while (count < nums && (s.charAt(j) == color || s.charAt(j) == 'w')) {
               count++;
               j++;
            }
            int d = i;
            if (d > 0)
               d--;
            while (count < nums && s.charAt(d) == 'w' && d > 0) {
               count++;
               if (d >= 1)
                  d--;
            }
            list.add(count);
         }
      }
      /*
		for (int i = 0; i < nums; i++) {
			int count = 0;
			if (isRed(s, i)) {
				int x = i + 1;
				count++;
				while (isRed(s, x)) {
					count++;
					x++;
				}
				while (isBlue(s, x)) {
					count++;
					x++;
				}
				if (count > max) {
					max = count;
				}
			}
         int j = i - 1;
         while (j > 0) {
            if (
         }
		}
		
		for (int i = 0; i < nums; i++) {
			int count = 0;
			if (isBlue(s, i)) {
				int x = i + 1;
				count++;
				while (isBlue(s, x)) {
					count++;
					x++;
				}
				while (isRed(s, x)) {
					count++;
					x++;
				}
				if (count > max) {
					max = count;
				}
			}
		}*/
      
		if ((!s.contains("b") || !s.contains("r")))
			list.add(str.length());
		int max = Collections.max(list);
      
		out.println(max + "");
		out.close();
	}
	
	/*public static Boolean isBlue(String s, int pos) {
		return s.charAt(pos) == 'r' || s.charAt(pos) == 'w';
	}
	
	public static Boolean isRed(String s, int pos) {
		return s.charAt(pos) == 'r' || s.charAt(pos) == 'w';
	}*/
}
