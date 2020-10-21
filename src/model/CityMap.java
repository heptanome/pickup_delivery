package model;

import java.util.List;

public class CityMap {
	/**
	 * Default constructor
	 */
	private List<Intersection> intersections;
	private List<Segment> segments;

	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
		this.segments = segments;
	}

	public List<Intersection> getInstersections() {
		return intersections;
	}

	public List<Segment> getSegments() {
		return segments;
	}
}
