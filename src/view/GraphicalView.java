package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;
import view.graphical.GraphicalPoint;
import view.graphical.GraphicalSegment;

/**
 * Class used to to display the intersections, the segments, the requests and
 * the tour graphically, i.e. on a map
 */
public class GraphicalView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color depotColor = Color.yellow;
	private static final Color pickupColor = Color.blue;
	private static final Color deliveryColor = Color.magenta;
	private static final Color bgColor = new Color(0xc1c3c6);

	private final int GV_WIDTH = 820;
	private final int GV_HEIGHT = 840; // Larger than 800x800 to have margins

	private List<Intersection> intersections;
	private List<Segment> segments;
	private float minLat;
	private float minLongi;
	private float maxLat;
	private float maxLongi;

	private List<GraphicalPoint> graphicalPoints;
	private Intersection selectedPoint;
	private List<GraphicalSegment> graphicalSegments;
	private HashMap<String, GraphicalPoint> graphicalPointMap;

	/**
	 * Constructor
	 * 
	 * @param loadedMap the cityMap containing the Intersections and Segments to
	 *                  display
	 */
	public GraphicalView(CityMap loadedMap) {
		setLayout(null);
		setBounds(0, 0, GV_WIDTH, GV_HEIGHT);
		graphicalPoints = new LinkedList<GraphicalPoint>();
		selectedPoint = null;
		graphicalSegments = new LinkedList<GraphicalSegment>();
		minLat = Float.POSITIVE_INFINITY;
		minLongi = Float.POSITIVE_INFINITY;
		maxLat = 0;
		maxLongi = 0;
		graphicalPointMap = new HashMap<>();

		if (loadedMap != null) {
			intersections = loadedMap.getInstersections();
			segments = loadedMap.getSegments();
			setExtemeCoordinates();
			displayMap();
		}

	}

	/**
	 * Paints the component
	 */
	public void paint(Graphics g) {
		// Background
		g.setColor(bgColor);
		g.fillRect(0, 0, 820, 820);

		// Draw segments
		List<GraphicalSegment> coloredSegments = new LinkedList<GraphicalSegment>(); // To store the colored
																						// segments and draw them
																						// at the end
		Graphics2D g2d = (Graphics2D) g;
		Stroke normalRoad = new BasicStroke(1f);
		Stroke importantRoad = new BasicStroke(5f);
		g2d.setStroke(normalRoad);
		for (GraphicalSegment gs : graphicalSegments) {
			if (gs != null) {
				if (gs.getOnPath() == 1) {
					coloredSegments.add(gs);
				}
				g2d.setColor(gs.getColor());
				g2d.drawLine(gs.getXOriginPixel(), gs.getYOriginPixel(), gs.getXDestPixel(), gs.geYDestPixel());
			}
		}
		// Draw colored segments (those on the tour) on top
		g2d.setStroke(importantRoad);
		for (GraphicalSegment gs : coloredSegments) {
			g2d.setColor(gs.getColor());
			g2d.drawLine(gs.getXOriginPixel(), gs.getYOriginPixel(), gs.getXDestPixel(), gs.geYDestPixel());
		}

		// Draw all intersections
		List<GraphicalPoint> coloredIntersections = new LinkedList<GraphicalPoint>(); // To store the colored
																						// intersections and draw them
																						// at the end
		for (GraphicalPoint gp : graphicalPoints) {
			if (gp.getColor() == Color.white) {
				g.setColor(Color.white);
				g.fillOval(gp.getXPixel(), gp.getYPixel(), gp.getSize(), gp.getSize());
			} else {
				coloredIntersections.add(gp);
			}
		}
		// Draw colored intersections (special ones)
		for (GraphicalPoint gp : coloredIntersections) {
			g.setColor(gp.getColor());
			g.fillOval(gp.getXPixel(), gp.getYPixel(), gp.getSize(), gp.getSize());
		}

	}

	/**
	 * Method called to set the Graphical points and segments to display on the map.
	 */
	public void displayMap() {
		// Graphical points
		for (Intersection i : intersections) {
			GraphicalPoint gp = new GraphicalPoint(i, minLat, minLongi, maxLat, maxLongi);
			graphicalPoints.add(gp);
			graphicalPointMap.put(i.getNumber(), gp);
		}

		// Graphical segments
		for (Segment s : segments) {
			GraphicalSegment gs = createSegment(s.getNumberOrigin(), s.getNumberDestination());
			if (gs != null) {
				graphicalSegments.add(gs);
			}
		}
	}

	/**
	 * Sets the Segments that belong to the Tour to be displayed in red and bigger.
	 * 
	 * @param segments the list of Segment that belong to the Tour
	 */
	public void displayTour(List<Segment> segments) {
		// Clear en eventual current tour (display on the map)
		clearTourOnMap();
		clearSelectedPoint();

		// Change color of corresponding segments
		int segSize = graphicalSegments.size();
		segments.forEach(s -> {
			int j = 0;
			while (j < segSize) {
				GraphicalSegment seg = graphicalSegments.get(j);
				if (s.getNumberOrigin().equals(seg.getOrigin())
						&& s.getNumberDestination().equals(seg.getDestination())) {
					seg.setOnPath(1);
					seg.setColor(s.getColor());
					break;
				}
				j++;
			}
		});

		repaint();

	}

	/**
	 * Sets the color and size of the GraphicalPoints that are part of the loaded
	 * SetOfRequests
	 * 
	 * @param sr the SetOfRequests, containing the the intersections to highlight
	 */
	public void displayRequests(SetOfRequests sr) {
		// Reset the map
		clearRequestsOnMap();
		clearTourOnMap();
		clearSelectedPoint();

		if (sr != null) {
			// Look for the departure point
			int i = 0;
			boolean found = false;
			while (i < graphicalPoints.size() && !found) {
				if (sr.getDepotAddress().equals(graphicalPoints.get(i).getPoint().getNumber())) {
					graphicalPoints.get(i).setColor(depotColor);
					graphicalPoints.get(i).setSize(12);
					found = true;
				}
				i++;
			}

			// Change the color of pickup and delivery points
			for (Request r : sr.getRequests()) {
				i = 0;
				boolean dFound = false;
				boolean pFound = false;
				while (i < graphicalPoints.size() && (!pFound || !dFound)) {
					if (r.getPickupAddress().equals(graphicalPoints.get(i).getPoint().getNumber())) {
						graphicalPoints.get(i).setColor(pickupColor);
						graphicalPoints.get(i).setSize(12);
						pFound = true;
					} else if (r.getDeliveryAddress().equals(graphicalPoints.get(i).getPoint().getNumber())) {
						graphicalPoints.get(i).setColor(deliveryColor);
						graphicalPoints.get(i).setSize(16);
						dFound = true;
					}
					i++;
				}
			}
		}

		repaint();
	}

	/**
	 * Goes over all of the intersections of the map to set the exteme coordinates,
	 * in order to later set the scale of the map.
	 */
	public void setExtemeCoordinates() {
		for (Intersection i : intersections) {
			if (i.getLatitude() < minLat) {
				minLat = i.getLatitude();
			} else if (i.getLatitude() > maxLat) {
				maxLat = i.getLatitude();
			}

			if (i.getLongitude() < minLongi) {
				minLongi = i.getLongitude();
			} else if (i.getLongitude() > maxLongi) {
				maxLongi = i.getLongitude();
			}
		}
	}

	/**
	 * Creates a graphical segment
	 * 
	 * @param idOrigin      the id of the origin Intersection
	 * @param idDestination the id of the destination Intersection
	 * @return the GraphicalSegment created
	 */
	public GraphicalSegment createSegment(String idOrigin, String idDestination) {
		// Go through the list to find the intersection
		GraphicalPoint origin = graphicalPointMap.get(idOrigin);
		GraphicalPoint destination = graphicalPointMap.get(idDestination);

		if (origin != null && destination != null) {
			GraphicalSegment gs = new GraphicalSegment(origin, destination);
			return gs;
		}
		return null;
	}

	/**
	 * Select (double the size) a Graphical Point on the map
	 * 
	 * @param inter the Intersection that we want the corresponding Graphical Point
	 *              to be selected
	 */
	public void selectPoint(Intersection inter) {
		// clear if necessary
		this.clearSelectedPoint();
		String interNum = inter.getNumber();

		graphicalPoints.forEach(gp -> {
			if (gp.getPoint().getNumber().equals(interNum)) {
				gp.setSize(gp.getSize() * 2);
				;
				selectedPoint = gp.getPoint();
				return;
			}
		});

		repaint();
	}

	/**
	 * Getter for the selectedPonint attribute
	 * 
	 * @return the selectedPoint attribute
	 */
	public Intersection getSelectedPoint() {
		return selectedPoint;
	}

	/**
	 * This method is used to respond to a click on the point of coordinates (x,y)
	 * on the map, when you are looking for a type of point in particular (random or
	 * special : pickup, delivery, depot)
	 * 
	 * @param x         the horizontal coordinate (in pixels) of the click
	 * @param y         the vertical coordinate (in pixels) of the click
	 * @param isSpecial a boolean indicating whether the intersection has to be
	 *                  special (i.e is a colored point on the map) or not.
	 * @return the Intersection clicked if one was found, else returns null
	 */
	public Intersection mapClickedResponse(int x, int y, boolean isSpecial) {
		// clear if necessary
		this.clearSelectedPoint();

		// Look for a new selected point
		graphicalPoints.forEach(gp -> {
			if (gp.isClicked(x, y) && gp.getIsSpecial() == isSpecial) {
				gp.setSize(gp.getSize() * 2);
				selectedPoint = gp.getPoint();
				return;
			}
		});

		repaint();
		return selectedPoint;
	}

	/**
	 * This method is used to respond to a click on the point of coordinates (x,y)
	 * on the map
	 * 
	 * @param x the horizontal coordinate (in pixels) of the click
	 * @param y the vertical coordinate (in pixels) of the click
	 * @return the Intersection clicked if one was found, else returns null
	 */
	public Intersection mapClickedResponse(int x, int y) {
		// clear if necessary
		this.clearSelectedPoint();

		// Look for a new selected point

		ListIterator<GraphicalPoint> gpIterator = graphicalPoints.listIterator();
		GraphicalPoint gp = gpIterator.next();
		while (gpIterator.hasNext() && !gp.isClicked(x, y)) {
			gp = gpIterator.next();
		}

		if (gp.isClicked(x, y)) {
			gp.setSize(gp.getSize() * 2);
			selectedPoint = gp.getPoint();
		}

		repaint();
		return selectedPoint;
	}

	/**
	 * Unselect (reduce its size) the selected point on the map, if there is one.
	 */
	public void clearSelectedPoint() {
		if (selectedPoint != null) {
			int i = 0;
			// Look for the selected point in the list of Graphical Points
			while ((i < graphicalPoints.size()) && selectedPoint != null) {
				if (selectedPoint.getNumber().equals(graphicalPoints.get(i).getPoint().getNumber())) {
					graphicalPoints.get(i).setSize(graphicalPoints.get(i).getSize() / 2);
					selectedPoint = null;
				}
				i++;
			}
		}
	}

	/**
	 * Resets every point to white and to a normal size (8px here)
	 */
	public void clearRequestsOnMap() {
		int i = 0;
		while (i < graphicalPoints.size()) {
			graphicalPoints.get(i).setColor(Color.white);
			graphicalPoints.get(i).setSize(8);
			i++;
		}
		// In case a point was selected
		clearSelectedPoint();
	}

	/**
	 * Resets all the segments of the map to white and to a normal size (the ones
	 * that belonged to the tour were red and thicker)
	 */
	public void clearTourOnMap() {
		int segSize = graphicalSegments.size();
		int i = 0;
		// reset all segments to white and small size
		while (i < segSize) {
			graphicalSegments.get(i).setOnPath(0);
			graphicalSegments.get(i).setColor(Color.white);
			i++;
		}
	}
}
