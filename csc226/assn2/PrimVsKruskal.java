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

import edu.princeton.cs.algs4.*;
import org.ietf.jgss.MessageProp;
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
		Graph graph = new Graph(G);
		Edge kruskal_step = graph.kruskalStep();
		Edge prim_step = graph.primStep();

		while (kruskal_step != null && prim_step != null)
		{
			if (kruskal_step == null || prim_step == null) return false;
			if (graph.createsCycleInPrimMST(kruskal_step) || graph.createsCycleInKruskalMST(prim_step)) return false;

			kruskal_step = graph.kruskalStep();
			prim_step = graph.primStep();
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
		
		s.close();
		
	   	if (valuesRead < n*n)
	   	{
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}	
		
        boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
    }
}

class Graph 
{
	// General variables
	private double[][] adjacency_matrix;
	private int size;

	// Kruskal variables
	private UF kruskal_mst;
	private MinPQ<Edge> kruskal_pq;
	private boolean[] kruskal_mst_has_edge;

	// Prim variables4
	private boolean[] prim_visited;
	private boolean[] prim_mst_has_edge;
	private Bag<Integer> cloud;

	/*	PLEASE NOTE: 
		If cloud is a linkedList rather than Bag, then the output 
		is the same as Rich's since iteration is no longer arbitrary.
		I chose to use Bag because I am unsure as to whether or not we
		are allowed to include Java.util or not and I do not want to 
		implement it myself (especially since Bag does get the job done).
	*/

	public Graph(double[][] adjacency_matrix)
	{
		// Graph
		this.adjacency_matrix = adjacency_matrix;
		this.size = adjacency_matrix.length;

		// Kruskal
		this.kruskal_mst = new UF(size);
		this.kruskal_pq = new MinPQ<Edge>();
		this.kruskal_mst_has_edge = new boolean[Integer.parseInt("" + size + size)];

		// Prim
		this.prim_visited = new boolean[size];
		this.prim_mst_has_edge = new boolean[Integer.parseInt("" + size + size)];
		this.cloud = new Bag<Integer>();

		// Initialization
		cloud.add(0);
		prim_visited[0] = true;	
	}

	/* 	kruskalStep()
		Returns next step in Kruskal's algorithm.
		Returns null if no steps remain.
	*/
	public Edge kruskalStep()
	{
		// If first iteration, then construct pq
		if (kruskal_mst.count() == size)
		{	
			for (int i = 0; i < size; i++)
			{
				for (int j = i; j < size; j++)
				{
					if (adjacency_matrix[i][j] > 0) kruskal_pq.insert(new Edge(i, j, adjacency_matrix[i][j]));
				}
			}
		}

		// Find and return next step
		while (!kruskal_pq.isEmpty())
		{
			Edge kruskal_step = kruskal_pq.delMin();
			int v1 = kruskal_step.either();
			int v2 = kruskal_step.other(v1);

			if (!createsCycleInKruskalMST(kruskal_step))
			{
				kruskal_mst.union(v1, v2);
				kruskal_mst_has_edge[toInteger(kruskal_step)] = true;
				return kruskal_step;
			}
		}

		return null;
	}

	/* 	toInteger()
		Converts Edge type to integer for indexing 
		(inneficient hashing on memory but oh well).
	*/
	private int toInteger(Edge e)
	{
		int v1 = e.either();
		int v2 = e.other(v1);
		return Integer.parseInt((v1 <= v2)? ("" + v1 + v2) : ("" + v2 + v1));
	}

	/* 	primStep()
		Returns next step in Prim's algorithm
		Returns null if complete.
	*/
	public Edge primStep()
	{
		if (cloud.size() < size)
		{
			MinPQ<Edge> prim_pq = new MinPQ<>();

			for (int i : cloud)
			{
				for (int j = 0; j < size; j++)
				{
					if (adjacency_matrix[i][j] > 0)
					{
						Edge e = new Edge(i, j, adjacency_matrix[i][j]);
						int v1 = e.either();
						int v2 = e.other(v1);

						// If e doesn't create a cycle, then add it to pq
						if (!createsCycleInPrimMST(e) && !prim_mst_has_edge[toInteger(e)]) prim_pq.insert(e);
					}
				}
			}

			Edge prim_step = prim_pq.delMin();
			int v1 = prim_step.either();
			int v2 = prim_step.other(v1);

			prim_mst_has_edge[toInteger(prim_step)] = true;
			prim_visited[(prim_visited[v1])? v2 : v1] = true;
			cloud.add((prim_visited[v1])? v2 : v1);

			return prim_step;
		}

		return null;
	}

	/* 	createsCycleInKruskalMST()
		Returns whether adding a given edge would
		hypothetically create a cycle in the Kruskal MST.
	*/
	public boolean createsCycleInKruskalMST(Edge e)
	{
		int v1 = e.either();
		int v2 = e.other(v1);
		return kruskal_mst.connected(v1, v2) && !kruskal_mst_has_edge[toInteger(e)];
	}

	/* 	createsCycleInPrimMST()
		Returns whether adding a given edge would
		hypothetically create a cycle in the Prim MST.
	*/
	public boolean createsCycleInPrimMST(Edge e)
	{
		int v1 = e.either();
		int v2 = e.other(v1);
		return prim_visited[v1] && prim_visited[v2] && !prim_mst_has_edge[toInteger(e)];
	}

	/* 	kruskal()
		Completely runs Kruskal's algorithm
		and returns the set of edges in MST.
	*/
	public Bag<Edge> kruskal()
	{
		if (kruskal_mst.count() != size) resetAlgs(); 

		Bag<Edge> edges = new Bag<>();

		Edge step = kruskalStep();
		while(step != null)
		{
			edges.add(step);
			step = kruskalStep();
		} 

		return edges;
	}

	/* 	prim()
		Completely runs Prim's algorithm
		and returns the set of edges in MST.
	*/
	public Bag<Edge> prim()
	{
		if (cloud.size() != 1) resetAlgs(); 

		Bag<Edge> edges = new Bag<>();

		Edge step = primStep();
		while(step != null)
		{
			edges.add(step);
			step = primStep();
		} 

		return edges;
	}

	/* 	resetAlgs()
		Internal factory method that resets the instance.
	*/
	private void resetAlgs()
	{
		// Kruskal variables
		kruskal_mst = new UF(size);
		kruskal_pq = new MinPQ<Edge>();

		// Prim variables
		prim_visited = new boolean[size];
		prim_mst_has_edge = new boolean[Integer.parseInt("" + size + size)];
		cloud = new Bag<Integer>();

		// Initialization
		cloud.add(0);
		prim_visited[0] = true;
	}
}