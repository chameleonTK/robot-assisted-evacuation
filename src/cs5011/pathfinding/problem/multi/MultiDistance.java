package cs5011.pathfinding.problem.multi;
import cs5011.pathfinding.Distance;
import cs5011.pathfinding.problem.State;

public class MultiDistance implements Distance{
	
	public double measure(State a, State b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	@Override public String toString() {
		return "Mulit-agent Euclidean distance";
    }
}
