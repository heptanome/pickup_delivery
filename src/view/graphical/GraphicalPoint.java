package view.graphical;

import java.lang.Math;
import java.awt.*;
import model.Intersection;

/**
 * Graphical representation of an Intersection
 * Color code: white: random intersection blue : pickup point magenta : delivery
 * point yellow : departure
 */
public class GraphicalPoint {

	private int size = 8; //diameter of the point
	private int xPixel; 
	private int yPixel; 
	private Intersection point;
	private boolean isSpecial = false; 
	private Color color = Color.white;
	private final int MAP_SIZE = 800;
	private final int MAP_MARGIN = 10;

	/**
	 * Contructor
	 * @param i the intersection that will be represented by the GraphicalPoint
	 * @param minLat the minimum latitude of all the existing intersections 
	 * @param minLongi he minimum longitude of all the existing intersections
	 * @param maxLat he maximum latitude of all the existing intersections
	 * @param maxLongi the maximum longitude of all the existing intersections
	 */
	public GraphicalPoint(Intersection i, float minLat, float minLongi, float maxLat, float maxLongi) {
		float xRange = maxLat - minLat;
		float yRange = maxLongi - minLongi;

		float x = (float) ((i.getLatitude() - minLat) *  MAP_SIZE / xRange );
		float y = (float) ((i.getLongitude() - minLongi) * MAP_SIZE / yRange );

		xPixel = Math.round(x) + MAP_MARGIN - size/2;
		yPixel = Math.round(y) + MAP_MARGIN - size/2;
		point = i;
		
	}

	/**
	 * 
	 * @param x horizontal coordinate of the point clicked
	 * @param y vertical coordinate of the point clicked
	 * @return true if the (x,y) belongs to the circle of center(xPixel,yPixel) and of radius size/2
	 */
	public boolean isClicked(int x, int y){
		if(Math.sqrt( (x-(xPixel+size/2))*(x-(xPixel+size/2)) + (y-(yPixel+size/2))*(y-(yPixel+size/2)) )<= size/2){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Getter for xPixel attribute
	 * @return xPixel
	 */
	public int getXPixel() {
		return xPixel;
	}

	/**
	 * Getter for yPixel attribute
	 * @return yPixel
	 */
	public int getYPixel() {
		return yPixel;
	}
	/**
	 * Getter for point attribute
	 * @return point
	 */
	public Intersection getPoint() {
		return point;
	}

	/**
	 * Getter for color attribute
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color attribute
	 * @param c the new color
	 */
	public void setColor(Color c) {
		color = c;
		if(c == Color.white){
			isSpecial = false;
		} else {
			isSpecial = true;
		}
		
	}

	/**
	 * Getter for isSpecial attribute
	 * @return isSpecial
	 */
	public boolean getIsSpecial(){
		return isSpecial;
	}

	/**
	 * Setter of isSpecial attribute
	 * @param b the new value for isSpecial
	 */
	public void setIsSpecial(boolean b){
		isSpecial = b;
	}

	/**
	 * Getter for size attribute
	 * @return size, the diameter of the point
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Setter of size attribute
	 * @param i the new size (diameter)
	 */	
	public void setSize(int i) {
		//Recenter the point
		xPixel += (size/2 - i/2);
		yPixel += (size/2 - i/2);
		
		size = i;
	}

}