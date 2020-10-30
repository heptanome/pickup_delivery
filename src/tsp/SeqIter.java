package tsp;

import java.util.Collection;
import java.util.Iterator;

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
		System.out.println("Candidats de "+currentVertex+" pour unvisited: "+unvisited);
		for (Integer s : unvisited){
			if(g.isDeliveryAddress(s)) {
				System.out.println(s+" est un delivery point.");
				int pickup = g.getPickUpFromDelivery(s);
				if (g.isArc(currentVertex, s) && !(unvisited.contains(pickup)) )
					candidates[nbCandidates++] = s;
				
			} else {
				if (g.isArc(currentVertex, s))
					candidates[nbCandidates++] = s;
			}
		}
		
		for(int i = 0; i < nbCandidates; i++) {
			System.out.print(candidates[i]+"  ");
		}
		System.out.println();
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
