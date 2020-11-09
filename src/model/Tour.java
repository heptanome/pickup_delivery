package model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import tsp.CompleteGraph;
import tsp.TSP;
import tsp.TSP3;

/**
 * Main class of the Model. A tour will hold onto a map, a set of requests and
 * once computed an ordered list of segments. This class receives events from
 * the controller and will send the updated data to the View using its
 * propertyChange
 */
public class Tour {
	private CityMap map;
	private SetOfRequests setOfRequests;
	private PropertyChangeSupport support;
	private LinkedList<Segment> path;
	private RoadMap roadMap;

	public Tour() {
		// observable object
		support = new PropertyChangeSupport(this);
		this.map = null;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	/**
	 * Once the map has been loaded, this method saves it in the Tour and warns the
	 * View it is ready to be used
	 * 
	 * @param mapPath the map's file path
	 * @throws Exception The map parser can throw an exception if the file doesn't
	 *                   load properly
	 */
	public void setMap(String mapPath) throws Exception {
		CityMap oldMap = this.map;
		MapParser mp = new MapParser(mapPath);
		this.map = mp.loadMap();
		// signal the observers the map has changed
		support.firePropertyChange("updateMap", oldMap, this.map);
	}

	/**
	 * Once the set of requests has been loaded, this method saves it in the Tour
	 * and warns the View it has been updated.
	 * 
	 * @param reqPath The set of requests' file path
	 * @throws Exception if the requests file doesn't load properly
	 */
	public void setRequests(String reqPath) throws Exception {
		SetOfRequests oldReq = this.setOfRequests;
		RequestParser rp = new RequestParser(reqPath, this.map);
		this.setOfRequests = rp.loadRequests();
		// signal the observers the set of requests has changed
		support.firePropertyChange("updateRequests", oldReq, this.setOfRequests);
	}

	/**
	 * Once a new Request has been specified by the user, this method adds it to the
	 * setOfRequests, computes a new path, saves it in the Tour and warns the View
	 * it has been updated
	 * 
	 * @param newRequest     new request to add to the path
	 * @param beforeDelivery Address before the delivery address of the new request
	 * @param beforePickup   Address before the pickup address of the new request
	 */
	public void addRequest(Request newRequest, Intersection beforeDelivery, Intersection beforePickup) {
		support.firePropertyChange("startComputing", null, this);
		this.setOfRequests.addRequest(newRequest);
		this.roadMap.addRequest(newRequest, beforePickup, beforeDelivery, this.map, this.path);
		this.refreshColorsOfTour();
		support.firePropertyChange("updateRequests", null, this.setOfRequests);
		support.firePropertyChange("tourComputed", null, this);
	}

	/**
	 * Once a Request has been specified by the user, this method removes it from
	 * the setOfRequests, computes a new path, saves it in the Tour and warns the
	 * View it has been updated
	 * 
	 * @param request request to delete
	 */
	public int deleteRequest(Request request) {
		support.firePropertyChange("startComputing", null, this);
		int nbRequests = this.setOfRequests.deleteRequest(request);
		this.roadMap.deleteRequest(request, this.map, this.path);
		this.refreshColorsOfTour();
		support.firePropertyChange("updateRequests", null, this.setOfRequests);
		support.firePropertyChange("tourComputed", null, this);
		return nbRequests;
	}

	/**
	 * Once a tour has been computed, this method adds it in the Tour, adds the
	 * corresponding RoadMap in the Tour and warns the View it has been updated. The
	 * tour computed is the best tour that could be found in 20s, not necessarily
	 * the best of all possible tours.
	 * 
	 * @return list of segments containing the path the delivery man should follow
	 */
	public List<Segment> computeTour() {
		support.firePropertyChange("startComputing", null, this);
		// TSP tsp = new TSP1();
		// TSP tsp = new TSP2();
		TSP tsp = new TSP3();

		CompleteGraph g = new CompleteGraph(map, setOfRequests);
		long startTime = System.currentTimeMillis();
		this.path = new LinkedList<Segment>();

		tsp.searchSolution(20000, g);

		System.out.println("Solution of cost " + tsp.getSolutionCost() + " found in "
				+ (System.currentTimeMillis() - startTime) + "ms : ");

		int[] solutionInt = new int[setOfRequests.getRequestNodes().size() + 1];
		Intersection[] solutionIntersection = new Intersection[setOfRequests.getRequestNodes().size() + 1];

		for (int i = 0; i < setOfRequests.getRequestNodes().size(); i++)
			solutionInt[i] = tsp.getSolution(i);

		solutionInt[solutionInt.length - 1] = 0;
		
		//Converting integer, used for array indexes, into the corresponding Intersection
		Map<Integer, Intersection> nodeIntersection = g.getNodeNames();
		for (int i = 0; i < solutionInt.length; i++) {
			solutionIntersection[i] = nodeIntersection.get(solutionInt[i]);
		}
		
		/*Find all the intermediate nodes
		 * For example, if in the solution, B is after A, we need to know
		 * all intermediate intersections, and the different Segment of the Map.
		 * We use the Djikstra precedence array to find this path.
		 * */
		List<Integer> intermediateNodes = new LinkedList<Integer>();
		Segment newSegment;
		for (int indexSol = 0; indexSol < solutionIntersection.length - 1; indexSol++) {
			int idOrigine = map.getIntFromIntersectionMap(solutionIntersection[indexSol]);
			int idDepart = map.getIntFromIntersectionMap(solutionIntersection[indexSol + 1]);
			int[] precedence = g.getPrecedenceOfANode(idOrigine);
			for (int i = idDepart; i != idOrigine; i = precedence[i]) {
				intermediateNodes.add(i);
			}
			
			/*From the intermediates nodes, we find the corresponding Segment in the map,
			 * and add it to the path
			 * */
			ListIterator<Integer> iterator = intermediateNodes.listIterator(intermediateNodes.size());
			Intersection currentNodeInter = solutionIntersection[indexSol];
			Intersection previousNodeInter = solutionIntersection[indexSol];
			while (iterator.hasPrevious()) {
				int previousNodeId = iterator.previous();
				previousNodeInter = map.getIntersectionFromIdMap(previousNodeId);
				newSegment = map.getSegmentFromInter(currentNodeInter, previousNodeInter);
				newSegment.setColor(new Color((255 / solutionIntersection.length * indexSol), 100, 100));
				this.path.add(newSegment);
				currentNodeInter = previousNodeInter;
			}
			intermediateNodes.clear();
		}
		this.roadMap = new RoadMap(this.path, this.setOfRequests);
		support.firePropertyChange("tourComputed", null, this);
		return this.path;
	}

	/**
	 * Getter for the roadMap attribute
	 * 
	 * @return the Tour's roadMap
	 */
	public RoadMap getRoadMap() {
		return roadMap;
	}

	/**
	 * Getter fot the path attribute
	 * 
	 * @return the Tour's path
	 */
	public LinkedList<Segment> getPath() {
		return this.path;
	}

	/**
	 * Getter for the setOfRequests attribute
	 * 
	 * @return the Tour's set of requests
	 */
	public SetOfRequests getSOR() {
		return this.setOfRequests;
	}

	/**
	 * Resets the map to null
	 */
	public void resetMap() {
		CityMap oldMap = this.map;
		this.map = null;
		// signal the observers the map has changed
		support.firePropertyChange("updateMap", oldMap, this.map);
	}

	/**
	 * Resets the set of request to null
	 */
	public void resetRequests() {
		SetOfRequests oldSor = this.setOfRequests;
		this.setOfRequests = null;
		// signal the observers the map has changed
		support.firePropertyChange("updateRequests", oldSor, this.setOfRequests);
	}

	/**
	 * Method used to find the Intersection visited before another one
	 * 
	 * @param i the reference Intersection
	 * @return the Intersection before i
	 */
	public Intersection getIntersectionBefore(Intersection i) {
		return roadMap.getIntersectionBefore(i);
	}

	/**
	 * Describe the Tour
	 * 
	 * @return a string for humans to understand what the hell is going on
	 */
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
		String departureTime = setOfRequests.getDepartureTime().format(format);
		TreeMap<LocalTime, Integer> durations = roadMap.calculateTime(path, setOfRequests.getDepartureTime());
		String message = "Road Map :\n" + "Departure at " + departureTime + " from Depot ("
				+ setOfRequests.getDepot().getLatitude() + ", " + setOfRequests.getDepot().getLongitude() + ")\n\n"
				+ this.roadMap.printRoadMap(this.path, durations) + "\n\n" + "Have a good Tour :)";

		return message;
	}

	/**
	 * Refresh the colors of the tour (degrading from red to green) after update
	 */
	public void refreshColorsOfTour() {
		int number = path.size();
		for (int index = 0; index < number; index++) {
			path.get(index).setColor(new Color((255 / number * index), 100, 100));
		}
	}
}
