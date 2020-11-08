package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class is representing a map by keeping in memory a loaded map XML file. It
 * mostly uses a list of intersections and a list of segments.
 *
 */
public class CityMap {
	private List<Intersection> intersections;
	private List<Segment> segments;
	private int nbVertices;
	private Map<Intersection, Integer> numberToIdMap;
	private Map<Integer, Intersection> idToNumberMap;

	/**
	 * Building a map according to its most important values
	 * 
	 * @param intersections all intersections retrieved from parsing
	 * @param segments      all segments retrieved from parsing
	 */
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
		this.segments = segments;
		this.nbVertices = intersections.size();
		this.numberToIdMap = new HashMap<Intersection, Integer>();
		this.idToNumberMap = new HashMap<Integer, Intersection>();
		this.convertNumberToId();
	}

	/**
	 * From two intersections this method finds the segment it represents on the map
	 * 
	 * @param origin
	 * @param destination
	 * @return a segment if found, null otherwise
	 */
	public Segment getSegmentFromInter(Intersection origin, Intersection destination) {
		for (Segment s : segments) {
			if (s.getOrigin() == origin && s.getDestination() == destination)
				return s;
		}
		return null;
	}

	/**
	 * From the intersections, populate two java <Map> objects to easily retrieve
	 * them afterwards
	 * Number is a unique <String>
	 * Id is a unique <int>, used to compute the shortest path
	 */
	private void convertNumberToId() {
		int index = 0;
		for (Intersection intersection : this.intersections) {
			this.numberToIdMap.put(intersection, index);
			this.idToNumberMap.put(index, intersection);
			index++;
		}
	}

	public int getNbVertices() {
		return this.nbVertices;
	}

	public Map<Intersection, Integer> getNumberIdMap() {
		return numberToIdMap;
	}

	public int getIntFromIntersectionMap(Intersection number) {
		return numberToIdMap.get(number);
	}

	public Intersection getIntersectionFromIdMap(int id) {
		return idToNumberMap.get(id);
	}

	public List<Intersection> getInstersections() {
		return intersections;
	}

	public List<Segment> getSegments() {
		return segments;
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
}
