package model;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	 * @return
	 */
	public Intersection[] getRequestNodes() {
		// 2 * request (destination and departure) + 1 depot
		Intersection[] requestNodes = new Intersection[requests.size() * 2 + 1];

		requestNodes[0] = depot;
		int index = 1;
		for (Request r : requests) {
			requestNodes[index] = r.getDelivery();
			requestNodes[index + 1] = r.getPickup();
			index += 2;
		}
		return requestNodes;
	}

	// we assume that all delivery and pickup points are unique. One intersection =
	// one and only one pickup (or delivery) point.
	public boolean isDeliveryPoint(Intersection i) {

		for (Request r : requests) {
			if (i == r.getDelivery()) {
				return true;
			}
		}
		return false;
	}

	public Intersection getPickUpFromDelivery(Intersection delivery) {
		for (Request r : requests) {
			if (delivery == r.getPickup()) {
				return r.getPickup();
			}
		}
		return null;
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
