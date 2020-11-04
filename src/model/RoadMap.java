package model;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Road map that the delivery man should follow
 * @attribute setOfRequests       : List of unordered requests with a depot and a departure time
 * @type      			          : SetOfRequests
 * @attribute mapAddressToRequest : Map linking a pickup or delivery address with its request
 * @type      			          : HashMap<Intersection, Request>
 * @attribute orderedAddresses    : List of ordered pickup and delivery addresses for a set of requests
 * @type                          : LinkedList<Intersection>
 */
public class RoadMap {
	private SetOfRequests setOfRequests;
	private HashMap<Intersection, Request> mapAddressToRequest;
	private LinkedList<Intersection> orderedAddresses;

	/**
	 * Constructor
	 * @param roadsAfterComputedTour : ordered roads
	 * @type 						 : List<Segment>
	 * @param initialSetOfRequests   : initial List of unordered requests with a depot and a departure time
	 * @type 						 : SetOfRequests
	 */
	public RoadMap(List<Segment> roadsAfterComputedTour, SetOfRequests initialSetOfRequests) {
		this.setOfRequests = initialSetOfRequests;
		this.mapAddressToRequest = new HashMap<Intersection,Request>();
		this.orderedAddresses = new LinkedList<Intersection>();
		this.calculateMapAddressToRequest();
		this.reorderAddresses(roadsAfterComputedTour);
	}
	
	/**
	 * Fill a map to link the addresses of a request represented by intersections to the reference of this request 
	 */
	private void calculateMapAddressToRequest() {
		for (Request currentRequest : this.setOfRequests.getRequests()) {
			this.mapAddressToRequest.put(currentRequest.getPickup(),currentRequest);
			this.mapAddressToRequest.put(currentRequest.getDelivery(),currentRequest);
		}
	}

	/**
	 * Reorder the pickup and delivery addresses after computing the tour
	 * @param roadsAfterComputedTour : ordered roads
	 * @type 						 : List<Segment>
	 */
	private void reorderAddresses(List<Segment> roadsAfterComputedTour) {
		HashMap<Intersection, Request> addressesToFind = new HashMap<Intersection,Request>();
		addressesToFind.putAll(mapAddressToRequest); 
		
		for (Segment currentSegment : roadsAfterComputedTour) {
			Intersection currentIntersection = currentSegment.getOrigin();

			if (addressesToFind.containsKey(currentIntersection)) {
				Request request = addressesToFind.get(currentIntersection);
				Intersection pickUpAddress = request.getPickup();
				
				// The found intersection is a pickup address
				if (pickUpAddress == currentIntersection) {
					this.addIntersectionToOrderedList(currentIntersection, addressesToFind);
				} else {
					// The found intersection is a delivery address and its pickup address was already found
					if (!addressesToFind.containsKey(pickUpAddress)) {
						this.addIntersectionToOrderedList(currentIntersection, addressesToFind);
					}
				}
			}
			// We stop to iterate on each segment when all pickup and delivery addresses were found
			if (addressesToFind.isEmpty()) {
				break;
			}
		}
	}
	
	/**
	 * Add an address to the ordered list of addresses and remove it from the list of unvisited addresses
	 * @param currentIntersection : current intersection in the list of ordered segments
	 * @type 					  : Intersection currentIntersection
	 * @param addressesToFind	  : Map linking the remaining unvisited pickup or delivery addresses with their request 
	 * @type					  : HashMap<Intersection, Request>
	 */
	private void addIntersectionToOrderedList(Intersection currentIntersection, HashMap<Intersection, Request> addressesToFind) {
		this.orderedAddresses.add(currentIntersection);
		addressesToFind.remove(currentIntersection);
	}
	
	private void printReorderedAddresses() {
		int index = 0;
		for (Intersection currentAddress : this.orderedAddresses) {
			System.out.println("Address number : "+index+" "+currentAddress.toString());
			index++;
		}
	}

}
