/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: October 14, 2017 (I had already written my own mazesolver so most of that code has been implemented here)
 * Filename: Maze.java
 * Details: CSC115 Assignment 4
 */


public class Maze
{
	//PRIVATE GLOBAL{
	private String[] mazeData;
	private char[][] pathData;
	private Cell start, finish;
	private CellDeque memDeque, path; //(path could be represented as a Stack)
	private int rows, columns;
	//END OF PRIVATE GLOBAL}


	//CONSTRUCTOR 1 of 1
	/**
	 * Creates Maze object.
	 * @param mazeData String array that contains the maze line-by-line.
	 * @param start The starting Cell of the maze.
	 * @param finish The ending Cell of the maze.
	 */
	public Maze(String[] mazeData, Cell start, Cell finish)
	{
		this.mazeData = mazeData;
		this.start = start;
		this.finish = finish;

		rows = mazeData.length;
		columns = mazeData[0].length();

		pathData = new char[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				pathData[i][j] = mazeData[i].charAt(j);
			}
		}

		memDeque = new CellDeque();
		path = new CellDeque();
	}


	//PUBLIC METHODS(String toString(), CellDeque Solve()){
	/**
	 * String representation of the maze.
	 * @return The String s that represents the maze.
	 */
	public String toString()
    {
    	String s = "";
    	for(int i = 0; i <= rows; i++)
    	{
    		if(i == 0)
    		{
    			s = " ";
    			for(int j = 0; j < columns; j++)
    			{
    				//gets row index markers
    				s = s + (j%10) + " ";
    			}
    		}
    		else
    		{
    			for(int j = 0; j <= columns; j++)
    			{
    				if(j == 0)
    				{
    					//gets column index markers
    					s = s + ((i-1)%10);
    				}
    				else
    				{
    					//printes tokens of maze seperated by spaces for readablitlty
    					s = s + pathData[i-1][j-1] + " ";
    				}
    			}
    		}

    		//avoids empty line below maze
    		if(i != rows)
    		{
    			s = s + "\n";
    		}
  		}
    	return s;
    }


    /**
     * The primary callable method that solves a created maze.
     * @return path The CellDeque that contains, in order the path of the maze.
     */
	public CellDeque solve()
	{
		traverse(start);
		return path;
	}
	//END OF PUBLIC METHODS}


	//PRIVATE METHODS{
	//	HELPER METHODS{
	/**
	 * Finds the item at the given Cell.
	 * @param c The given Cell.
	 * @return pathData[c.row][c.col] The char in the array that holds the solving maze.
	 */
	private char itemAt(Cell c)
    {
        if(c.col > columns || c.row > rows || c.row < 0 || c.col < 0)
        {
            return '*';
        }
    	return pathData[c.row][c.col];
    }


    /**
     * Replaced the item in pathData at the given Cell.
     * @param c The given Cell.
     * @param item The item that will be placed.
     */
    private void replaceItemAt(Cell c, char item)
    {
    	pathData[c.row][c.col] = item;
    }


    /**
     * Marks the given Cell with an X.
     * @param c The given Cell.
     */
    private void mark(Cell c)
    {
    	path.insertFirst(c);
    	replaceItemAt(c, '@');
    }


    /**
     * Erases the previously marked Cell.
     * @param c The given Cell.
     */
    private void unmark(Cell c)
    {
    	path.removeFirst();
    	replaceItemAt(c, ' ');
    }


    /**
     * Returns the upwards Cell.
     * @param c The given Cell.
     * @return temp The upwards Cell.
     */
	private Cell up(Cell c)
	{
		Cell temp = new Cell(c.row-1, c.col);
		return temp;
	}


	/**
     * Returns the downwards Cell.
     * @param c The given Cell.
     * @return temp The downwards Cell.
     */
	private Cell down(Cell c)
	{
		Cell temp = new Cell(c.row+1, c.col);
		return temp;
	}


	/**
     * Returns the rightwards Cell.
     * @param c The given Cell.
     * @return temp The rightwards Cell.
     */
	private Cell right(Cell c)
	{
		Cell temp = new Cell(c.row, c.col+1);
		return temp;
	}


	/**
     * Returns the leftwards Cell.
     * @param c The given Cell.
     * @return temp The leftwards Cell.
     */
	private Cell left(Cell c)
	{
		Cell temp = new Cell(c.row, c.col-1);
		return temp;
	}


	/**
     * Recursive method that backtrackes the path when a dead end is reached.
     * @param pos The current Cell, starting from dead end.
     * @param end The first Cell from which another path is present.
     */
	private void backtrack(Cell pos, Cell end)
    {
        unmark(pos);

        //basecase at start (executed if maze has no solutions)
        if(end == start && pos.equals(end))
        {
        	return;
        }
        //basecase at fork
        else if(pos.equals(end))
        {
            mark(pos);
            return;
        }
        //checks for mark following precedence (ambiguous so mark character can easily be changed)
        else if(itemAt(down(pos)) != ' ' && itemAt(down(pos)) != '*')
        {
            backtrack(down(pos), end);
        }
        else if(itemAt(right(pos)) != ' ' && itemAt(right(pos)) != '*')
        {
            backtrack(right(pos), end);
        }
        else if(itemAt(up(pos)) != ' ' && itemAt(up(pos)) != '*')
        {
            backtrack(up(pos), end);
        }
        else if(itemAt(left(pos)) != ' ' && itemAt(left(pos)) != '*')
        {
            backtrack(left(pos), end);
        }
    }
	//	END OF HE LPER METHODS}


    /**
     * Recursive method that traverses the maze from start to finish.
     * @param pos The current Cell, starting from start.
     */
	private void traverse(Cell pos)
	{
		mark(pos);

		//basecase at finish
        if(pos.equals(finish))
        {
            return;
        }
        //*
        //determines path in order of precedence: down, right, up, left
        else if(itemAt(down(pos)) == ' ')
        {
        	//determines if multiple paths available
            if(itemAt(right(pos)) == ' ' || itemAt(up(pos)) == ' ' || itemAt(left(pos)) == ' ')
            {
            	//inserts point at which fork is present to front of Deque
                memDeque.insertFirst(pos);
                //determines where, incase of dead end, to continue from with precedence
                if(itemAt(right(pos)) == ' ')
                {
                	//inserts point at back of Deque
                    memDeque.insertLast(right(pos));
                }
                else if(itemAt(up(pos)) == ' ')
                {
                    memDeque.insertLast(up(pos));
                }
                else if(itemAt(left(pos)) == ' ')
                {
                    memDeque.insertLast(left(pos));
                }
            }
            traverse(down(pos));
        }
        //same logic as *
        else if(itemAt(right(pos)) == ' ')
        {
            if(itemAt(up(pos)) == ' ' || itemAt(left(pos)) == ' ')
            {
                memDeque.insertFirst(pos);
                if(itemAt(up(pos)) == ' ')
                {
                    memDeque.insertLast(up(pos));
                }
                else if(itemAt(left(pos)) == ' ')
                {
                    memDeque.insertLast(left(pos));
                }
            }
            traverse(right(pos));
        }
        //same logic as *
        else if(itemAt(up(pos)) == ' ')
        {
            if(itemAt(left(pos)) == ' ')
            {
                memDeque.insertFirst(pos);
                memDeque.insertLast(left(pos));
            }
            traverse(up(pos));
        }
        //same logic as *
        else if(itemAt(left(pos)) == ' ')
        {
            traverse(left(pos));
        }
        //if dead end
        else
        {
        	//no solutions if there were no alternative routes
            if(memDeque.isEmpty())
            {
            	backtrack(pos, start);
            }
            else
            {
            	//backtracks from dead end to Cell where fork is present
                backtrack(pos, memDeque.removeFirst());
                //continues
                traverse(memDeque.removeLast());
            }
        }
	}
	//END OF PRIVATE METHODS}
}
