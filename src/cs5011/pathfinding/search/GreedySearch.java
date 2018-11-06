package cs5011.pathfinding.search;

import cs5011.pathfinding.Distance;
import cs5011.pathfinding.Problem;

public class GreedySearch extends InformedSearch{
	
	public GreedySearch(Distance heuristic) {
		super(heuristic);
		this.name = "greedy";
	}

	public double calculateCost(Problem pb, Node prevNode, Node nextNode) {
		if (prevNode != null) {
			return prevNode.getCost() + this.heuristic.measure(prevNode.getState(), nextNode.getState());
		} else {
			return 0;
		}
	}
	
	public double estimateCost(Problem pb, Node prevNode, Node nextNode) {
		return this.heuristic.measure(pb.getGoalState(), nextNode.getState());
	}
	
	@Override public String toString() {
		return "Greedy Search algorithm";
    }
}