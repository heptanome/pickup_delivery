package tsp;

import java.util.Collection;
import java.util.Iterator;


public class TSP1 extends TemplateTSP {
	@Override
	protected float bound(Integer currentVertex, Collection<Integer> unvisited) {
		/*Useless bound method, used in the first iteration*/
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
