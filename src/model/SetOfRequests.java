package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.text.SimpleDateFormat;

/**
 *
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

	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");
		String departureString = format.format(departureTime);
		// pour une raison qui m'échappe, le date to string ne fonctionne pas
		String message = "Departure at " + departureString + " from " + depot.getNumber() + "\nRequests :\n";
		for (Request r : requests) {
			message += r + "\n";
		}
		return message;
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
	
	public List<Intersection> getRequestNodes() {
	  List<Intersection> requestNodes = new ArrayList<Intersection>(requests.size()*2 + 1); //2*request (destination and departure) +1 depot
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
		for(Request r : requests) {
			if(i == r.getDelivery()) {
				return true;
			}
		}
		return false;
	}
	
	public List<Request> getRequestsFromDelivery(Intersection delivery) {
		List<Request> requestsList = new LinkedList<Request>();
		for(Request r : requests) {
			if(delivery ==  r.getDelivery()) {
				requestsList.add(r);
			}
		}
		return requestsList;
	}
}
