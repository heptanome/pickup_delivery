package tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP3 extends TemplateTSP {

	@Override
	protected float bound(Integer currentVertex, Collection<Integer> unvisited) {
		/* From the current vertex, we compute dmin, the cost of the shortest arc from the current Vertex to one 
		 * of the unvisited vertices.
		 * We compute L(i), the cost of the shortest arc from i to the Origin (point 0), or to one of the
		 * unvisited vertices
		 * 
		 * The sum of dmin and L(i), for i each of  unvisited vertices, is the returned bound.
		 * 
		 * This bound method is inspired from the "TP AAIA : Branch & Bound pour le voyageur de commerce" 
		 * by Pierre-Edouard Portier, Christine Solnon and Christian Wolf we did last year.
		 * */
		float dmin = Float.MAX_VALUE;
	    float l = 0;
	    float borne = 0;
	    for(int i : unvisited){
	      l = 0;
	      int sommet = i;
	      if(g.getCost(currentVertex, sommet) < dmin)
	          dmin = g.getCost(currentVertex, i);

	        l = g.getCost(sommet, 0);
	        for(int j : unvisited){
	           int sommet2 = j;
	           if(g.getCost(sommet, sommet2)<l){
	              l = g.getCost(sommet, sommet2);
	           }
	      }
	      borne += l;
	    }
	    borne += dmin;
	  return borne;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) throws Exception {
		// TODO Auto-generated method stub
		return new SeqIter(unvisited, currentVertex, g);
	}

}
