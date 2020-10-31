package tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP3 extends TemplateTSP {

	@Override
	protected float bound(Integer currentVertex, Collection<Integer> unvisited) {
		float dmin = Float.MAX_VALUE;
	    float l = 0;
	    float borne = 0;
	    for(int i : unvisited){ //Pour chaque sommet appartenant à NonVus
	      l = 0;
	      int sommet = i;
	      if(g.getCost(currentVertex, sommet) < dmin) //recherche du plus petit arc partant du dernier sommet
	          dmin = g.getCost(currentVertex, i);

	        l = g.getCost(sommet, 0); //recherche du plus petit arc partant du sommet en cours d'étude
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
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		// TODO Auto-generated method stub
		return new SeqIter(unvisited, currentVertex, g);
	}

}
