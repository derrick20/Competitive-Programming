/*
ID: d3rrick1
LANG: JAVA
TASK: ride
 */

import java.io.*;
import java.util.Scanner;

public class ride {
	public static void main(String[] args) throws Exception
	{
		Scanner infile = new Scanner(new File("ride.in"));
		
		String comet = infile.nextLine();
		String group = infile.nextLine();
		infile.close();
		
		int cProduct = 1;
		int gProduct = 1;
		
		for (int i = 0; i < comet.length(); i++)
		{
			cProduct *= comet.charAt(i) - 64;
		}
		
		for (int i = 0; i < group.length(); i++)
		{
			gProduct *= group.charAt(i) - 64;
		}
		PrintStream outfile = new PrintStream("ride.out");
		System.setOut(outfile);
		if (cProduct % 47 == gProduct % 47)
			System.out.println("GO");
		else
			System.out.println("STAY");
	}
}
