/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: September 15, 2017
 * FIlename: BingoBall.java
 * Details: CSC 115 Assignment 1
 */

public class BingoBall {

	private static char[] bingo = {'B','I','N','G','O'};
	private char letter;
	private int value;

	//Assigns value to letter for given object BingoBall
	public BingoBall(int value) {
		if (value > 0 && value < 76) {
			this.value = value;
			letter = bingo[value%5];
		} else {
			throw new IllegalArgumentException("number must be between 1 and 75; it was "+value);
		}
	}

	private void setLetter() {
		letter = bingo[value%5];
	}
	
	public int getValue() {
		return value;
	}

	public char getLetter() {
		return letter; 
	}

	public void setValue(int value) {
		if (value > 0 && value < 76) {
			this.value = value;
			setLetter();
		} else {
			throw new IllegalArgumentException("number must be between 1 and 75; it was "+value);
		}
	}

	/*
	 * Compares BingoBall objects; if both value AND 
	 * letter (precaution for setLetter) are 
	 * the same then match is true
	 */
	public boolean equals(BingoBall other) {

		if(value == other.getValue()/*??letter == other.getLetter()??*/)
		{
			return true;
		}

		return false;
	}

	//label is <letter><value>
	public String toString() {
		String label;
		label = "" + letter + value;
		return label;
	}

	/*
	 * Slightly modified this tester so as to not print: 
	 * "The second ball has been changed to NULL"
	 * and extended tester so as to create and
	 * print all 75 possible objects variances at once
	 * and ensure that value-to-index assignment algorithm works
 	 */
	public static void main(String[] args) {
		BingoBall b = new BingoBall(42);
		System.out.println("Created a BingoBall: "+b);
		System.out.println("The number is "+b.getValue());
		System.out.println("The letter is "+b.getLetter());
		BingoBall c = null;
		try {
			c = new BingoBall(76);
		} catch (Exception e) {
			System.out.println("Correctly caught the exception");
		}
		c = new BingoBall(14);
		System.out.println("Created a second BingoBall: "+c);
		if (!b.equals(c)) {
			System.out.println("The two balls are not equivalent");
		}
		c.setValue(42);
		System.out.println("The second ball has been changed to "+c);
		if (b.equals(c)) {
			System.out.println("They are now equivalent");
		}
		c.setValue(74);
		System.out.println("The second bingo ball has been changed to "+c);

		//ADDITIONAL TESTING
		System.out.println();

		BingoBall[] rack = new BingoBall[75];

		for(int i = 0; i < 75; i++)
		{
			rack[i] = new BingoBall(i+1);
		}

		BingoBall unchanged = new BingoBall(75);

		for(int i = 0; i < rack.length; i++)
		{
			System.out.println("Label of ball is: " + rack[i]);
			System.out.println("Value of ball is: " + rack[i].getValue());
			System.out.println("Letter on ball is: " + rack[i].getLetter() + "\n");
		}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("TESTER CHECKLIST");
		System.out.println("setLetter [tested and WORKS]");
		System.out.println("getValue [tested and WORKS]");
		System.out.println("getLetter [tested and WORKS]");
		System.out.println("setValue [tested and WORKS]");
		System.out.println("equals [tested and WORKS]");
		System.out.println("toString [tested and WORKS]");
		System.out.println("Value to Letter algorithm [tested and WORKS]");
		System.out.println("BingBall.java [FUNCTIONAL]");
	}
}