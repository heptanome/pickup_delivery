package tsp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * 
	 * Vertex B can not be a successor of vertex A if B is a point where one or more deliveries have to
	 * be made and all of the corresponding pickup points have not already been visited.
	 * 
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g){
		this.candidates = new Integer[unvisited.size()];
		for (Integer s : unvisited){
			if(g.isDeliveryAddress(s)) {
				List<Integer> pickupList = g.getPickUpFromDelivery(s);
				int index = 0;
				while(index < pickupList.size() && unvisited.contains(pickupList.get(index))) {
					index++;
				}
				if(index < pickupList.size()) {
					if (g.isArc(currentVertex, s)) {
						candidates[nbCandidates++] = s;
					}	
				}
			} else {
				if (g.isArc(currentVertex, s)) {
					candidates[nbCandidates++] = s;
				}
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}
