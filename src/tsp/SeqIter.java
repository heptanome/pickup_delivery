package tsp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g){
		this.candidates = new Integer[unvisited.size()];
		System.out.println("taille: "+unvisited.size()+"  "+unvisited);
		for (Integer s : unvisited){
			
			if(g.isDeliveryAddress(s)) {
				List<Integer> pickupList = g.getPickUpFromDelivery(s);
				System.out.println(s+": "+pickupList);
				ListIterator<Integer> pickUpIt = pickupList.listIterator(0);
				while( pickUpIt.hasNext() && !(unvisited.contains(pickUpIt.next()) ));
				if(! pickUpIt.hasNext()) {
					candidates[nbCandidates++] = s;
				}
			} else {
				if (g.isArc(currentVertex, s))
					candidates[nbCandidates++] = s;
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
