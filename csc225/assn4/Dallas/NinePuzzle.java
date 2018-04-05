import java.io.File;
import java.util.*;

public class NinePuzzle{

	public static final int NUM_BOARDS = 362880;//9!
	private static final int[][] goal = {
		{1, 2, 3},
		{4, 5, 6},
		{7, 8, 0},
	};

	private static void moveTile(int[][] B, int row, int column, String direction, int vertices, Graph graph){
		int board[][] = new int[3][3];
		for(int a = 0; a < 3; a++){
			board[a] = B[a].clone();
		}
		if(direction == "up"){
			board[row][column] = board[row-1][column];
			board[row-1][column] = 0;
		}
		else if(direction == "down"){
			board[row][column] = board[row+1][column];
			board[row+1][column] = 0;
		}
		else if(direction == "left"){
			board[row][column] = board[row][column-1];
			board[row][column-1] = 0;
		}
		else if(direction == "right"){
			board[row][column] = board[row][column+1];
			board[row][column+1] = 0;
		}
		graph.addEdge(getIndexFromBoard(board), vertices);
	}

	public static boolean SolveNinePuzzle(int[][] B){
		Graph graph = new Graph(NUM_BOARDS);
		for(int a = 0; a < graph.getVertices(); a++){
			int[][] board = getBoardFromIndex(a);
			int row = -1;
			int column = -1;
			for(int b = 0; b < 3; b++){
				for(int c = 0; c < 3; c++){
					if(board[b][c] == 0){
						row = b;
						column = c;
					}
				}
			}
			if(row > 0){
				moveTile(board, row, column, "up", a, graph);
			}
			if(row < 2){
				moveTile(board, row, column, "down", a, graph);
			}
			if(column > 0){
				moveTile(board, row, column, "left", a, graph);
			}
			if(column < 2){
				moveTile(board, row, column, "right", a, graph);
			}
		}
		int start = getIndexFromBoard(B);
		int finish = getIndexFromBoard(goal);
		BFS bfs = new BFS(graph, finish);
		if(bfs.hasPathTo(start)){
			for(int path : bfs.pathTo(start)){
				printBoard(getBoardFromIndex(path));
			}
			return true;
		}
		return false;
	}

	public static void printBoard(int[][] B){
		for(int a = 0; a < 3; a++){
			for(int b = 0; b < 3; b++){
				System.out.printf("%d ",B[a][b]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static int getIndexFromBoard(int[][] B){
		int[] P = new int[9];
		int[] PI = new int[9];
		for(int a = 0; a < 9; a++){
			P[a] = B[a/3][a%3];
			PI[P[a]] = a;
		}
		int s;
		int tmp;
		int id = 0;
		int multiplier = 1;
		for(int a = 9; a > 1; a--){
			s = P[a-1];
			P[a-1] = P[PI[a-1]];
			P[PI[a-1]] = s;
			tmp = PI[s];
			PI[s] = PI[a-1];
			PI[a-1] = tmp;
			id += multiplier*s;
			multiplier *= a;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int tmp;
		for(int a = 0; a < 9; a++){
			P[a] = a;
		}
		for(int a = 9; a > 0; a--){
			tmp = P[a-1];
			P[a-1] = P[id%a];
			P[id%a] = tmp;
			id /= a;
		}
		int[][] B = new int[3][3];
		for(int a = 0; a < 9; a++){
			B[a/3][a%3] = P[a];
		}
		return B;
	}
	
	public static void main(String[] args){
		Scanner s;
		if(args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			}
			catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		}
		else{
			System.out.println("Failure: no file provided");
			return;
		}
		int graphNum = 0;
		double totalTimeSeconds = 0;
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt()){
				break;
			}
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for(int i = 0; i < 3 && s.hasNextInt(); i++){
				for(int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			if(isSolvable){
				System.out.printf("Board %d: Solvable.\n",graphNum);
			}
			else{
				System.out.printf("Board %d: Not solvable.\n",graphNum);
			}
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n\tAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);
	}
}

class BFS{

	private int[] edgeTo;
	private boolean[] marked;

	public BFS(Graph graph, int source){
		edgeTo = new int[graph.getVertices()];
		marked = new boolean[graph.getVertices()];
		bfs(graph, source);
	}

	private void bfs(Graph graph, int source){
		Queue<Integer> path = new LinkedList<Integer>();
		marked[source] = true;
		path.add(source);
		while(!path.isEmpty()){
			int vertex = path.remove();
			for(int vertex2 : graph.adjacent(vertex)){
				if(!marked[vertex2]){
					edgeTo[vertex2] = vertex;
					marked[vertex2] = true;
					path.add(vertex2);
				}
			}
		}
	}

	public boolean hasPathTo(int vertices){
		return marked[vertices];
	}

	public Iterable<Integer> pathTo(int vertices){
		if(!hasPathTo(vertices)){
			return null;
		}
		Stack<Integer> path = new Stack<Integer>();
		for(int a = vertices; a != 0; a = edgeTo[a]){
			path.push(a);
		}
		path.push(0);
		return path;
	}
}

class Graph{

	private final int vertices;
	private LinkedList<Integer>[] adjacent;

	@SuppressWarnings("unchecked")
	public Graph(int vertices){
		this.vertices = vertices;
		adjacent = new LinkedList[vertices];
		for(int a = 0; a < vertices; a++){
			adjacent[a] = new LinkedList<Integer>();
		}
	}

	public int getVertices(){
		return vertices;
	}

	public void addEdge(int vertices, int vertices2){
		adjacent[vertices].add(vertices2);
		adjacent[vertices2].add(vertices);
	}

	public Iterable<Integer> adjacent(int vertices){
		return adjacent[vertices];
	}
}