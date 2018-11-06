package cs5011.pathfinding.problem.multi;

import cs5011.pathfinding.problem.State;

public class MultiState extends State {

	private State stateA;
	private State stateB;
	public MultiState(int index, State a, State b) {
		super(index);
		this.stateA = a;
		this.stateB = b;
	}
	
	@Override public String toString() {
		return this.stateA.toString()+":"+this.stateB.toString();
    }

}
