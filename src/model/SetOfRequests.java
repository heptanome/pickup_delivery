package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A set of requests is made of a list of requests, a date and a depot.
 */
public class SetOfRequests {
	private Intersection depot;
	private Date departureTime;
	private List<Request> requests;

	public SetOfRequests(Intersection idDepot, Date departure, List<Request> req) {
		this.depot = idDepot;
		this.departureTime = departure;
		this.requests = req;
	}

	public String getDepotAddress() {
		return depot.getNumber();
	}

	public Intersection getDepot() {
		return depot;
	}

	public List<Request> getRequests() {
		return requests;
	}

	/**
	 * From the set of requests, return the list of intersections used for the depot
	 * and for each request
	 * 
	 * @return List<Intersection>
	 */
	public List<Intersection> getRequestNodes() {
		// 2*request (destination and departure) +1 depot
	  List<Intersection> requestNodes = new ArrayList<Intersection>(requests.size()*2 + 1);
	  requestNodes.add(0, depot);
	  int index = 1;
	  for(Request r : requests) {
		  requestNodes.add(index,r.getDelivery());
		  requestNodes.add(index+1,r.getPickup());
		  index +=2;
	  }
	  return requestNodes;
    }

	public boolean isDeliveryPoint(Intersection i) {
		for (Request r : requests)
			if (i == r.getDelivery())
				return true;
		return false;
	}
	
	/**
	 * From the set of requests, return the list of requests whose delivery point is
	 * the intersection delivery.
	 * 
	 * @param delivery the delivery point to be tested
	 * @type Intersection
	 * @return List<Request>
	 */
	public List<Request> getRequestsFromDelivery(Intersection delivery) {
		List<Request> requestsList = new LinkedList<Request>();
		for(Request r : requests)
			if(delivery ==  r.getDelivery())
				requestsList.add(r);
		return requestsList;
	}
	
	/**
	 * From the set of requests, return the list of requests whose delivery or pickup point is
	 * the intersection given as a parameter.
	 * 
	 * @param intersection the intersection to be tested
	 * @type Intersection
	 * @return List<Request>
	 */
	public Request getRequestFromIntersection(Intersection intersection) {
		for(Request r : requests)
			if(intersection ==  r.getPickup() || intersection == r.getDelivery())
				return r;
		return null;
	}
	
	public void deleteRequest(Request request) {
		requests.remove(request);
	}
	

	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");
		String departureString = format.format(departureTime);
		String message = "Departure at " + departureString + " from " + depot.getNumber() + "\nRequests :\n";
		for (Request r : requests) {
			message += r + "\n";
		}
		return message;
	}
}
