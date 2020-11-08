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

	public Intersection(String number, float latitude, float longitude) {
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
		this.neighbours = new LinkedList<Intersection>();
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public String getNumber() {
		return this.number;
	}

	public void addNeighbour(Intersection i) {
		neighbours.add(i);
	}

	public List<Intersection> getNeighbours() {
		return neighbours;
	}

	public String toString() {
		//return "id : " + number + " {" + latitude + ", " + longitude + "}.";
		return "id : " + number;
	}
}
