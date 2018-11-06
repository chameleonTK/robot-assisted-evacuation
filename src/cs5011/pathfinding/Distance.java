package cs5011.pathfinding;

import cs5011.pathfinding.problem.State;

public interface Distance {
	public double measure(State a, State b);
}

class EuclideanDistance implements Distance{
	public double measure(State a, State b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
	}
	
	@Override public String toString() {
		return "Euclidean distance";
    }
}

class ManhattanDistance implements Distance{
	public double measure(State a, State b) {
		if (a.equals(new State(4)) && b.equals(new State(4))){
			System.out.println(Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()));
		}
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}
	
	@Override public String toString() {
		return "Manhattan distance";
    }
}

class ChebyshevDistance implements Distance{
	public double measure(State a, State b) {
		return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
	}
	
	@Override public String toString() {
		return "Chebyshev distance";
    }
}
