package model;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class CityMap {
	/**
	 * Default constructor
	 */
  private List<Intersection> intersections;
  private List<Segment> segments;
  private int nbVertices;
  private Map<Intersection, Integer> numberToIdMap;
  private Map<Integer, Intersection> idToNumberMap;	
	
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
	    this.segments = segments;
	    this.nbVertices = intersections.size();
	    this.numberToIdMap = new HashMap<Intersection,Integer>();
	    this.idToNumberMap = new HashMap<Integer,Intersection>();
	    this.convertNumberToId();
	}
	
	public String toString() {
	    String message = "";
	    message += "Intersections :\n";
	    for (Intersection i : intersections) {
	      message += i + "\n";
	    }
	    message += "\n\nSegments :\n";
	    for (Segment s : segments) {
	      message += s + "\n";
	    }
	    return message;
	  }
	  
	  public int getNbVertices() {
		  return this.nbVertices;
	  }
	  
	  public Map<Intersection,Integer> getNumberIdMap(){
		  return numberToIdMap;
	  }
	  
	  public int getIntFromIntersectionMap(Intersection number){
		  return numberToIdMap.get(number);
	  }
	  
	  public Intersection getIntersectionFromIdMap(int id){
		  return idToNumberMap.get(id);
	  }

	public List<Intersection> getInstersections() {
		return intersections;
	}
	
	public List<Segment> getSegments() {
		return segments;
	}
	
	public Segment getSegmentFromInter(Intersection origin, Intersection destination) {
		for(Segment s: segments) {
			if(s.getOrigin() == origin && s.getDestination() == destination)
				return s;
		}
		return null;
	}
	
	public List<String> getNeighbours(String point){
		List<String> neighbours = new LinkedList<String>();
		//parcourir tous les segments
		for (Segment s : segments) {
			if(point.equals(s.getNumberOrigin()))
				neighbours.add(s.getNumberDestination());
		}
		return neighbours;
	}
	
	private void convertNumberToId() {
		  int index = 0;
		  for(Intersection intersection : this.intersections) {
			  this.numberToIdMap.put(intersection,index);
			  this.idToNumberMap.put(index,intersection);
			  index ++;
		  }
	}
}
