package view;

import javax.swing.*;

import model.Intersection;
import model.CityMap;
import model.Request;
import model.Segment;
import model.SetOfRequests;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GraphicalView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Intersection> intersections;
	private List<Segment> segments;
	private float minLat;
	private float minLongi;
	private float maxLat;
	private float maxLongi;

	private List<GraphicalPoint> graphicalPoints;
	private String selectedPointId;
	private List<GraphicalSegment> graphicalSegments;

	public GraphicalView(CityMap loadedMap) {
		setLayout(null);
		setBounds(0, 0, 820, 840); //Larger than 800x800 to have margins
		intersections = loadedMap.getInstersections();
		segments = loadedMap.getSegments();

		graphicalPoints = new LinkedList<GraphicalPoint>();
		selectedPointId = null;
		graphicalSegments = new LinkedList<GraphicalSegment>();
		minLat = Float.POSITIVE_INFINITY;
		minLongi = Float.POSITIVE_INFINITY;
		maxLat = 0;
		maxLongi = 0;
		setExtemeCoordinates();

		displayMap();
	}

	public void paint(Graphics g) {
		// Background
		g.setColor(Color.gray);
		g.fillRect(0, 0, 820, 820);

		// Draw segments
		Graphics2D g2d = (Graphics2D)g;
		Stroke normalRoad = new BasicStroke(1f);
		Stroke importantRoad = new BasicStroke(5f);
		g2d.setStroke(normalRoad);
		for (GraphicalSegment gs : graphicalSegments) {
			if (gs != null) {
				if(gs.getOnPath() == 1) {
					g2d.setStroke(importantRoad);
				}
				g2d.setColor(gs.getColor());
				g2d.drawLine(gs.getXOriginPixel(), gs.getYOriginPixel(), gs.getXDestPixel(), gs.geYDestPixel());
				if(gs.getOnPath() == 1) {
					g2d.setStroke(normalRoad);
				}
			}
		}

		// Draw intersections
		List<GraphicalPoint> coloredIntersections = new LinkedList<GraphicalPoint>(); //To store the colored intersections and draw them at the end
		for (GraphicalPoint gp : graphicalPoints) {
			if(gp.getColor()==Color.white){
				g.setColor(Color.white);
				g.fillOval(gp.getXPixel(), gp.getYPixel(), gp.getSize(), gp.getSize());
			} else {
				coloredIntersections.add(gp);
			}
		}
			
		for (GraphicalPoint gp : coloredIntersections) {
			g.setColor(gp.getColor());
			g.fillOval(gp.getXPixel(), gp.getYPixel(), gp.getSize(), gp.getSize());
		}

	}

	public void displayMap() {
		// Graphical points
		for (Intersection i : intersections) {
			GraphicalPoint gp = new GraphicalPoint(i, minLat, minLongi, maxLat, maxLongi);
			graphicalPoints.add(gp);
		}

		// Graphical segments
		for (Segment s : segments) {
			GraphicalSegment gs = createSegment(s.getNumberOrigin(), s.getNumberDestination());
			if (gs != null) {
				graphicalSegments.add(gs);
			}
		}
	}
	
	public void displayTour(List<Segment> segments) {
		int segSize = graphicalSegments.size();
		
		int i = 0;
		// reset all segments to white
		while (i < segSize) {
			graphicalSegments.get(i).setOnPath(0);
			i++;
		}
		
		// change color of corresponding segments
		segments.forEach(s -> {
			int j = 0;
			while(j < segSize) {
				GraphicalSegment seg = graphicalSegments.get(j);
				if(s.getNumberOrigin().equals(seg.getOrigin()) &&
				s.getNumberDestination().equals(seg.getDestination())
						) {
					seg.setOnPath(1);
					seg.setColor(Color.red);
					break;
				}
				j++;
			}
		});
		
		repaint();
		
	}

	public void displayRequests(SetOfRequests sr) {
		// Reset map, i. e. change the color of all points to white
		int i = 0;
		while (i < graphicalPoints.size()) {
			graphicalPoints.get(i).setColor(Color.white);
			graphicalPoints.get(i).setSize(8);
			i++;
		}
		
		// Look for the departure point
		i = 0;
		boolean found = false;
		while (i < graphicalPoints.size() && !found) {
			if (sr.getDepot().equals(graphicalPoints.get(i).getIntersectionId())) {
				graphicalPoints.get(i).setColor(Color.yellow);
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
				if (r.getPickupAddress().equals(graphicalPoints.get(i).getIntersectionId())) {
					graphicalPoints.get(i).setColor(Color.BLUE);
					graphicalPoints.get(i).setSize(12);
					pFound = true;
				} else if (r.getDeliveryAddress().equals(graphicalPoints.get(i).getIntersectionId())) {
					graphicalPoints.get(i).setColor(Color.MAGENTA);
					graphicalPoints.get(i).setSize(12);
					dFound = true;
				}
				i++;
			}
		}

		repaint();
	}

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

	public GraphicalSegment createSegment(String idOrigin, String idDestination) {
		// Go through the list to find the intersection
		int i = 0;
		GraphicalPoint origin = null;
		GraphicalPoint destination = null;
		while ((i < graphicalPoints.size()) && (origin == null || destination == null)) {
			if (idOrigin.equals(graphicalPoints.get(i).getIntersectionId())) {
				origin = graphicalPoints.get(i);
			}
			if (idDestination.equals(graphicalPoints.get(i).getIntersectionId())) {
				destination = graphicalPoints.get(i);
			}
			i++;
		}
		if (origin != null && destination != null) {
			GraphicalSegment gs = new GraphicalSegment(idOrigin, idDestination, origin.getXPixel(), origin.getYPixel(), destination.getXPixel(),
					destination.getYPixel());
			return gs;
		}
		return null;
	}

	public String mapClickedResponse(int x, int y){
		//if a point is already selected, unselect it
		if(selectedPointId!=null){
			System.out.println("already a selection" + selectedPointId);
			System.out.println(graphicalPoints.size());
			int i = 0;
			while ((i < graphicalPoints.size()) && selectedPointId!=null ) {
				System.out.println(graphicalPoints.get(i).getIntersectionId());
				if (selectedPointId.equals(graphicalPoints.get(i).getIntersectionId())) {
					graphicalPoints.get(i).setSize(graphicalPoints.get(i).getSize()/2);;
					selectedPointId = null;
					System.out.println("found");
					
				}
				i++;
			}
		}

		//Look for a new selected point
		boolean found = false;
		int i = 0;
		while ((i < graphicalPoints.size()) && !found) {
			found = graphicalPoints.get(i).isClicked(x,y);
			if (found) {
				graphicalPoints.get(i).setSize(graphicalPoints.get(i).getSize()*2);;
				selectedPointId = graphicalPoints.get(i).getIntersectionId();
			}
			i++;
		}

		repaint();

		return selectedPointId;

	}

	public String getSelectedPointId(){
		return selectedPointId;
	}

}
