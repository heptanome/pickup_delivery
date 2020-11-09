package model;

import java.awt.Color;

/**
 * An object represented by two intersections (an origin and a destination) as
 * well as a name and a length
 */
public class Segment {
	private Intersection origin;
	private Intersection destination;
	private String name;
	private float length;
	private Color color = Color.white;

	/**
	 * Constructor
	 * @param origin
	 * 			First intersection of the segment
	 * @param destination
	 * 			Second intersection of the segment
	 * @param name
	 * 			Name of the segment
	 * @param length
	 * 			Length in meter of the segment
	 */
	public Segment(Intersection origin, Intersection destination, String name, float length) {
		this.origin = origin;
		this.destination = destination;
		this.name = name;
		this.length = length;
	}

	/**
	 * Get the first intersection of a segment
	 * @return the first intersection of a segment
	 */
	public Intersection getOrigin() {
		return origin;
	}

	/**
	 * Get the address of the first intersection of a segment
	 * @return the address of the first intersection of a segment
	 */
	public String getNumberOrigin() {
		return origin.getNumber();
	}

	/**
	 * Get the second intersection of a segment
	 * @return the second intersection of a segment
	 */
	public Intersection getDestination() {
		return destination;
	}

	/**
	 * Get the address of the second intersection of a segment
	 * @return the address of the second intersection of a segment
	 */
	public String getNumberDestination() {
		return destination.getNumber();
	}

	/**
	 * Get the length of a segment
	 * @return length 
	 */
	public float getLength() {
		return this.length;
	}

	/**
	 * Convert information of a Segment to a String
	 */
	public String toString() {
		//return "Name : " + name + ", origin : " + origin.getNumber() + ", destination : " + destination.getNumber()
				//+ " (length : " + length + ").";
		return "{ "+origin.getNumber()+" ; "+destination.getNumber()+"}\n";
	}

	public String getName() {
		return name;
	}

	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}
