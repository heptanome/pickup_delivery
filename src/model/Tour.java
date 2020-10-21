package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import tsp.*;


public class Tour {
	public CityMap map;
	public SetOfRequests setOfRequests;
	private PropertyChangeSupport support;

	public Tour() {
		// observable object
		support = new PropertyChangeSupport(this);
		this.map = null;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	public void setMap(String mapPath) {
		CityMap oldMap = this.map;
		MapParser mp = new MapParser(mapPath);
		this.map = mp.loadMap();
		// signal the observers the map has changed
		support.firePropertyChange("updateMap", oldMap, this.map);
	}

	public void setRequests(String reqPath) {
		SetOfRequests oldReq = this.setOfRequests;
		RequestParser rp = new RequestParser(reqPath);
		this.setOfRequests = rp.loadRequests();
		// signal the observers the set of requests has changed
		support.firePropertyChange("updateRequests", oldReq, this.setOfRequests);
	}
	
	private CompleteGraph mapToCompleteGraph() {
		int vertices = map.getNbVertices();
	    Map<String,Integer> numberToIdMap =  map.getNumberIdMap();
	    List<Segment> segments= map.getSegments();
	    String[] requestNodes = setOfRequests.getRequestNodes();
	    CompleteGraph g = new CompleteGraph(vertices,numberToIdMap,segments,requestNodes);
	    return g;
	}
	
	public List<Segment> computeTour(){
		TSP tsp = new TSP1();
		CompleteGraph g = mapToCompleteGraph();
		List<Segment> path = new LinkedList<Segment>();
		long startTime = System.currentTimeMillis();
		
		tsp.searchSolution(20000, g);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		
		
		int[] solutionInt = new int[setOfRequests.getRequestNodes().length+1];
		String[] solutionString = new String[setOfRequests.getRequestNodes().length+1];
		
		for (int i=0; i<setOfRequests.getRequestNodes().length; i++)
			solutionInt[i]=tsp.getSolution(i);
		solutionInt[solutionInt.length-1] = 0;
		
		Map<Integer,String> nodeNames = g.getNodeNames();
		for(int i=0; i < solutionInt.length; i++) {
			solutionString[i] = nodeNames.get(solutionInt[i]);
		}

		List<Integer> intermediateNodes = new LinkedList<Integer>();
		for(int indexSol = 0; indexSol < solutionString.length-1; indexSol++) {
			int idOrigine = map.getIntFromNumberMap(solutionString[indexSol]);
			int idDepart = map.getIntFromNumberMap(solutionString[indexSol+1]);
			int[] precedence = g.getPrecedenceOfANode(idOrigine);
			for (int i=idDepart; i!= idOrigine; i=precedence[i]) {
				intermediateNodes.add(i);
			}

			ListIterator<Integer> iterator = intermediateNodes.listIterator(intermediateNodes.size()); 
			String currentNodeNumber = solutionString[indexSol];
			String previousNodeNumber = solutionString[indexSol];
			while(iterator.hasPrevious()){
				int previousNodeId = iterator.previous();
				previousNodeNumber = map.getStringFromIdMap(previousNodeId);
				path.add(map.getSegmentFromPoints(currentNodeNumber, previousNodeNumber));
			}
			intermediateNodes.clear();
		}
		return path;
		
	}
}
