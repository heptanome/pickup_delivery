package tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {

	@Override
	protected float bound(Integer currentVertex, Collection<Integer> unvisited) {
		/*
		 * Fonction d'évaluation simple :
		 * On détermine le plus petit arc du graphe
		 * on renvoie cette plus petite longueur multipliée par le nombre de sommets restant 
		 * à visiter*/
		return (unvisited.size()+1 * g.minArcCost());
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		// TODO Auto-generated method stub
		return new SeqIter(unvisited, currentVertex, g);
	}

}
