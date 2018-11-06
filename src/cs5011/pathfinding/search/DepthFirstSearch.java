package cs5011.pathfinding.search;

import java.util.ArrayList;
import java.util.Stack;

import cs5011.pathfinding.Problem;

public class DepthFirstSearch extends Search{

	public DepthFirstSearch() {
		super(new CustomStack<Node>());
		this.name = "dfs";
	}
	
	public double calculateCost(Problem pb, Node a, Node b) {
		return (a==null?0:a.getCost()+1);
	}
	
	public double estimateCost(Problem pb, Node a, Node b) {
		return 1;
	}
	
	@Override public String toString() {
		return "Depth First Search algorithm";
    }
}

@SuppressWarnings("serial")
class CustomStack<T> extends Stack<T> implements IFrontier<T>{

	@Override
	public T next() {
		return this.pop();
	}

	@Override
	public boolean add(T node) {
		T newNode = this.push(node);
		if (newNode == null) {
			return false;
		}
		return true;
	}

	@Override
	public void addAll(ArrayList<T> nodes) {
		for(int i=nodes.size()-1; i>=0; i--) {
			this.push(nodes.get(i));
		}
	}
	

}

