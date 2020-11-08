package model;

import java.util.List;
import java.util.ListIterator;

import tsp.CompleteGraph;

import java.util.ArrayList;
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
	 * The goal of the addRequest method is to re-compute the smallest possible part of the path, 
	 * while the other parts of the path remain unchanged.
	 * 
	 * @param newRequest     : the request that must me added to the tour
	 * @type 				 : Request
	 * @param beforePickup   : the intersection that has to be visited before the pickup point of the new request
	 * @type 				 : Intersection
	 * @param beforeDelivery : the intersection that has to be visited before the delivery point of the new request
	 * @type 				 : Intersection
	 * @param cm             : the CityMap corresponding to the path, used to compute the new path
	 * @type 				 : CityMap
	 * @param path           : the tour before the new request has been added
	 * @type 				 : List<Segment>
	 * @return List<Segment> : the path modified path with the new request
	 */
	public List<Segment> addRequest(Request newRequest, Intersection beforePickup, Intersection beforeDelivery, CityMap cm, List<Segment> path) {
		
	 /* There are two main cases :
	  * 	- newDelivery does not follow newPickup : 2 new paths have to be computed and added to the tour, 
	  * 		from beforePickup to newPickup to afterPickup and from beforeDelivery to new Delivery to afterDelivery
	  * 
	  * 	- newDelivery follows newPickup : 1 new path has to be computed and added to the tour, from beforePickup
	  * 		to newPickup to newDelivery to afterDelivery
	  * 
	  * The method is in three steps :
	  * 	- finding important points, such as afterDelivery and afterPickup, and adding the new Request into the
	  * 		the list of addresses that have to be visited
	  * 	
	  * 	- computing the new shortest intermediates paths, and finding all the intermediates nodes from the cityMap
	  * 	
	  * 	- cutting and concatenating the old path in order to create a new path with the new Request.
	  * */
	
		Intersection newPickup = newRequest.getPickup();
		Intersection newDelivery = newRequest.getDelivery();
		Intersection afterPickup = this.orderedAddresses.get(this.orderedAddresses.indexOf(beforePickup)+1);
		Intersection afterDelivery = this.orderedAddresses.get(this.orderedAddresses.lastIndexOf(beforeDelivery)+1);
		
		this.orderedAddresses.add(this.orderedAddresses.lastIndexOf(beforeDelivery)+1, newDelivery);
		this.orderedAddresses.add(this.orderedAddresses.indexOf(beforePickup)+1, newPickup);
		
		List<Segment> pickupPath = new LinkedList<Segment>();
		List<Segment> deliveryPath = new LinkedList<Segment>();
		List<Intersection> zone;
		if(beforePickup == beforeDelivery) {
			zone = new ArrayList<Intersection>(4);
			zone.add(beforePickup); zone.add(newPickup); zone.add(newDelivery); zone.add(afterDelivery);
			afterPickup = newDelivery;
			beforeDelivery = newPickup;
			pickupPath = adjustRoadMap(zone, cm);
		} else {
			zone = new ArrayList<Intersection>(4);
			zone.add(beforePickup); zone.add(newPickup); zone.add(afterPickup);
			pickupPath = adjustRoadMap(zone, cm);
			zone.clear();
			zone.add(beforeDelivery); zone.add(newDelivery); zone.add(afterDelivery);
			deliveryPath = adjustRoadMap(zone, cm);
		}

		List<Segment> beginning = new LinkedList<Segment>();
		List<Segment> middle = new LinkedList<Segment>();
		List<Segment> end = new LinkedList<Segment>();
		
		ListIterator<Segment> iterator = path.listIterator();
		Segment next = iterator.next();
		while(next.getOrigin() != beforePickup && iterator.hasNext()) {
			beginning.add(next);
			next = iterator.next();
		}
		
		while(next.getOrigin() != afterPickup && iterator.hasNext() )
			next = iterator.next();
		
		while(next.getOrigin() != beforeDelivery && iterator.hasNext()) {
			middle.add(next);
			next = iterator.next();
		}
		
		while(next.getOrigin() != afterDelivery && iterator.hasNext()) {
			next = iterator.next();
		}
		
		while(iterator.hasNext()) {
			end.add(next);
			next = iterator.next();
		}
		
		if(! this.isLastIntersection(afterDelivery))
			end.add(next);
		
		path.clear();
		path.addAll(beginning);
		path.addAll(pickupPath);
		path.addAll(middle);
		path.addAll(deliveryPath);
		path.addAll(end);
		return path;
		
	}
	
	/**
	 * Compute and return the shortest path in the cityMap cm from the first to the last point of the zone list,
	 * while passing by every point in the list, in the given order. 
	 * 
	 * @return List<Segment>
	 * @param List<Intersection> zone : 
	 * @param CityMap cm
	 * */
	private List<Segment> adjustRoadMap(List<Intersection> zone, CityMap cm){
		List<Segment> path = new LinkedList<Segment>();
		
		CompleteGraph pickupGraph = new CompleteGraph(cm, zone);
		
		for(int i = 0; i < zone.size()-1; i++) {
			path.addAll(this.constructPath(zone.get(i), zone.get(i+1), cm, pickupGraph));
		}
		return path;
	}
	/**
	 * Compute and return the shortest path in the cityMap cm from point first to point second 
	 * 
	 * @return List<Segment>
	 * @param Intersection first
	 * @param Intersection second
	 * @param CityMap cm
	 * @param CompleteGraph g
	 * */
	private List<Segment> constructPath(Intersection first, Intersection second, CityMap cm, CompleteGraph g){
		
		List<Segment> path = new LinkedList<Segment>();
		
		List<Integer> intermediateNodes = new LinkedList<Integer>();
		int firstInt = cm.getIntFromIntersectionMap(first);
		int secondInt1 = cm.getIntFromIntersectionMap(second);
		int[] precedence = g.getPrecedenceOfANode(firstInt);
		for (int i=secondInt1; i!= firstInt; i=precedence[i]) {
			intermediateNodes.add(i);
		}
		intermediateNodes.add(firstInt);

		ListIterator<Integer> iterator = intermediateNodes.listIterator(intermediateNodes.size()); 
		Intersection currentNodeInter = cm.getIntersectionFromIdMap(iterator.previous());
		Intersection previousNodeInter = currentNodeInter;
		while(iterator.hasPrevious()){
			int previousNodeId = iterator.previous();
			previousNodeInter = cm.getIntersectionFromIdMap(previousNodeId);
			path.add(cm.getSegmentFromInter(currentNodeInter, previousNodeInter));
			currentNodeInter = previousNodeInter;
		}
		return path;
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
		this.orderedAddresses.addFirst(this.setOfRequests.getDepot());
		this.orderedAddresses.addLast(this.setOfRequests.getDepot());
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

	/**
	 * Prints the addressess in the terminal, in the right order
	 */
	public void printReorderedAddresses() {
		int index = 0;
		for (Intersection currentAddress : this.orderedAddresses) {
			System.out.println("Address number : "+index+" "+currentAddress.toString());
			index++;
		}
	}

	/**
	 * Checks if an intersection is before another one in the orderedAddresses liked list
	 * @param i1 the first Intersection
	 * @param i2 the second Intersection
	 * @return true if i1 is indeed before i2 in the LinkedList orderedAdresses or if i1 == i2, false is it is after or if one of the two
	 * intersections were not found in the list
	 * 
	 */
	public boolean checkPrecedence(Intersection i1, Intersection i2) {
		int i1index = orderedAddresses.indexOf(i1);
		int i2index = orderedAddresses.indexOf(i2);
		System.out.println(i1index + "  " + i2index);
		if((i1index != -1) && (i2index != -1) && (i1index <= i2index)){
			//Both intersections were found and i1 is before, or equal to, i2
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if an intersection is the last of the LinkedList orderedAddresses
	 * @param i the Intersection
	 * @return true if i1 is indeed the last Intersection in the LinkedList orderedAdresses, false for any other case
	 * 
	 */
	public boolean isLastIntersection(Intersection i) {
		return this.orderedAddresses.getLast() == i;
	}
}
