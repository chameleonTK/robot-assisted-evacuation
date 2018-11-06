package cs5011.pathfinding;
import cs5011.pathfinding.problem.multi.MultiDistance;
import cs5011.pathfinding.search.*;

/**
 * Assigmnment1: solve pathfinding problem using many algorithms.
 * @author 180008901
 *
 */
public class RobotAssistedEvacuation {

	/** Main function.
     * @param args
     * argument[0] is to set problem's name which can be pb1, pb2, pb3, ... and m100 for multi-agent problem.
     * argument[1] is to set search algorithm which can be dfs, bfs, astar, greedy and bi.
     * argument[2] is to set heuristic function (only used for informed search) which can be euclidean, manhattan and chebyshev
     */
	public static void main(String[] args) {
		
		//Get arguments from command line interface
		Problem p = getProblem(args);
		Distance d = getDistance(args);
		Search s = getSearch(args, d);
		
		System.out.println(p.toString());
		
		//Let's start
		System.out.println("**** START ****");
		System.out.println("Problem: " + p.index);
		System.out.println("Algorithm: " + s.toString());
		if (s.name!=null && (s.name.equals("greedy") || s.name.equals("astar"))) {
			System.out.println("Heuristic: " + d.toString());
		}
		System.out.println();
		
		
		System.out.println("**** RUN ****");
		Node finalNode = s.run(p);
		System.out.println("**** SOLUTION ****");
		if (finalNode == null) {
			System.out.println("No solution");
		} else {
			System.out.println(finalNode.printDetail());
		}
		System.out.println("**** END ****");
	}
	
	
	/**
	 * Get arguments from CLI and return a selected problem
	 * @param args arguments from CLI
	 * @return selected problem
	 */
	public static Problem getProblem(String[] args) {
		Problem p;
		if (args.length > 0) {
			switch (args[0]) {
				case "pb1": p = new Problem(1); break;
				case "pb2": p = new Problem(2); break;
				case "pb3": p = new Problem(3); break;
				case "pb4": p = new Problem(4); break;
				case "pb5": p = new Problem(5); break;
				case "pb6": p = new Problem(6); break;
				case "pb7": p = new Problem(7); break;
				case "pb8": p = new Problem(8); break;
				case "pb9": p = new Problem(9); break;
				case "m100": 
					p = new Problem(100);
					p.resetToMultiMap(100);
					break;
				default: 
					p = new Problem(1);
					System.out.println("Not support this argument ["+args[0]+"]\nSelect default problem: [pb1]"+"\n");
					break;
			}
		} else {
			p = new Problem(1);
			System.out.println("No specific problem\nSelect default problem: [pb1]"+"\n");
		}
		
		return p;
	}

	/**
	 * Get arguments from CLI and return a selected search algorithm
	 * @param args arguments from CLI
	 * @return selected search algorithm
	 */
	public static Search getSearch(String[] args, Distance d) {
		Search s;
		if (args.length > 1) {
			switch (args[1]) {
				case "dfs": s = new DepthFirstSearch(); break;
				case "bfs": s = new BreadthFirstSearch(); break;
				case "greedy": s = new GreedySearch(d); break;
				case "astar": s = new AStarSearch(d); break;
				case "bi": s = new BidirectionalSearch((new BreadthFirstSearch()).getFrontier(), (new BreadthFirstSearch()).getFrontier()); break;
				default: 
					s = new DepthFirstSearch();
					System.out.println("Not support this argument ["+args[1]+"]\nSelect default search: "+s.toString()+"\n");
					break;
			}
		} else {
			s = new DepthFirstSearch();
			System.out.println("No specific search\nSelect default search: "+s.toString()+"\n");
		}
		
		return s;
	}
	
	public static Distance getDistance(String[] args) {
		Distance d;
		if (args.length > 2) {
			switch (args[2]) {
				case "euclidean": d = new EuclideanDistance(); break;
				case "manhattan": d = new ManhattanDistance(); break;
				case "chebyshev": d = new ChebyshevDistance(); break;
				default: d = new EuclideanDistance(); break;
			}
		} else {
			d = new EuclideanDistance();
		}
		
		// new distance for multi-agent problem.
		// TODO: implement it
		if (args.length > 2 && args[0].equals("m100")) {
			d = new MultiDistance();
		}
		
		return d;
	}

}
