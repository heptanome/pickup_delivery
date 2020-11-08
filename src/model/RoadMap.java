package model;

import tsp.CompleteGraph;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Road map that the delivery man should follow
 */
public class RoadMap {
	private HashMap<Intersection, Request> mapAddressToRequest;
	private LinkedList<Intersection> orderedAddresses;

	/**
	 * Constructor
	 * @param roadsAfterComputedTour ordered roads
	 * @param initialSetOfRequests 
	 * 			initial List of unordered requests with a depot and a departure time
	 */
	public RoadMap(List<Segment> roadsAfterComputedTour, SetOfRequests initialSetOfRequests) {
		this.mapAddressToRequest = new HashMap<Intersection,Request>();
		this.orderedAddresses = new LinkedList<Intersection>();
		this.calculateMapAddressToRequest(initialSetOfRequests);
		this.reorderAddresses(roadsAfterComputedTour, initialSetOfRequests);
	}
	
	/**
	 * Add a request and calculate the new corresponding path 
	 * @param newRequest
	 * 			New request to add
	 * @param beforePickup
	 * 			Preceding intersection of the Pick Up Address of the new request to add
	 * @param beforeDelivery
	 * 			Preceding intersection of the Delivery Address of the new request to add
	 * @param cityMap
	 * 			Loaded map containing all segments and intersections to calculate the new path
	 * @param path
	 * 			Actual path after the tour's been computed
	 */
	public void addRequest(Request newRequest, Intersection beforePickup,
							Intersection beforeDelivery, CityMap cityMap,List<Segment> path) {

		Intersection newPickup = newRequest.getPickup();
		Intersection newDelivery = newRequest.getDelivery();
		
		int indexBeforePickup = this.orderedAddresses.indexOf(beforePickup)+1;
		Intersection afterPickup = this.orderedAddresses.get(indexBeforePickup);
		int indexBeforeDelivery = this.orderedAddresses.lastIndexOf(beforeDelivery)+1;
		Intersection afterDelivery = this.orderedAddresses.get(indexBeforeDelivery);

		this.orderedAddresses.add(indexBeforePickup, newDelivery);
		this.orderedAddresses.add(indexBeforeDelivery, newPickup);
		
		List<Segment> pickupPath = new LinkedList<Segment>();
		List<Segment> deliveryPath = new LinkedList<Segment>();
		List<Intersection> zone = new ArrayList<Intersection>(4);
		if(beforePickup == beforeDelivery) {
			Intersection[] addresses = {beforePickup, newPickup, newDelivery, afterDelivery};
			pickupPath = this.findNewRoads(zone, cityMap, addresses);
			afterPickup = newDelivery;
			beforeDelivery = newPickup;
		} else {
			Intersection[] addressesPickup = {beforePickup, newPickup, afterPickup};
			pickupPath = this.findNewRoads(zone, cityMap, addressesPickup);
			Intersection[] addressesDelivery = {beforeDelivery, newDelivery, afterDelivery};
			deliveryPath = this.findNewRoads(zone, cityMap, addressesDelivery);
		}

		calculateNewTotalPath(path, pickupPath, deliveryPath, beforePickup, afterPickup, beforeDelivery, afterDelivery);
		
	}

	/**
	 * Delete a request and calculate the new path without this request
	 * @param requestToDelete
	 * 			Request to delete
	 * @param cityMap
	 * 			Loaded map containing all segments and intersections to calculate the new path
	 * @param path
	 * 			Actual path after the tour's been computed
	 */
	public void deleteRequest(Request requestToDelete, CityMap cityMap, List<Segment> path){
		int indexPickUpToDelete = this.orderedAddresses.indexOf(requestToDelete.getPickup());
		int indexDeliveryToDelete = this.orderedAddresses.indexOf(requestToDelete.getDelivery());
		
		//Get the intersections before and after the request to delete
		Intersection beforePickup = this.orderedAddresses.get(indexPickUpToDelete-1);
		Intersection afterPickup = this.orderedAddresses.get(indexPickUpToDelete+1);
		Intersection beforeDelivery = this.orderedAddresses.get(indexDeliveryToDelete-1);
		Intersection afterDelivery = this.orderedAddresses.get(indexDeliveryToDelete+1);
		
		//Remove the request to delete
		this.orderedAddresses.remove(indexPickUpToDelete);
		this.orderedAddresses.remove(indexDeliveryToDelete);
		
		List<Intersection> zone = new ArrayList<Intersection>(2);
		
		//Calculate the new path between the intersections before and after the PickUp Address of the deleted request
		List<Segment> pickupPath = new LinkedList<Segment>();
		Intersection[] addressesPickup = {beforePickup, afterPickup};
		pickupPath = this.findNewRoads(zone, cityMap, addressesPickup);

		//Calculate the new path between the intersections before and after the Delivery Address of the deleted request
		List<Segment> deliveryPath = new LinkedList<Segment>();
		Intersection[] addressesDelivery = {beforeDelivery, afterDelivery};
		deliveryPath = this.findNewRoads(zone, cityMap, addressesDelivery);
		
		calculateNewTotalPath(path, pickupPath, deliveryPath, beforePickup, afterPickup, beforeDelivery, afterDelivery);
	}

	/**
	 * Prints the addresses in the terminal, in the right order
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
	 * @param i1
	 * 			the first Intersection
	 * @param i2
	 * 			the second Intersection
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
	 * @param i
	 * 			the Intersection
	 * @return true if i1 is indeed the last Intersection in the LinkedList orderedAdresses, false for any other case
	 * 
	 */
	public boolean isLastIntersection(Intersection i) {
		return this.orderedAddresses.getLast() == i;
	}

	/**
	 * Fill a map to link the addresses of a request represented by intersections to the reference of this request
	 * @param initialSetOfRequests
	 * 			initial set of requests containing a depot and a list of requests
	 */
	private void calculateMapAddressToRequest(SetOfRequests initialSetOfRequests) {
		for (Request currentRequest : initialSetOfRequests.getRequests()) {
			this.mapAddressToRequest.put(currentRequest.getPickup(),currentRequest);
			this.mapAddressToRequest.put(currentRequest.getDelivery(),currentRequest);
		}
	}

	/**
	 * Reorder the pickup and delivery addresses after computing the tour
	 * @param roadsAfterComputedTour
	 * 			ordered roads
	 */
	private void reorderAddresses(List<Segment> roadsAfterComputedTour, SetOfRequests initialSetOfRequests) {
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
		Intersection depot = initialSetOfRequests.getDepot();
		this.orderedAddresses.addFirst(depot);
		this.orderedAddresses.addLast(depot);
	}
	
	/**
	 * Add an address to the ordered list of addresses and remove it from the list of unvisited addresses
	 * @param currentIntersection 
	 * 			current intersection in the list of ordered segments
	 * @param addressesToFind
	 * 			Map linking the remaining unvisited pickup or delivery addresses with their request 
	 */
	private void addIntersectionToOrderedList(Intersection currentIntersection, HashMap<Intersection, Request> addressesToFind) {
		this.orderedAddresses.add(currentIntersection);
		addressesToFind.remove(currentIntersection);
	}
	
	private List<Segment> adjustRoadMap(List<Intersection> zone, CityMap cm){
		List<Segment> path = new LinkedList<Segment>();
		
		CompleteGraph pickupGraph = new CompleteGraph(cm, zone);
		
		for(int i = 0; i < zone.size()-1; i++) {
			path.addAll(this.constructPath(zone.get(i), zone.get(i+1), cm, pickupGraph));
		}
		return path;
	}

	private List<Segment> constructPath(Intersection first, Intersection second, CityMap cm, CompleteGraph g){
		
		List<Segment> path = new LinkedList<Segment>();
		
		List<Integer> intermediateNodes = new LinkedList<Integer>();
		int firstInt = cm.getIntFromIntersectionMap(first);
		int secondInt = cm.getIntFromIntersectionMap(second);
		int[] precedence = g.getPrecedenceOfANode(firstInt);
		for (int i=secondInt; i!= firstInt; i=precedence[i]) {
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

	private void addIntersectionToPath(ListIterator<Segment> iterator, Segment next, 
										List<Segment> listSegments, Intersection intersectionLimit ) {
		while(next.getOrigin() != intersectionLimit && iterator.hasNext()) {
			if(listSegments != null) {
				listSegments.add(next);
			}
			next = iterator.next();
		}
	}

	private List<Segment> findNewRoads(List<Intersection> zone, CityMap cityMap, Intersection[] addresses) {
		zone.clear();
		for(Intersection address : addresses) {
			zone.add(address);
		}
		return adjustRoadMap(zone, cityMap);
	}
	
	private List<Segment> calculateNewTotalPath(List<Segment> path, List<Segment> pickupPath, List<Segment> deliveryPath,
			Intersection beforePickup, Intersection afterPickup, Intersection beforeDelivery, Intersection afterDelivery){

		List<Segment> beginning = new LinkedList<Segment>();
		List<Segment> middle = new LinkedList<Segment>();
		List<Segment> end = new LinkedList<Segment>();
		ListIterator<Segment> iterator = path.listIterator();
		Segment next = iterator.next();
		
		this.addIntersectionToPath(iterator, next, beginning, beforePickup);
		this.addIntersectionToPath(iterator, next, null, afterPickup);
		this.addIntersectionToPath(iterator, next, middle, beforeDelivery);
		this.addIntersectionToPath(iterator, next, null, afterDelivery);
		this.addIntersectionToPath(iterator, next, end, null);
		
		if(!this.isLastIntersection(afterDelivery))
			end.add(next);
		
		path.clear();
		path.addAll(beginning);
		path.addAll(pickupPath);
		path.addAll(middle);
		path.addAll(deliveryPath);
		path.addAll(end);
		return path;
	}
}