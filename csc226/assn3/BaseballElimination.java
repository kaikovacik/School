/* BaseballElimination.java
   CSC 226 - Summer 2018
   Assignment 4 - Baseball Elimination Program
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java BaseballElimination
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test divisions (in the format described below) and run
   the program with
	java -cp .;algs4.jar BaseballElimination file.txt (Windows)
   or
    java -cp .:algs4.jar BaseballElimination file.txt (Linux or Mac)
   where file.txt is replaced by the name of the text file.
   
   The input consists of an integer representing the number of teams in the division and then
   for each team, the team name (no whitespace), number of wins, number of losses, and a list
   of integers represnting the number of games remaining against each team (in order from the first
   team to the last). That is, the text file looks like:
   
	<number of teams in division>
	<team1_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>
	...
	<teamn_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>

	
   An input file can contain an unlimited number of divisions but all team names are unique, i.e.
   no team can be in more than one division.


   R. Little - 07/13/2018
   Also K. Kovacik Helped - 07/20/2018 (last minute, I know)
*/

import edu.princeton.cs.algs4.*;
import java.util.*;
import java.io.File;

//Do not change the name of the BaseballElimination class
public class BaseballElimination {
	
	// We use an ArrayList to keep track of the eliminated teams.
	public ArrayList<String> eliminated = new ArrayList<String>();
	private int n;
	private Team[] teams;
	private int[][] versus;

	/* BaseballElimination(s)
		Given an input stream connected to a collection of baseball division
		standings we determine for each division which teams have been eliminated 
		from the playoffs. For each team in each division we create a flow network
		and determine the maxflow in that network. If the maxflow exceeds the number
		of inter-divisional games between all other teams in the division, the current
		team is eliminated.
	*/
	public BaseballElimination(Scanner s) {
		this.n = s.nextInt();
		this.teams = new Team[n];
		this.versus = new int[n][n];

		// Read input
		for (int i = 0; i < n; i++) {
			teams[i] = new Team(s.next(), s.nextInt(), s.nextInt());
			for (int j = 0; j < n; j++) {
				versus[i][j] = s.nextInt();
			}
		}

		for (Team team : teams) {
			if (isEliminated(team)) eliminated.add(team.name);
		}
	}

	/* indexOf(team)
		Returns integer representation of given Team object
		for easy indexing.
	*/
	private int indexOf(Team team) {
		for (int i = 0; i < n; i++) {
			if (teams[i].equals(team)) return i;
		}

		return -1;
	}

	/* matches(team)
		Returns a queue of game matches that have yet to
		take place and that do not include the given team.
	*/
	private java.util.Queue<Match> matches(Team team) {
		java.util.Queue<Match> matches = new java.util.LinkedList<>();
		for (int i = 0; i < n; i++) {
			if (!teams[i].equals(team)) {
				for (int j = i+1; j < n; j++) {
					if (teams[j] != team && versus[i][j] > 0) {
						matches.add(new Match(teams[i], teams[j]));
					}
				}
			}
		}

		return matches;
	}

	/* calculateMaxFlow(team)
	   returns the value of the maximum flow for a network
	   based on the given team.
	*/
	private double calculateMaxFlow(Team team) {
		FlowNetwork network = buildTeamNetwork(team);

		return new FordFulkerson(network, network.V()-2, network.V()-1).value();
	}

	/* buildTeamNetwork(team)
	   Constructs a flow network for the baseball-elimination
	   problem for the given team.
	*/
	private FlowNetwork buildTeamNetwork(Team team) {
		java.util.Queue<Match> matches = matches(team);
		int size = n+matches.size()+2;
		FlowNetwork network = new FlowNetwork(size);
		
		
		int target = size-1;
		int source = target-1;

		int total_games = team.wins+team.to_play;

		for (Team t : teams) {
			if (!t.equals(team)) {
				network.addEdge(new FlowEdge(indexOf(t), target, total_games-t.wins));
			}
		}

		int i = n;
		for (Match match : matches) {
			Team team1 = match.either();
			Team team2 = match.other(team1);

			network.addEdge(new FlowEdge(i, indexOf(team1), Double.POSITIVE_INFINITY));
			network.addEdge(new FlowEdge(i, indexOf(team2), Double.POSITIVE_INFINITY));

			network.addEdge(new FlowEdge(source, i++, versus[indexOf(team1)][indexOf(team2)]));
		}

		return network;
	}

	/* isTriviallyEliminated(team)
	   Checks if the given team even has a chance of winning
	   based on number of wins, games left, and leading
	   team's wins.
	*/
	private boolean isTriviallyEliminated(Team team) { 
		for (Team cmp : teams) {
			if (team.wins+team.to_play < cmp.wins) {
				return true;
			}
		}

		return false;
	}

	/* isEliminated(team)
	   Checks the possibility of the given team winning the
	   division.
	*/
	private boolean isEliminated(Team team) {
		if (isTriviallyEliminated(team)) return true;

		// Find maximum number of exterior games
		int max = 0;
		for (Match match : matches(team)) {
			Team team1 = match.either();
			Team team2 = match.other(team1);
			max += versus[indexOf(team1)][indexOf(team2)];
		}

		return calculateMaxFlow(team) < max;
	}

	/* main()
	   Contains code to test the BaseballElimantion function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		BaseballElimination be = new BaseballElimination(s);		
		
		if (be.eliminated.size() == 0)
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}

/* Team
	Describes a team from the division.
	Organizes relevant data on specific team.
*/
class Team {
	String name;
	int wins;
	int to_play;

	public Team(String name, int wins, int to_play) {
		this.name = name;
		this.wins = wins;
		this.to_play = to_play;
	}

	public boolean equals(Team other) {
		return (name.equals(other.name) && wins == other.wins && to_play == other.to_play)? true : false;
	}

	public String toString() {
		return "\"" +  name + "\"";
	}
}

/* Match
	Describes a game match that two teams are participating in.
	Allows for organization with pairs.
*/
class Match {
	Team team1;
	Team team2;

	public Match(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}

	public Team either() {
		return team1;
	}

	public Team other(Team team) {
		return (team1.equals(team))? team2 : team1;
	}

	public String toString() {
		return team1 + " VS " + team2;
	}
}