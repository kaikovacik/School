/**
 * RunSolver.java
 *
 * Created for CSC115 201605 Assignment four.
 * Modified from a previous version created by M. Zastre.
 * Current modification: B. Bultena
 *
 * This top-level program takes the name of a file containing maze
 * information, and then reads this into a integers and an array
 * of strings. The Maze object is instantiated, solve() called on it
 * and the results (if any) are output on the console.
 *
 * This code will not compile until there is an implementation of
 * the Maze.java class that has at least a constructor and a
 * bare-bones implementation of solve() (i.e., bare-bones means that
 * the method simply returns null).
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RunSolver {

	/**
	 * Runs an external test of the Maze class.
	 * @param args The single name of a text file containing maze information
	 *	in the following format (excluding the line number):
	 * 	<ol>
	 *	<li> int int</li>
	 *	<li> int int</li>
	 *	<li> int int</li>
	 *	<li> maze row of spaces and asterisks.</li>
	 * 	<li> repeat similar lines as many times as required.</li>
	 *	</ol>
	 * The first line contains the number of rows and the number of columns.
	 * The second line contains the cell number of the entrance to the maze.
	 * The third line contains the cell number of the exit from the maze.
	 * Subsequent lines contain equal length strings of asterisks and spaces,
	 *	an asterisk representing a wall and a space representing a free cell.
 	 */
	public static void main(String args[]) {
		if (args.length < 1) {
			System.err.println("usage: java RunSolver <mazefile>");
			System.exit(1);
        	}
	
		Scanner infileScanner = null;
		String infileName = args[0];
		int rows, columns;
		int startRow, startColumn;
		int finishRow, finishColumn;

		try {
			infileScanner = new Scanner(new File(infileName));
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open the given file: "+infileName);
			System.exit(1);
		}

		rows = infileScanner.nextInt();
		columns = infileScanner.nextInt();
		startRow = infileScanner.nextInt();
		startColumn = infileScanner.nextInt();
		finishRow = infileScanner.nextInt();
		finishColumn = infileScanner.nextInt();
		infileScanner.nextLine(); // clears the newline after the last int.
         
		String mazeData[] = new String[rows];
		String line = null;
		for (int row = 0; row < rows; row++) {
			line = infileScanner.nextLine();
			mazeData[row] = line.trim();
			//System.out.println(mazeData[row]);//prints unsolved maze
		}

		Maze maze = new Maze(mazeData, new Cell(startRow,startColumn),
			new Cell(finishRow,finishColumn));
		System.out.println(maze + "\n");
		CellDeque path = maze.solve();
		System.out.println(maze + "\n");

		if (path == null || path.isEmpty()) {
			System.out.println("No path!!");
		} else {
			System.out.println("Solution found");
			while (!path.isEmpty()) {
				System.out.print(path.removeFirst()+" "); // removeLast??
			}
			System.out.println();
		}
	}
}
