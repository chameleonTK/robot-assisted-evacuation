package cs5011.pathfinding.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import cs5011.pathfinding.Distance;

abstract public class InformedSearch extends Search{

	protected Distance heuristic;
	static Comparator<Node> comp = new Comparator<Node>() {
        @Override
        public int compare(Node s1, Node s2) {
            return (s1.getEstimatedCost() - s2.getEstimatedCost() > 0? 1:-1);
        }
    };
    
	public InformedSearch(Distance heuristic) {
		super(new CustomPriorityQueue<Node>(InformedSearch.comp));
		this.heuristic = heuristic;
	}
}

@SuppressWarnings("serial")
class CustomPriorityQueue<T> extends PriorityQueue<T> implements IFrontier<T>{

	public CustomPriorityQueue(Comparator<T> comp) {
		super(comp);
	}

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
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object newNode) {
		boolean itContains = super.contains(newNode);
		if (itContains) {
			//In case that there is duplicated node in frontier
			//If it has better cost, it will be treat as an undiscovered node
			//If it has lower cost, it will be treat as an discovered node and ignore that node 
			
			//return false; means newNode does not contain in frontier and the search will ignore it
			//return true; means newNode contains in frontier and the search will add it into queue
			for (T e : this) {
				if (e.equals(newNode)) {
					if (this.comparator().compare(e, (T)newNode) >=1) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override public String toString() {
		T[] nodes = (T[])this.toArray();
		Arrays.sort(nodes, this.comparator());
		String s = "[";
		for(int i=0; i < nodes.length; i++) {
			if (i!=nodes.length-1) {
				s+= nodes[i].toString()+", ";
			} else {
				s+= nodes[i].toString();
			}
		}
		
		s += "]";
		return s;
    }
}