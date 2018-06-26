/* PrimVsKruskal.java
   CSC 226 - Summer 2018
   Assignment 2 - Prim MST versus Kruskal MST Template
   
   The file includes the "import edu.princeton.cs.algs4.*;" so that yo can use
   any of the code in the algs4.jar file. You should be able to compile your program
   with the command
   
	javac -cp .;algs4.jar PrimVsKruskal.java
	
   To conveniently test the algorithm with a large input, create a text file
   containing a test graphs (in the format described below) and run
   the program with
   
	java -cp .;algs4.jar PrimVsKruskal file.txt
	
   where file.txt is replaced by the name of the text file.
   
   The input consists of a graph (as an adjacency matrix) in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry G[i][j] >= 0.0 of the adjacency matrix gives the weight (as type double) of the edge from 
   vertex i to vertex j (if G[i][j] is 0.0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that G[i][j]
   is always equal to G[j][i].


   R. Little - 06/22/2018
*/

<<<<<<< HEAD
import edu.princeton.cs.algs4.*;
import java.util.Scanner;
import java.io.File;

//Do not change the name of the PrimVsKruskal class
public class PrimVsKruskal{

   /* PrimVsKruskal(G)
       Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
       determine if the minimum spanning tree of G found by Prim's algorithm is equal to 
       the minimum spanning tree of G found by Kruskal's algorithm.
       
       If G[i][j] == 0.0, there is no edge between vertex i and vertex j
       If G[i][j] > 0.0, there is an edge between vertices i and j, and the
       value of G[i][j] gives the weight of the edge.
       No entries of G will be negative.
   */
   static boolean PrimVsKruskal(double[][] G){
       int n = G.length;

       /* Build the MST by Prim's and the MST by Kruskal's */
       /* (You may add extra methods if necessary) */
       
       /* ... Your code here ... */
       
       
       /* Determine if the MST by Prim equals the MST by Kruskal */
       boolean pvk = true;
       /* ... Your code here ... */

       return pvk;	
   }
       
   /* main()
      Contains code to test the PrimVsKruskal function. You may modify the
      testing code if needed, but nothing in this function will be considered
      during marking, and the testing process used for marking will not
      execute any of the code below. 
   */
  public static void main(String[] args) {
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
       
       int n = s.nextInt();
       double[][] G = new double[n][n];
       int valuesRead = 0;
       for (int i = 0; i < n && s.hasNextDouble(); i++){
           for (int j = 0; j < n && s.hasNextDouble(); j++){
               G[i][j] = s.nextDouble();
               if (i == j && G[i][j] != 0.0) {
                   System.out.printf("Adjacency matrix contains self-loops.\n");
                   return;
               }
               if (G[i][j] < 0.0) {
                   System.out.printf("Adjacency matrix contains negative values.\n");
                   return;
               }
               if (j < i && G[i][j] != G[j][i]) {
                   System.out.printf("Adjacency matrix is not symmetric.\n");
                   return;
               }
               valuesRead++;
           }
       }
       
       if (valuesRead < n*n){
           System.out.printf("Adjacency matrix for the graph contains too few values.\n");
           return;
       }	
       
       boolean pvk = PrimVsKruskal(G);
       System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
   }
=======
 import edu.princeton.cs.algs4.*;
 import java.util.Scanner;
 import java.io.File;

//Do not change the name of the PrimVsKruskal class
public class PrimVsKruskal
{

	/* PrimVsKruskal(G)
		Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
		determine if the minimum spanning tree of G found by Prim's algorithm is equal to 
		the minimum spanning tree of G found by Kruskal's algorithm.
		
		If G[i][j] == 0.0, there is no edge between vertex i and vertex j
		If G[i][j] > 0.0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static boolean PrimVsKruskal(double[][] G)
	{
		int n = G.length;
		boolean[] kruskal_visited = new boolean[n];
		boolean[] prim_visited = new boolean[n];

		// for n times:
		// 	ps = Make a step with Prim's
		// 	ks = Make a step with Kruskal's
		// 	if edge from Prim's makes cycle in Kruskal tree
		//		return false
		// 	if edge from Kruskal's makes cycles in Prim tree
		// 		return false
		//
		// return true

		for (int t = 0; t < n; t++)
		{	
			// Kruskal's
			Edge kruskal_edge;

			System.out.println((new Edge(1,1,1.0)).toString().equals((new Edge(1,1,1.0)).toString()));

			double smallest_weight = Double.POSITIVE_INFINITY;
			for (int i = 0; i < n; i++)
				for(int j = i; j < n; j++)
				{
					if (G[i][j] < smallest_weight)
					{
						smallest_weight = G[i][j];
						kruskal_edge = new Edge(i, j, smallest_weight);
					}
				}	
		}

		return true;
	}
		
	/* main()
	   Contains code to test the PrimVsKruskal function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below. 
	*/
   public static void main(String[] args) 
   {
		Scanner s;
		if (args.length > 0)
		{
			try
			{
				s = new Scanner(new File(args[0]));
			} 
			catch(java.io.FileNotFoundException e)
			{
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}
		else
		{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int n = s.nextInt();
		double[][] G = new double[n][n];
		int valuesRead = 0;
		for (int i = 0; i < n && s.hasNextDouble(); i++)
		{
			for (int j = 0; j < n && s.hasNextDouble(); j++)
			{
				G[i][j] = s.nextDouble();
				if (i == j && G[i][j] != 0.0) 
				{
					System.out.printf("Adjacency matrix contains self-loops.\n");
					return;
				}
				if (G[i][j] < 0.0) 
				{
					System.out.printf("Adjacency matrix contains negative values.\n");
					return;
				}
				if (j < i && G[i][j] != G[j][i]) 
				{
					System.out.printf("Adjacency matrix is not symmetric.\n");
					return;
				}

				valuesRead++;
			}
		}
		
		if (valuesRead < n*n)
		{
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}	
		
        boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
    }
>>>>>>> 1b7ead6752ff17fdf9559195de8eaea9a3479f5d
}