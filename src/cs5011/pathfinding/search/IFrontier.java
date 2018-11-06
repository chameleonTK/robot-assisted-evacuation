package cs5011.pathfinding.search;

import java.util.ArrayList;

public interface IFrontier<E>{
	public E next();
	public boolean add(E node);
	public void addAll(ArrayList<E> nodes);
	public boolean isEmpty();	
	public boolean contains(E node);
}
