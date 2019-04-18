import java.io.File;
import java.util.Scanner;

/*
ID: d3rrickl
LANG: JAVA
PROB: friday
 */

public class fridayfail{
	public static void main(String[] args) throws Exception {
		int[] days = new int[7];
		int today = 1;
		Scanner infile = new Scanner(new File("friday.in"));
		int years = 1900 + infile.nextInt() - 1;
		infile.close();
		
		for (int i = 1900; i <= years; i++) {
			if (years % 100 == 0 && years % 400 != 0 || years % 4 != 0) {
				for (int j = 1; j <= 365; j++) {
					today++;
					System.out.println(j + " " + days[6] + " " + days[0] + " " + days[1] + " " + days[2] + " " + days[3] + " " + days[4] + " " + days[5]);
					if (j <= 31) {
						add(days, j, today); // -0 Jan
					}
					else if (j - 31 <= 28) {
						add(days, j - 31, today); // - 31 Feb
					}
					else if (j - 59 <= 31) {
						add(days, j - 59, today); // -31-28 Mar
					}
					else if (j - 90 <= 30) {
						add(days, j - 90, today); //-31-28-31 Apr
					}
					else if (j - 120 <= 31) {
						add(days, j - 120, today); // - 31-28-31-30 May
					}
					else if (j - 151 <= 30) {
						add(days, j - 151, today); // - 31-28-31-30-31 June
					}
					else if (j - 181 <= 31) {
						add(days, j - 181, today); // - 31-28-31-30-31-30 July
					}
					else if (j - 212 <= 31) {
						add(days, j - 212, today); // - 31-28-31-30-31-30-31 August
					}
					else if (j - 243 <= 30) {
						add(days, j - 243, today); // - 31-28-31-30-31-30-31-31 September
					}
					else if (j - 273 <= 31) {
						add(days, j - 273, today); // - 31-28-31-30-31-30-31-31-30 October
					}
					else if (j - 304 <= 30) {
						add(days, j - 304, today); // - 31-28-31-30-31-30-31-31-30-31 November
					}
					else if (j - 334 <= 31) {
						add(days, j - 334, today); // - 31-28-31-30-31-30-31-31-30-31-30 December
					}
					else {}
				}
			}
			else {
				for (int j = 1; j <= 366; j++) {
					today++;
					if (j <= 31) {
						add(days, j, today); // -0 Jan
					}
					else if (j - 31 <= 29) {
						add(days, j - 31, today); // - 31 Feb
					}
					else if (j - 60 <= 31) {
						add(days, j - 60, today); // -31-29 Mar
					}
					else if (j - 91 <= 30) {
						add(days, j - 91, today); //-31-29-31 Apr
					}
					else if (j - 121 <= 31) {
						add(days, j - 121, today); // - 31-29-31-30 May
					}
					else if (j - 152 <= 30) {
						add(days, j - 152, today); // - 31-29-31-30-31 June
					}
					else if (j - 182 <= 31) {
						add(days, j - 182, today); // - 31-29-31-30-31-30 July
					}
					else if (j - 213 <= 31) {
						add(days, j - 213, today); // - 31-29-31-30-31-30-31 August
					}
					else if (j - 244 <= 30) {
						add(days, j - 244, today); // - 31-29-31-30-31-30-31-31 September
					}
					else if (j - 274 <= 31) {
						add(days, j - 274, today); // - 31-29-31-30-31-30-31-31-30 October
					}
					else if (j - 305 <= 30) {
						add(days, j - 305, today); // - 31-29-31-30-31-30-31-31-30-31 November
					}
					else if (j - 335 <= 31) {
						add(days, j - 335, today); // - 31-29-31-30-31-30-31-31-30-31-30 December
					}
					else {}
					System.out.println(j + " " + days[6] + " " + days[0] + " " + days[1] + " " + days[2] + " " + days[3] + " " + days[4] + " " + days[5]);
				}
			}
		}
		System.out.println(days[6] + " " + days[0] + " " + days[1] + " " + days[2] + " " + days[3] + " " + days[4] + " " + days[5]);
	}
	
	public static void add(int[] days, int j, int today) {
		if (j == 13) {
			switch (today % 7) {
				case 1:
					days[1] += 1;
				case 2:
					days[2] += 1;
				case 3:
					days[3] += 1;
				case 4:
					days[4] += 1;
				case 5:
					days[5] += 1;
				case 6:
					days[6] += 1;
				case 0:
					days[0] += 1;
			}
		}
	}
}
