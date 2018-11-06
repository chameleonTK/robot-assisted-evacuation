package cs5011.pathfinding.search;
import java.util.ArrayList;
import java.util.List;

import cs5011.pathfinding.Problem;
import cs5011.pathfinding.problem.*;

public class Node {

	private State state;
	private Node prevNode;
	private double cost;
	private double estimateHeuristic;
	private int depth;
	private int nOfVisited;
	
	public Node(State state) {
		this.state = state;
		this.prevNode = null;
		this.cost = 0;
		this.depth = 0;
	}
	
	public Node(State state, Node prevNode, Search search, Problem problem) {
		this.state = state;
		this.prevNode = prevNode;
		this.depth = prevNode.depth+1;
		this.cost = search.calculateCost(problem, prevNode, this);
		this.estimateHeuristic = search.estimateCost(problem, prevNode, this);;
	}

	public static List<State> getTrackStates(Node n) {
		List<State> s = new ArrayList<State>();
		if (n.prevNode != null) {
			s = Node.getTrackStates(n.prevNode);
			
		}
		
		s.add(n.state);
		return s;
	}
	
	public static Node merge(Node first, Node second) {
		List<State> f = Node.getTrackStates(first);
		List<State> s = Node.getTrackStates(second);
		
		Node n = null;
		for(int i=0; i < f.size(); i++) {
			if (i==0) {
				n = new Node(f.get(i));
			} else {
				Node nextN = new Node(f.get(i));
				nextN.prevNode = n;
				n = nextN;
			}
		}
		
		for(int i=s.size()-1; i >=0; i--) {
			Node nextN = new Node(s.get(i));
			nextN.prevNode = n;
			n = nextN;
		}
		
		n.nOfVisited = first.nOfVisited;
		n.depth = first.depth + second.depth;
		n.cost = first.cost + second.cost;
		n.estimateHeuristic = first.estimateHeuristic + second.estimateHeuristic;
		
		return n;
	}
	
	public State getState() {
		return this.state;
	}
	
	public String getPath() {
		if (this.prevNode != null) {
			return this.prevNode.getPath() + " > " + this.state.toString();
		} else {
			return this.state.toString();
		}
	}
	
	public int setVisitedIndex(int n) {
		this.nOfVisited = n;
		return n;
	}
	
	public double getCost() {
		return this.cost;
	}

	public double getEstimatedCost() {
		return this.estimateHeuristic;
	}
	
	public String printDetail() {
		return String.format("Path: %s\nDepth: %d\nCost: %.2f\n#Visited: %d\n", this.getPath(), this.depth, this.cost, this.nOfVisited);
	}
	
	@Override public String toString() {
		return String.format ("%s (H:%.2f, C:%.2f)",this.state.toString(),this.getEstimatedCost(), this.getCost());
    }
	
	@Override
    public boolean equals(Object obj) {
		if (!(obj instanceof Node)) {
			return false;
		} 
		return this.state.toString().equals(((Node)obj).state.toString());
	}
	
	@Override
    public int hashCode() {
		return this.state.toString().hashCode();
	}
}
