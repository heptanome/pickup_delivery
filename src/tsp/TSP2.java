package tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {

	@Override
	protected float bound(Integer currentVertex, Collection<Integer> unvisited) {
		/*
		 * Simple bound method :
		 * we find the shortest arc of the graph
		 * the remaining cost is superior, or equal to, this cost multiplied by the number of
		 * remaining vertex to visit + 1 (because we need to go back at the origin)
		 * 
		 * This bound method is inspired from the "TP AAIA : Branch & Bound pour le voyageur de commerce" 
		 * by Pierre-Edouard Portier, Christine Solnon and Christian Wolf we did last year.
		 */
		return (unvisited.size()+1 * g.minArcCost());
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) throws Exception{
		return new SeqIter(unvisited, currentVertex, g);
	}

}
