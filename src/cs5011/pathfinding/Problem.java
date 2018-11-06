package cs5011.pathfinding;
import java.util.*;

import cs5011.pathfinding.problem.*;
import cs5011.pathfinding.problem.Map;
import cs5011.pathfinding.problem.multi.MultiAgentMap;

public class Problem {

	private Map map;
	private AdjacencyTable edges;
	private Coordinate coord;
	public int index;
	
	public Problem(int index) {
		this.index = index;
		this.edges = new AdjacencyTable(index);
		this.coord = new Coordinate(index);
		this.map = new Map(edges, coord);
	}

	public boolean isInitState(State state) {
		return state.equals(this.map.getInitialState());
	}
	
	public boolean isGoalState(State state) {
		return state.equals(this.map.getGoalState());
	}

	public State getInitialState() {
		return this.map.getInitialState();
	}
	
	public State getGoalState() {
		return this.map.getGoalState();
	}

	public List<State> move(State state) {
		return this.map.getNextStates(state);
	}

	public void resetToMultiMap(int index) {
		this.index = index;
		this.edges = new AdjacencyTable(index);
		this.coord = new Coordinate(index);
		this.map = new MultiAgentMap(edges, coord);
	}
	
	@Override public String toString() {
		return this.map.toString();
    }
}
