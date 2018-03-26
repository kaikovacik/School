/*
 * Kai Kovacik
 * V00880027
 * March 25, 2018
 */

/* NinePuzzle.java
   CSC 225 - Spring 2017
   Assignment 4 - Template for the 9-puzzle
   
   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt
	
   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the 
   empty square. For example, a sample board with the middle square empty is
   
    1 2 3
    4 0 5
    6 7 8
   
   And a solved board is
   
    1 2 3
    4 5 6
    7 8 0
   
   An input file can contain an unlimited number of boards; each will be 
   processed separately.
  
   B. Bird    - 07/11/2014
   M. Simpson - 11/07/2015
*/

import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;

public class NinePuzzle
{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;

	private static class Graph
	{

		private int numOfVertices;
		private int numOfEdges;
		private LinkedList<Integer>[] adjacency;

		@SuppressWarnings("unchecked") 
		private Graph(int numOfVertices)
		{
			this.numOfVertices = numOfVertices;
			this.numOfEdges = 0;
			adjacency = new LinkedList[numOfVertices];
			for (int i = 0; i < numOfVertices; i++)
				adjacency[i] = new LinkedList<Integer>();
		}

		private void addEdge(int index1, int index2)
		{
			adjacency[index1].add(index2);
			numOfEdges++;
		}

	}

	private static int[][] copyArray(int[][] arr)
	{
		int[][] cpy = new int[arr.length][arr.length];
		for (int i = 0; i < arr.length; i++)
			cpy[i] = arr[i].clone();

		return cpy;
	}

	private static Graph BuildNPuzzleGraph(int size)
	{
		int[][] board;
		Graph graph = new Graph(size);
		int x = 0;
		int y = 0;

		for (int i = 0; i < graph.numOfVertices; i++)
		{
			board = getBoardFromIndex(i);
			int n = board.length;
			// Find coordinates of space
			getCoordinates:
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < n; k++)
				{
					if (board[j][k] == 0)
					{
						x = k;
						y = j;
						break getCoordinates;
					}
				}
			}

			if (y > 0)
			{
				// Move tile up
				int[][] tempB = copyArray(board);
				tempB[y][x] = tempB[y-1][x];
				tempB[y-1][x] = 0;

				// Connect move in graph
				graph.addEdge(getIndexFromBoard(board), getIndexFromBoard(tempB));
			}
			if (y < n-1)
			{
				// Move tile down
				int[][] tempB = copyArray(board);
				tempB[y][x] = tempB[y+1][x];
				tempB[y+1][x] = 0;

				// Connect move in graph
				graph.addEdge(getIndexFromBoard(board), getIndexFromBoard(tempB));
			}
			if (x > 0)
			{
				// Move tile left
				int[][] tempB = copyArray(board);
				tempB[y][x] = tempB[y][x-1];
				tempB[y][x-1] = 0;

				// Connect move in graph
				graph.addEdge(getIndexFromBoard(board), getIndexFromBoard(tempB));				
			}
			if(x < n-1)
			{
				// Move tile right
				int[][] tempB = copyArray(board);
				tempB[y][x] = tempB[y][x+1];
				tempB[y][x+1] = 0;

				// Connect move in graph
				graph.addEdge(getIndexFromBoard(board), getIndexFromBoard(tempB));
			}
		}

		return graph;
	}

	private static LinkedList<Integer> BFSpath(Graph G, int[][] start, int[][] end)
	{
		int start_index = getIndexFromBoard(start);
		int end_index = getIndexFromBoard(end);

		LinkedList<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[G.numOfVertices];
		int[] edgeTo = new int[G.numOfVertices];

		queue.add(start_index);
		visited[start_index] = true;
		searchForEnd:
		while (!queue.isEmpty())
		{
			int curr = queue.poll();

			for (int neighbor : G.adjacency[curr])
			{
				// Stop if end is reached
				if (neighbor == end_index)
				{
					edgeTo[neighbor] = curr;
					visited[neighbor] = true;
					break searchForEnd;
				}
				else if (!visited[neighbor])
				{
					queue.add(neighbor);
					edgeTo[neighbor] = curr;
					visited[neighbor] = true;
				}
			}
		}

		// Return path or null if no path was found
		if (!visited[end_index])
			return null;
		else
		{
			LinkedList<Integer> path = new LinkedList<Integer>();

			for (int i = end_index; i != start_index; i = edgeTo[i])
				path.push(i);
			path.push(start_index);

			return path;
		}
	}

	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the 
		value 0),return true if the board is solvable and false otherwise. 
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	public static boolean SolveNinePuzzle(int[][] B)
	{
		// Build game state graph
		Graph game_states = BuildNPuzzleGraph(NUM_BOARDS);
		// Find path from B to solution (getBoardFromIndex(0))
		LinkedList<Integer> path = BFSpath(game_states, B, getBoardFromIndex(0));

		if (path == null)
			return false;
		else
		{
			// Print path
			for (int step : path)
				printBoard(getBoardFromIndex(step));
			return true;
		}
	}
	
	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284. 
	*/
	public static int getIndexFromBoard(int[][] B)
	{
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++)
		{
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for (n = 9; n > 1; n--)
		{
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;
			
			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id)
	{
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--)
		{
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for (i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}
	

	public static void main(String[] args)
	{
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		
		Scanner s;

		if (args.length > 0)
		{
			//If a file argument was provided on the command line, read from the file
			try
			{
				s = new Scanner(new File(args[0]));
			} 
			catch (java.io.FileNotFoundException e)
			{
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}
		else
		{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read boards until EOF is encountered (or an error occurs)
		while (true)
		{
			graphNum++;
			if (graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++)
			{
				for (int j = 0; j < 3 && s.hasNextInt(); j++)
				{
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9)
			{
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			printBoard(B);
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();

			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:totalTimeSeconds);
	}

}