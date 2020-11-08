package model;

/**
 * An object represented by two intersections (an origin and a destination) as
 * well as a name and a length
 */
public class Segment {
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

	public Intersection getOrigin() {
		return origin;
	}

	public String getNumberOrigin() {
		return origin.getNumber();
	}

	public Intersection getDestination() {
		return destination;
	}

	public String getNumberDestination() {
		return destination.getNumber();
	}

	public float getLength() {
		return this.length;
	}

	public String toString() {
		//return "Name : " + name + ", origin : " + origin.getNumber() + ", destination : " + destination.getNumber()
				//+ " (length : " + length + ").";
		return "{origin : " + origin.getNumber() + ";destination : " + destination.getNumber()+"}\n";
	}
}
