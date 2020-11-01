package view;

import java.lang.Math;
import java.awt.*;
import model.Intersection;

public class GraphicalPoint {

	private int xPixel;
	private int yPixel;
	private String intersectionId;
	private Color color;
	/*
	 * Color code: white: random intersection blue : pickup point magenta : delivery
	 * point yellow : departure
	 */
	private int size;

	public GraphicalPoint(Intersection i, float minLat, float minLongi, float maxLat, float maxLongi) {
		float xRange = maxLat - minLat;
		float yRange = maxLongi - minLongi;

		float x = (float) ((i.getLatitude() - minLat) * 800 / xRange );
		float y = (float) ((i.getLongitude() - minLongi) * 800 / yRange );


		size = 8;

		xPixel = Math.round(x) +10 - size/2;
		yPixel = Math.round(y) +10 - size/2;
		intersectionId = i.getNumber();
		color = Color.white;
		
	}

	public int getXPixel() {
		return xPixel;
	}

	public int getYPixel() {
		return yPixel;
	}

	public String getIntersectionId() {
		return intersectionId;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int i) {
		//Recenter the point
		xPixel += (size/2 - i/2);
		yPixel += (size/2 - i/2);
		
		size = i;
	}

}