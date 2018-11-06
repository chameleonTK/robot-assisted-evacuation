package cs5011.pathfinding.problem;

public class State {

//	private double cost;
	private int index;
	private String name;
	private double x;
	private double y;
	
	public State(int index) {
		this.index = index;
		this.name = State.getNameByIndex(index+1);
		this.x = 0;
		this.y = 0;
	}
	
	public State(int index, int[] coord) {
		this(index);
		if (coord.length > 0 ) {
			this.x = coord[0];
		}
		
		if (coord.length > 1 ) {
			this.y = coord[1];
		}
	}
	
	public int getIndex() {
		return this.index;
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public static String getNameByIndex(int n) {
		if (n==0) {
			return "";
		}
		
		int asciiOffset = (n-1) % 26;
		int remain = (n-1)/ 26;
		String name = Character.toString((char)('a'+asciiOffset));
		
		if (remain > 0) {
			return State.getNameByIndex(remain)+name;
		} else {
			return name;
		}
	}
	
	@Override public String toString() {
		return this.name;
    }
	
	@Override
    public boolean equals(Object obj) {
		if (!(obj instanceof State)) {
			return false;
		} 
		return this.index == ((State)obj).index;
	}
	
	@Override
    public int hashCode() {
		return this.index;
	}

}
