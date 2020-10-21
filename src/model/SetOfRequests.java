package model;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 */
public class SetOfRequests {
	private String idDepot;
	private Date departureTime;
	private List<Request> requests;

	public SetOfRequests(String idDepot, Date departure, List<Request> req) {
		this.idDepot = idDepot;
		this.departureTime = departure;
		this.requests = req;
	}

	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");
		String departureString = format.format(departureTime);
		// pour une raison qui m'échappe, le date to string ne fonctionne pas
		String message = "Departure at " + departureString + " from " + idDepot + "\nRequests :\n";
		for (Request r : requests) {
			message += r + "\n";
		}
		return message;
	}

	public String getDepot() {
		return idDepot;
	}

	public List<Request> getRequests() {
		return requests;
	}
	
	public String[] getRequestNodes() {
	  String [] requestNodes = new String[requests.size()*2 + 1]; //2*request (destination and departure) +1 depot
	  requestNodes[0] = idDepot;
	  for(int i = 1; i < requestNodes.length; i+=2)
	  for(Request r : requests) {
		  requestNodes[i] = r.getDeliveryAddress();
		  requestNodes[i+1] = r.getPickupAddress();
	  }
	  return requestNodes;
    }
}
