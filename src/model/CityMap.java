package model;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CityMap {
	/**
	 * Default constructor
	 */
  private List<Intersection> intersections;
  private List<Segment> segments;
  private int nbVertices;
  private Map<String, Integer> numberToIdMap;
  private Map<Integer, String> idToNumberMap;	
	
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
	    this.segments = segments;
	    this.nbVertices = intersections.size();
	    this.numberToIdMap = new HashMap<String,Integer>();
	    this.idToNumberMap = new HashMap<Integer,String>();
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
	  
	  public Map<String,Integer> getNumberIdMap(){
		  return numberToIdMap;
	  }
	  
	  public int getIntFromNumberMap(String number){
		  return numberToIdMap.get(number);
	  }
	  
	  public String getStringFromIdMap(int id){
		  return idToNumberMap.get(id);
	  }

	public List<Intersection> getInstersections() {
		return intersections;
	}
	
	public List<Segment> getSegments() {
		return segments;
	}
	
	public Segment getSegmentFromPoints(String origin, String destination) {
		for(Segment s: segments) {
			if(s.getNumberOrigin().equals(origin) && s.getNumberDestination().equals(destination))
				return s;
		}
		return null;
	}
	
	private void convertNumberToId() {
		  int index = 0;
		  for(Intersection intersection : this.intersections) {
			  this.numberToIdMap.put(intersection.getNumber(),index);
			  this.idToNumberMap.put(index,intersection.getNumber());
			  index ++;
		  }
	}
}
