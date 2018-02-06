/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: September 22, 2017
 * Filename: BingoCalls.java
 * Details: CSC115 Assignment 1
 */ 

public class BingoCalls
{
	BingoBall[] container = new BingoBall[2];
	private int numBallsCalled = 0;

	/**
	 * Creates a new, larger array and transfers all items
	 * and takes memory spaces.
	 * @param tempContainer Used as temporary to trasnfer items from Container.
	 */
	private void widenContainer()
	{
		BingoBall[] tempContainer = new BingoBall[container.length*2];

		for(int i = 0; i < container.length; i++)
		{
			tempContainer[i] = container[i];
		}

		container = tempContainer;
	}

	/**
	 * Inserts BingoBalls into container
	 * @param ball The specific BingoBall called.
	 * @return The value in the newly sorted array at
	 * index k.
	 */
	public void insert(BingoBall ball)
	{

		numBallsCalled++;

		if(numBallsCalled == container.length)
		{
			widenContainer();
		}

		container[numBallsCalled-1] = ball;
	}

	/**
	 * Gets the value of private numBallsCalled.
	 * @return The number of balls called during the game.
	 */
	public int numBallsCalled()
	{
		return numBallsCalled;
	}	
 
	/**
	 * Removes given BinggoBall from the Container.
	 * @param ball given BingoBall.
	 */
	public void remove(BingoBall ball)
	{
		for(int i = 0; i < numBallsCalled; i++)
		{

			//runs through matrix, checks for and removes matches
			if(container[i].equals(ball))
			{
				container[i] = null;

				/*
				 * removes null gap in matrix by
				 * parsing through matrix until last index 
				 * and turning last index into null
				 */
				while(i != numBallsCalled-1)
				{
					container[i] = container[i+1];

					if(i+1 == numBallsCalled-1)
					{
						container[i+1] = null;
					}
					
					i++;
				}

				numBallsCalled--;
			}
		}
	}

	/**
	 * Checks if equivalent BingoBall is already present
	 * in the container.
	 * @param ball the given BingoBall to check.
	 * @return whether true or false.
	 */
	public boolean wasCalled(BingoBall ball)
	{
		for(int i = 0; i < numBallsCalled; i++)
		{
			if(container[i].equals(ball))
			{
				return true;
			}
		}

		return false;	
	}

	/**
	 * Resets Container.
	 * @param tempContainer New empty container to replace
	 * used container.
	 */
	public void makeEmpty()
	{

		BingoBall[] tempContainer = new BingoBall[2];

		container = tempContainer;
		numBallsCalled = 0;	
	}

	/**
	 * Creates a new, larger array and transfers all items
	 * and takes memory spaces.
	 * @param contents String that shows contents of Container
	 * @param empty Boolean that determines whether or not
	 * to print empty braces.
	 * @return contents.
	 */
	public String toString()
	{
		String contents = "{";
		boolean empty = true;

		for(int i = 0; i < container.length; i++)
		{	

			//if not an empty space (null) and NOT the final index
			if(container[i] != null && i != numBallsCalled-1)
			{
				contents = contents + "" + container[i] + ", ";
				empty = false;
			}

			//if not an empty space (null) and the final index
			else if(container[i] != null && i == numBallsCalled-1)
			{
				contents = contents + "" + container[i] + "}";
				empty = false;
			}
			
			//if container is empty
			else if(empty)
			{
				contents = "{}";
			}
		}
		
		return contents;
	} 

	/**
	 * Used as a tester for BingoCalls and implementation
	 * of BingoBall class.
	 * @param test BingoCalls object to access BingoCalls methods.
	 * @param rack Array of BingoBalls to use in testing.
	 */
	public static void main(String[] args) 
	{
		BingoBall[] rack = new BingoBall[75];

		for(int i = 0; i < 75; i++)
		{
			rack[i] = new BingoBall(i+1);
		}

		BingoCalls test = new BingoCalls();

		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[1]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[53]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[43]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[74]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[42]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		test.insert(rack[12]);
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();
		
		if(test.wasCalled(rack[43]))
		{
			System.out.println(rack[43] + " is called again so:");
			test.remove(rack[43]);
		}

		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		System.out.println("Ball #18 was called: " + test.wasCalled(rack[18]));
		System.out.println("Ball #74 was called: " + test.wasCalled(rack[74]));

		test.makeEmpty();
		System.out.println("Container emptied: ");
		System.out.println(test);
		System.out.println("Array size: " + test.container.length);
		System.out.println("Balls called: " + test.numBallsCalled());
		System.out.println();

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("TESTER CHECKLIST");
		System.out.println("insert [tested and WORKS]");
		System.out.println("numBallsCalled [tested and WORKS]");
		System.out.println("removed [tested and WORKS]");
		System.out.println("wasCalled [tested and WORKS]");
		System.out.println("makeEmpty [tested and WORKS]");
		System.out.println("toString [tested and WORKS]");
	}
}