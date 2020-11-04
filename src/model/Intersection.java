package model;

import java.util.LinkedList;
import java.util.List;

public class Intersection {
	/**
	 * Default constructor
	 */

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

	public String toString() {
		return "id : " + number + " {" + latitude + ", " + longitude + "}.";
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
	
	public List<Intersection> getNeighbours(){
		return neighbours;
	}
}
