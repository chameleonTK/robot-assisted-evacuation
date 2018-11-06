package cs5011.pathfinding.problem;

import java.util.*;

public class Map {
	protected int initIndex, goalIndex;
	protected State[] states;
	protected List<List<State>> relations = new ArrayList<List<State>>();
	
	public Map(AdjacencyTable edges, Coordinate coord) {
		int[][] table = edges.getValue();
		int[][] xy = coord.getValue();
		
		states = new State[table.length];
		
		for(int i=0; i < table.length; i++) {
			int[] row = table[i];
			if (this.isStartIndex(row[i])) {
				this.initIndex = i;
			} else if (this.isExitIndex(row[i])) {
				this.goalIndex = i;
			}
			
			if (i < xy.length) {
				states[i] = new State(i, xy[i]);
			} else {
				states[i] = new State(i);
			}
		}
		
		for(int i=0; i < table.length; i++) {
			int[] row = table[i];
			relations.add(new ArrayList<State>());
			
			for(int j=0; j < row.length; j++) {
				if (this.isConnected(row[j])) {
					relations.get(i).add(states[j]);
				}
			}
		}
	}
	
	protected boolean isStartIndex(int value) {
		return value == 2;
	}
	
	protected boolean isExitIndex(int value) {
		return value == 8;
	}
	
	protected boolean isConnected(int value) {
		return value == 5;
	}
	
	public State getInitialState() {
		return this.states[this.initIndex];
	}
	
	public State getGoalState() {
		return this.states[this.goalIndex];
	}

	public List<State> getNextStates(State state) {
		return this.relations.get(state.getIndex());
	}
	
	@Override public String toString() {
		String s = "";
		s += "Start: " + this.getInitialState() + "\n";
		s += "Final: " + this.getGoalState() + "\n";
		s += "Map: ";
		for(int i=0; i< this.relations.size(); i++) {
			List<State> nextStates = this.relations.get(i);
			
			s+= "\t"+this.states[i]+" => ";
			for (State state: nextStates) {
				s += state+" ";
			}
			s+="\n";
		}
		
		return s;
    }

}
