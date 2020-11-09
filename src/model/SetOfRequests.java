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

	/**
	 * Constructor
	 * @param idDepot
	 * 			Intersection where the delivery man starts its tour
	 * @param departure
	 * 			Time of departure (when the delivery man starts its tour)
	 * @param req
	 * 			Unordered list of requests the delivery man has to visit
	 */
	public SetOfRequests(Intersection idDepot, Date departure, List<Request> req) {
		this.depot = idDepot;
		this.departureTime = departure;
		this.requests = req;
	}

	/**
	 * Get the depot address where the delivery man starts its tour
	 * @return address of the first intersection of a tour
	 */
	public String getDepotAddress() {
		return depot.getNumber();
	}

	/**
	 * Get the depot where the delivery man starts its tour
	 * @return first intersection of a tour
	 */
	public Intersection getDepot() {
		return depot;
	}

	/**
	 * Get the requests of a set of requests
	 * @return the list of requests of a set of requests
	 */
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

	/**
	 * Find if a specific intersection is a delivery or a pickup address
	 * @param intersection
	 * 			intersection of a request with an unknown type (delivery or pickup)
	 * @return true is the intersection is a delivery address, false if not
	 */
	public boolean isDeliveryPoint(Intersection intersection) {
		for (Request r : requests) {
			if (intersection == r.getDelivery()) {
				return true;
			}
		}
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
		for(Request r : requests) {
			if(delivery ==  r.getDelivery()) {
				requestsList.add(r);
			}
		}
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
		for(Request r : requests) {
			if(intersection ==  r.getPickup() || intersection == r.getDelivery()) {
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Delete a request from its list
	 * @param request
	 */
	public void deleteRequest(Request request) {
		requests.remove(request);
	}
	
	/**
	 * Add a request from its list
	 * @param request
	 */
	public void addRequest(Request request) {
		requests.add(request);
	}
	
	/**
	 * Convert information of a SetOfRequests to a String
	 */
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
