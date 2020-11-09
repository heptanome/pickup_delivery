package model;

import java.util.LinkedList;
import java.util.List;

/**
 * The class representing an intersection, which is a combination of a latitude,
 * a longitude, a number (or id) and neighbours
 */
public class Intersection {

	private String number;
	private float latitude;
	private float longitude;
	private List<Intersection> neighbours;

	/**
	 * Constructor
	 * Create an intersection with an empty list of neighbours
	 * A neighbour is an other intersection closed to this one
	 * @param number
	 * 			Number of an intersection (<String>)
	 * @param latitude
	 * 			Latitude of the intersection
	 * @param longitude
	 * 			Longitude of the intersection
	 */
	public Intersection(String number, float latitude, float longitude) {
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
		this.neighbours = new LinkedList<Intersection>();
	}

	/**
	 * Get the latitude of a request
	 * @return latitude of a request
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Get the longitude of a request
	 * @return longitude of a request
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Get the number of a request
	 * @return number of a request
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * Add a neighbour to the list of neighbours
	 * @param intersection
	 * 			Neighbour to add
	 */
	public void addNeighbour(Intersection intersection) {
		neighbours.add(intersection);
	}

	/**
	 * Get the neighbours of a request
	 * A neighbour is an other intersection closed to this intersection
	 * @return a list of the neighbours of the request
	 */
	public List<Intersection> getNeighbours() {
		return neighbours;
	}

	/**
	 * Convert information of an Intersection to a String
	 */
	public String toString() {
		return "id : " + number + " {" + latitude + ", " + longitude + "}.";
	}
}
