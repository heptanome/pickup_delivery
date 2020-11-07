package view;

import java.awt.*;


/**
 * Graphical representation of a Segment
 * Color code: white: random segment ; red : segment used by the tour
 */
public class GraphicalSegment {
	private GraphicalPoint origin;
	private GraphicalPoint destination;
	private int xOriginPixel;
	private int yOriginPixel;
	private int xDestPixel;
	private int yDestPixel;
	private Color color = Color.white;
	private int onPath = 0;

	/**
	 * Constructor
	 * @param o the GraphicalPoint representing the origin Intersection
	 * @param d the GraphicalPoint representing the destination Intersection
	 * 
	 */
	public GraphicalSegment(GraphicalPoint o, GraphicalPoint d) {
		origin = o;
		destination = d;
		// +4 so that the line starts from the center of the point
		xOriginPixel = o.getXPixel() + 4;
		yOriginPixel = o.getYPixel() + 4;
		xDestPixel = d.getXPixel() + 4;
		yDestPixel = d.getYPixel() + 4;

	}

	/**
	 * Getter for the address number of the origin intersection
	 * @return the address number of the intersection represented by the origin attribute
	 */	
	public String getOrigin() {
		return origin.getPoint().getNumber();
	}
	
	/**
	 * Getter for onPath attribute
	 * @return onPath
	 */		
	public int getOnPath() {
		return onPath;
	}

	/**
	 * Getter for the address number of the destination intersection
	 * @return the address number of the intersection represented by the destination attribute
	 */		
	public String getDestination() {
		return destination.getPoint().getNumber();
	}

	/**
	 * Getter for XOriginPixel attribute
	 * @return XOriginPixel
	 */	
	public int getXOriginPixel() {
		return xOriginPixel;
	}

	/**
	 * Getter for YOriginPixel attribute
	 * @return YOriginPixel
	 */	
	public int getYOriginPixel() {
		return yOriginPixel;
	}

	/**
	 * Getter for XDestPixel attribute
	 * @return XDestPixel
	 */	
	public int getXDestPixel() {
		return xDestPixel;
	}

	/**
	 * Getter for YDestPixel attribute
	 * @return YDestPixel
	 */	
	public int geYDestPixel() {
		return yDestPixel;
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
	}

	/**
	 * Setter for onPath attribute
	 * @param newOnPath the new onPath
	 */	
	public void setOnPath(int newOnPath) {
		onPath = newOnPath;
	}

}