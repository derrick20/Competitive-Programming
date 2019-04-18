/*
ID: d3rrick1
LANG: JAVA
PROG: gift1
*/
// REMEMBER THAT THAT'S HOW YOU SUBMIT IT!!!! ^^^^
import java.util.*;
import java.io.*;

public class gift1 {
	private static Scanner infile;

	public static void main(String[] args) throws Exception
	{
		infile = new Scanner(new File("gift1.in"));
		int nums = infile.nextInt();
		
		String[] memberNames = new String [nums]; // oh wait we might need to create a type of object for each member? look at this later
		int[] memberMoney = new int[nums];
		
		for (int i = 0; i < nums; i++)
		{
			memberNames[i] = infile.next();
		}
		
		for (int i = 0; i < nums; i++) 
		{
			String current = infile.next();
			int currentMember = 0;
			for (int k = 0; k < nums; k++) { // searches for the matching recipient name
				if (memberNames[k].equals(current))
					currentMember = k;
			}
			int currentMoney = infile.nextInt();
			int numRecipients = infile.nextInt();
			int finalMoney = 0;
			
			int[] recipients = new int[numRecipients];
			for (int j = 0; j < numRecipients; j++) // Fills the recipients array with the positions of the recipients
			{
				String temp = infile.next();
				for (int k = 0; k < nums; k++) { // searches for the matching recipient name, stores it in the recipients array
					if (memberNames[k].equals(temp))
						recipients[j] = k;
				}
				memberMoney[recipients[j]] += currentMoney/numRecipients;
				finalMoney = currentMoney%numRecipients - currentMoney;
			}
			memberMoney[currentMember] += finalMoney;
		}
		PrintStream outfile = new PrintStream("gift1.out");
		System.setOut(outfile);
		for (int i = 0; i < nums; i++)
			System.out.println(memberNames[i] + " " + memberMoney[i]);
	}
}
/* not able to make another class i think
public class Member {
	private String myName;
	private int myMoney;
	
	public Member()
	{
		myName = null;
		myMoney = 0;
	}
	
	public Member(String s, int i)
	{
		myName = s;
		myMoney = i;
	}
	
	public void setMoney(int i)
	{
		myMoney = i;
	}
	
	public int getMoney()
	{
		return myMoney;
	}
}
*/
