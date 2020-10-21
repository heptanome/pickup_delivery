package view;

import java.awt.*;

public class GraphicalSegment {
	private int xOriginPixel;
	private int yOriginPixel;
	private int xDestPixel;
	private int yDestPixel;
	private Color color;
	private String idOrigin;
	private String idDest;
	private int onPath;
	/*
	 * Color code: white: random segment blue : segment used by the tour
	 */

	public GraphicalSegment(String origin, String dest, int xo, int yo, int xd, int yd) {
		// +4 so that the line starts from the center of the point
		idOrigin = origin;
		idDest = dest;
		xOriginPixel = xo + 4;
		yOriginPixel = yo + 4;
		xDestPixel = xd + 4;
		yDestPixel = yd + 4;
		color = Color.white;
		onPath = 0;

	}
	
	public String getOrigin() {
		return idOrigin;
	}
	
	public int getOnPath() {
		return onPath;
	}
	
	public String getDestination() {
		return idDest;
	}

	public int getXOriginPixel() {
		return xOriginPixel;
	}

	public int getYOriginPixel() {
		return yOriginPixel;
	}

	public int getXDestPixel() {
		return xDestPixel;
	}

	public int geYDestPixel() {
		return yDestPixel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}
	
	public void setOnPath(int newOnPath) {
		onPath = newOnPath;
	}

}