package model;

public class Intersection {
	/**
	 * Default constructor
	 */

	private String number;
	private float latitude;
	private float longitude;

	public Intersection(String number, float latitude, float longitude) {
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
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
}
