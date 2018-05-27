/* bipartite.java
   CSC 225 - Spring 2018
   Template for recognizing bipartite graphs and printing out the two partitions
   in case the graph is bipartite.
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java bipartite
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java bipartite file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   Rahnuma Islam Nishat and Michael Simpson - March 17, 2018
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.util.*;

public class bipartite{

	/* isBipartite(G)
	   Given an adjacency matrix describing a graph,check if G is bipartite or not
	*/
	public static boolean isBipartite(int[][] G){
		int n = G.length;
		//TO DO: Write your code here
		return false;
     	
	}
	
	/*
	printPartitions(G)
	Given the adjacency matrix of a bipartite graph G, print out the two partitions of the vertices of G.
	*/
	
	public static void printPartitions(int[][] G){
		int n = G.length;
		//TO DO: Write your code here
	}
		
	/* main()
	   Contains code to test the BFS method.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			if(isBipartite(G.clone())){
				printPartitions(G.clone());
			}
			else{
				System.out.println("The graph is not bipartite.");
			}
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
				
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}