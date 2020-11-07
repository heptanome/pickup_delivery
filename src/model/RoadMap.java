package model;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import tsp.CompleteGraph;
import tsp.TSP;
import tsp.TSP2;

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
	
	public List<Segment> addRequest(Request newRequest, Intersection beforePickup, Intersection beforeDelivery, CityMap cm, List<Segment> path) {
		/*Step 1 :
		 * find "point after pickup" and "point after delivery"
		*/
		Intersection newPickup = newRequest.getPickup();
		Intersection newDelivery = newRequest.getDelivery();
		Intersection afterPickup = this.orderedAddresses.get(this.orderedAddresses.indexOf(beforePickup)+1);
		Intersection afterDelivery = this.orderedAddresses.get(this.orderedAddresses.lastIndexOf(beforeDelivery)+1);
		
		this.orderedAddresses.add(this.orderedAddresses.indexOf(beforePickup)+1, newPickup);
		this.orderedAddresses.add(this.orderedAddresses.lastIndexOf(beforeDelivery)+1, newDelivery);
		
		/*
		 * Step 2 : compute shortest path from beforePickup to newPickup and from
		 * newPickup to afterPickup
		 * (same for delivery points)
		 */
		
		List<Segment> pickupPath = adjustRoadMap(beforePickup, newPickup, afterPickup, cm);
		List<Segment> deliveryPath = adjustRoadMap(beforeDelivery, newDelivery, afterDelivery, cm);

		/*
		 * Step 3 : modify the path with the new paths*/

		List<Segment> beginning = new LinkedList<Segment>();
		List<Segment> middle = new LinkedList<Segment>();
		List<Segment> end = new LinkedList<Segment>();
		
		ListIterator<Segment> iterator = path.listIterator();
		Segment next = iterator.next();
		while(next.getOrigin() != beforePickup) {
			beginning.add(next);
			next = iterator.next();
		}
		while(next.getOrigin() != afterPickup)
			next = iterator.next();
		while(next.getOrigin() != beforeDelivery) {
			middle.add(next);
			next = iterator.next();
		}
		while(next.getOrigin() != afterDelivery) {
			next = iterator.next();
		}
		while(iterator.hasNext()) {
			end.add(next);
			next = iterator.next();
		}
		end.add(next);
		path.clear();
		path.addAll(beginning);
		path.addAll(pickupPath);
		path.addAll(middle);
		path.addAll(deliveryPath);
		path.addAll(end);
		return path;
		
	}
	
	private List<Segment> adjustRoadMap(Intersection beforeI, Intersection newI, Intersection afterI, CityMap cm){
		
		List<Segment> path = new LinkedList<Segment>();
		
		List<Intersection> zone = new ArrayList<Intersection>(3);
		zone.add(beforeI);
		zone.add(newI);
		zone.add(afterI);
		CompleteGraph pickupGraph = new CompleteGraph(cm, zone);;
		List<Integer> intermediateNodes = new LinkedList<Integer>();
		int beforeInt = cm.getIntFromIntersectionMap(beforeI);
		int newInt = cm.getIntFromIntersectionMap(newI);
		int[] precedence = pickupGraph.getPrecedenceOfANode(beforeInt);
		
		
		for (int i=newInt; i!= beforeInt; i=precedence[i]) {
			intermediateNodes.add(i);
		}
		intermediateNodes.add(beforeInt);

		ListIterator<Integer> iterator = intermediateNodes.listIterator(intermediateNodes.size()); 
		Intersection currentNodeInter = cm.getIntersectionFromIdMap(iterator.previous());
		Intersection previousNodeInter = currentNodeInter;
		while(iterator.hasPrevious()){
			int previousNodeId = iterator.previous();
			previousNodeInter = cm.getIntersectionFromIdMap(previousNodeId);
			path.add(cm.getSegmentFromInter(currentNodeInter, previousNodeInter));
			currentNodeInter = previousNodeInter;
		}
		intermediateNodes.clear();
		
		int afterInt = cm.getIntFromIntersectionMap(afterI);
		precedence = pickupGraph.getPrecedenceOfANode(newInt);
		for (int i=afterInt; i!= newInt; i=precedence[i]) {
			intermediateNodes.add(i);
		}
		intermediateNodes.add(newInt);

		iterator = intermediateNodes.listIterator(intermediateNodes.size()); 
		currentNodeInter = cm.getIntersectionFromIdMap(iterator.previous());
		previousNodeInter = currentNodeInter;
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
	 * @return true if i1 is indeed before i2 in the LinkedList orderedAdresses, false is it is after or if one of the two
	 * intersections were not found in the list
	 * 
	 */
	public boolean checkPrecedence(Intersection i1, Intersection i2) {
		int i1index = orderedAddresses.indexOf(i1);
		int i2index = orderedAddresses.indexOf(i2);
		System.out.println(i1index + "  " + i2index);
		if((i1index != -1) && (i2index != -1) && (i1index < i2index)){
			//Both intersections were found and i1 is before i2
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
