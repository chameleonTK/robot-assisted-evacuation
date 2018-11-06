package cs5011.pathfinding.problem.multi;
import java.util.ArrayList;
import java.util.List;

import cs5011.pathfinding.problem.*;

public class MultiAgentMap extends Map {

	protected State[] baseStates;
	protected int N;
	protected int numberOfNode;
	
	public MultiAgentMap(AdjacencyTable edges, Coordinate coord) {
		super(edges, coord);
		
		int[][] table = edges.getValue();
		int[][] xy = coord.getValue();
		
		this.N = table.length;
		this.numberOfNode = (this.N*(this.N));
		
		this.states = new State[this.numberOfNode];
		this.baseStates = new State[this.N];
		
		for(int i=0; i < table.length; i++) {
			if (i < xy.length) {
				this.baseStates[i] = new State(i, xy[i]);
			} else {
				this.baseStates[i] = new State(i);
			}
		}
		
		//Generate muliState and initial relation list
		this.relations = new ArrayList<List<State>>();
		for(int i=0; i < table.length; i++) {
			for(int j=0; j < table.length; j++) {
				int pos = this.mergeTwoIndex(i, j);
				this.states[pos] = new MultiState(pos, baseStates[j], baseStates[i]);
				this.relations.add(new ArrayList<State>());
			}
		}
		
		
		//Add connection between each multiState
		for(int i=0; i < table.length; i++) {
			for(int j=i; j < table.length; j++) {
				this.addJointConnection(edges, i ,j);
			}
		}
		
		//Determine init state and goal state
		int goal = -1;
		int start = -1;
		this.goalIndex = -1;
		this.initIndex = -1;
		
		for(int i=0; i < table.length; i++) {
			if (this.isExitIndex(table[i][i])) {
				if (goal!=-1) {
					this.goalIndex = this.mergeTwoIndex(i, goal);
				} else {
					this.goalIndex = this.mergeTwoIndex(i, i);
				}
				goal = i;
			}
			
			if (this.isStartIndex(table[i][i])) {
				if (start!=-1) {
					this.initIndex = this.mergeTwoIndex(i, start);
				} else {
					//TODO: in this case; search is not work because node AA, BB, CC, DD, ... are not added into map.
					this.initIndex = this.mergeTwoIndex(i, i);
				}
				start = i;
			}
		}
	}
	
	private List<State> addJointConnection(AdjacencyTable edges, int i, int j) {
		int[][] table = edges.getValue();
		List<State> lst = this.relations.get(this.mergeTwoIndex(i, j));
		
		List<State> lstA = new ArrayList<State>();
		List<State> lstB = new ArrayList<State>();
		// List all nodes that connected from state[i]
		for(int k=0; k < this.N; k++) {
			if (this.isConnected(table[i][k])) {
				lstA.add(this.baseStates[k]);
			}
		}
		
		// List all nodes that connected from state[j]
		for(int k=0; k < this.N; k++) {
			if (this.isConnected(table[j][k])) {
				lstB.add(this.baseStates[k]);
			}
		}
		
		// combine together
		for(State a: lstA) {
			for(State b: lstB) {
				if (!a.equals(b) || this.isExitIndex(table[a.getIndex()][a.getIndex()])) {
					int connectedIndex = this.mergeTwoIndex(a.getIndex(), b.getIndex());
					lst.add(this.states[connectedIndex]);
				}
			}
		}
		
		//in case that allow to move only one agent
		for(State a: lstA) {
			if (!a.equals(this.baseStates[j]) || this.isExitIndex(table[a.getIndex()][a.getIndex()])) {
				int connectedIndex = this.mergeTwoIndex(a.getIndex(), j);
				lst.add(this.states[connectedIndex]);
			}
		}
		
		for(State b: lstB) {
			if (!b.equals(this.baseStates[i]) || this.isExitIndex(table[b.getIndex()][b.getIndex()])) {
				int connectedIndex = this.mergeTwoIndex(b.getIndex(), i);
				lst.add(this.states[connectedIndex]);
			}
		}
		
		
		return this.relations.get(this.mergeTwoIndex(i, j));
	}
	
	public int mergeTwoIndex(int i, int j) {
		//All states map into AB, AC, AD, ... ; followed by (repeated state) AA BB CC DD
		
		if (i==j) {
			return (this.N*(this.N - 1)) +i;
		} else if (i<j){
			return i*this.N + j;
		} else {
			return j*this.N + i;
		}
	}
	
	protected boolean isStartIndex(int value) {
		return (value == 2) || (value == 5);
	}
	
	@Override public String toString() {
		String s = "";
		s += "Start: " + this.getInitialState() + "\n";
		s += "Final: " + this.getGoalState() + "\n";
		s += "Map: ";
		for(int i=0; i< this.relations.size(); i++) {
			List<State> nextStates = this.relations.get(i);
			
			if (this.states[i]==null) {
				continue;
			}
			s+= "\t"+this.states[i]+" => ";
			for (State state: nextStates) {
				s += state+" ";
			}
			s+="\n";
		}
		
		return s;
    }

}
