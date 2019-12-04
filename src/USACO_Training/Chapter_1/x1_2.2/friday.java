import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

/*
ID: d3rrickl
LANG: JAVA
PROB: friday
 */

public class friday {
	public static void main(String[] args) throws Exception {
		int today = 1;
		int[] days = new int[7];
		int months[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		Scanner infile = new Scanner(new File("friday.in"));
		int years = 1900 + infile.nextInt() - 1;
		infile.close();
		
		for (int year = 1900; year <= years; year++) {
			months[1] = (isLeap(year) ? 29 : 28);
			for (int month = 0; month < 12; month ++) {
				for (int day = 1; day <= months[month]; day++) {
					if (day == 13) {
						days[today % 7]++;
					}
					today++;
				}
			}
		}
		PrintStream outfile = new PrintStream("friday.out");
		System.setOut(outfile);
		System.out.println(days[6] + " " + days[0] + " " + days[1] + " " + days[2] + " " + days[3] + " " + days[4] + " " + days[5]);
	}
	
	public static boolean isLeap(int year) {
		return !(year % 100 == 0 && year % 400 != 0 || year % 4 != 0);
	}
}