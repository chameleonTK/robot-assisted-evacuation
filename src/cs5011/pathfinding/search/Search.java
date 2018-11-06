package cs5011.pathfinding.search;
import java.util.*;

import cs5011.pathfinding.Problem;
import cs5011.pathfinding.problem.*;

abstract public class Search {
	
	protected Set<Node> explored;
	protected IFrontier<Node> frontier;
	public String name;
	abstract public double calculateCost(Problem pb, Node a, Node b);
	abstract public double estimateCost(Problem pb, Node a, Node b);
	
	public Search(IFrontier<Node> frontier) {
		this.frontier = frontier;
	}
	
	public Node run(Problem problem) {
		int nOfVisited = 1;
		Node initNode = new Node(problem.getInitialState());
		frontier.add(initNode);
		explored = new HashSet<Node>();
	
		this.print();
		while(!frontier.isEmpty()) {
			Node nd = frontier.next();
			nd.setVisitedIndex(nOfVisited);
			explored.add(nd);
			if (problem.isGoalState(nd.getState())) {
				this.print(nd);
				return nd;
			} else {
				ArrayList<Node> nextNode = this.expand(problem, nd);
				frontier.addAll(nextNode);
			}
			
			this.print(nd);
			nOfVisited++;
		}
		return null;
	}
	
	public ArrayList<Node> expand(Problem problem, Node now) {
		List<State> states = problem.move(now.getState());
		ArrayList<Node> successors = new ArrayList<Node>();
		for(State state: states) {
			Node next = new Node(state, now, this, problem);
			
			// For informed search
			// if node exists in frontier
			// and it does not have better cost, frontier.contains() will return true and skip it
			// and it does have better cost, frontier.contains() will return false and add it in successors
			if (this.explored.contains(next) || this.frontier.contains(next)) {
				continue;
			}
			
			successors.add(next);
		}
		
		return successors;
	}
	
	public IFrontier<Node> getFrontier() {
		return this.frontier;
	}
	
	public void print() {
		System.out.println("Frontier: " + frontier);
		System.out.println("Explored: " + explored);
		System.out.println("");
	}
	
	public void print(Node nd) {
		System.out.println("Looking at: " + nd.getState().toString());
		System.out.println("Walking on: " + nd.getPath());
		this.print();
	}
}
