package cs5011.pathfinding.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs5011.pathfinding.Problem;
import cs5011.pathfinding.problem.State;

public class BidirectionalSearch extends Search {

	protected Set<Node> explored1, explored2;
	protected IFrontier<Node> frontier1, frontier2;
	public String name;
	public BidirectionalSearch(IFrontier<Node> frontier1, IFrontier<Node> frontier2) {
		super(frontier1);
		this.name = "bi";
		this.frontier1 = frontier1;
		this.frontier2 = frontier2;
	}

	public Node run(Problem problem) {
		int nOfVisited = 1;
		Node initNode = new Node(problem.getInitialState());
		frontier1.add(initNode);
		explored1 = new HashSet<Node>();
		
		Node goalNode = new Node(problem.getGoalState());
		frontier2.add(goalNode);
		explored2 = new HashSet<Node>();
	
		this.print();
		while(!frontier1.isEmpty() || !frontier2.isEmpty()) {
			//From TOP >> BOTTOM
			if (!frontier1.isEmpty()) {
				Node nd = frontier1.next();
				nd.setVisitedIndex(nOfVisited);
				explored1.add(nd);
				if (problem.isGoalState(nd.getState())) {
					this.print(nd);
					return nd;
				} else if(this.explored2.contains(nd)) {
					///Merge two path together
					System.out.println("XXXXXX1");
					for(Node h: this.explored2) {
						if (h.equals(nd)) {
							Node m = Node.merge(nd, h);
							this.print(m);
							return m;
						}
					}

					return nd;
				} else {
					ArrayList<Node> nextNode = this.expand(problem, nd, 1);
					frontier1.addAll(nextNode);
				}
				
				this.print(nd);
				nOfVisited++;
			}
			
			//From BOTTOM >> TOP
			if (!frontier2.isEmpty()) {
				Node nd = frontier2.next();
				nd.setVisitedIndex(nOfVisited);
				explored2.add(nd);
				if (problem.isInitState(nd.getState())) {
					this.print(nd);
					return nd;
				} else if(this.explored1.contains(nd)) {
					///Merge two path together
					System.out.println("XXXXXX2");
					for(Node h: this.explored1) {
						if (h.equals(nd)) {
							Node m = Node.merge(h, nd);
							this.print(m);
							return m;
						}
					}
					
					return nd;
				} else {
					ArrayList<Node> nextNode = this.expand(problem, nd, 2);
					frontier2.addAll(nextNode);
				}
				
				this.print(nd);
				nOfVisited++;
			}
		}
		return null;
	}
	
	public ArrayList<Node> expand(Problem problem, Node now, int frontierIndex) {
		List<State> states = problem.move(now.getState());
		ArrayList<Node> successors = new ArrayList<Node>();
		for(State state: states) {
			Node next = new Node(state, now, this, problem);
			if (frontierIndex == 1) {
				if (this.explored1.contains(next) || this.frontier1.contains(next)) {
					continue;
				}
			} else {
				if (this.explored2.contains(next) || this.frontier2.contains(next)) {
					continue;
				}
			}
			
			
			successors.add(next);
		}
		
		return successors;
	}
	
	public void print() {
		System.out.println("");
		System.out.println("Frontier1: " + frontier1);
		System.out.println("Explored1: " + explored1);
		System.out.println("");
		System.out.println("Frontier2: " + frontier2);
		System.out.println("Explored2: " + explored2);
		System.out.println("");
	}
	
	public void print(Node nd) {
		System.out.println("Looking at: " + nd.getState().toString());
		System.out.println("Walking on: " + nd.getPath());
		this.print();
		System.out.println("--------------------");
	}
	
	public double calculateCost(Problem pb, Node a, Node b) {
		return (a==null?0:a.getCost()+1);
	}
	
	public double estimateCost(Problem pb, Node a, Node b) {
		return 1;
	}
	
	@Override public String toString() {
		return "Bidirectional search algorithm";
    }

}
