package cs5011.pathfinding.search;
import java.util.ArrayList;
import java.util.LinkedList;

import cs5011.pathfinding.Problem;

public class BreadthFirstSearch extends Search{

	public BreadthFirstSearch() {
		super(new CustomQueue<Node>());		
		this.name = "bfs";
	}
	
	public double calculateCost(Problem pb, Node a, Node b) {
		return (a==null?0:a.getCost()+1);
	}
	
	public double estimateCost(Problem pb, Node a, Node b) {
		return 1;
	}
	
	@Override public String toString() {
		return "Breadth First Search algorithm";
    }
	
}

@SuppressWarnings("serial")
class CustomQueue<T> extends LinkedList<T> implements IFrontier<T>{

	@Override
	public T next() {
		return this.poll();
	}

	@Override
	public void addAll(ArrayList<T> nodes) {
		for (T node : nodes) {
			this.add(node);
		}
	}
	

}