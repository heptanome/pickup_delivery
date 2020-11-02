package model;

public class Segment extends Object {
	/**
	 * Default constructor
	 */

	private Intersection origin;
	private Intersection destination;
	private String name;
	private float length;

	public Segment(Intersection origin, Intersection destination, String name, float length) {
		this.origin = origin;
		this.destination = destination;
		this.name = name;
		this.length = length;
	}

	public String toString() {
		return "Name : " + name + ", origin : " + origin.getNumber() + ", destination : " + destination.getNumber() + " (length : " + length
				+ ").";
	}

	public String getNumberOrigin() {
		return origin.getNumber();
	}

	public String getNumberDestination() {
		return destination.getNumber();
	}
	
	public float getLength() {
		  return this.length;
	}
}
