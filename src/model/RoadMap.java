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
	private HashMap<Intersection, List<Request>> mapPickupAddressToRequest;
	private HashMap<Intersection, List<Request>> mapDeliveryAddressToRequest;
	private LinkedList<Intersection> orderedAddresses;

	/**
	 * Constructor
	 * @param roadsAfterComputedTour ordered roads
	 * @param initialSetOfRequests 
	 * 			initial List of unordered requests with a depot and a departure time
	 */
	public RoadMap(List<Segment> roadsAfterComputedTour, SetOfRequests initialSetOfRequests) {
		this.mapPickupAddressToRequest = new HashMap<Intersection,List<Request>>();
		this.mapDeliveryAddressToRequest = new HashMap<Intersection,List<Request>>();
		this.orderedAddresses = new LinkedList<Intersection>();
		this.calculateMapAddressToRequest(initialSetOfRequests);
		this.reorderAddresses(roadsAfterComputedTour);
		Intersection depot = initialSetOfRequests.getDepot();
		this.orderedAddresses.addFirst(depot);
		this.orderedAddresses.addLast(depot);
	}
	
	public LinkedList<Float> calculateTime(List<Segment> path, Float departureTime) {
		LinkedList<Float> roadsTime = new LinkedList<Float>();
		roadsTime.add(departureTime);
		Float actualTime = departureTime;
		
		ListIterator<Intersection> iterator = orderedAddresses.listIterator();
		Intersection nextRequestPoint = iterator.next();
		
		for (Segment road : path) {
			actualTime = actualTime + road.getLength()/1500;
			Intersection destinationIntersection = road.getDestination();
			if (destinationIntersection == nextRequestPoint) {
				//The delivery man delivers all items
				if(this.mapDeliveryAddressToRequest.containsKey(destinationIntersection)) {
					List<Request> listRequests = this.mapDeliveryAddressToRequest.get(destinationIntersection);
					for(Request request : listRequests) {
						actualTime = actualTime + request.getDeliveryDuration();
					}
				}
				//The delivery man picks up all items
				if(this.mapPickupAddressToRequest.containsKey(destinationIntersection)) {
					List<Request> listRequests = this.mapPickupAddressToRequest.get(destinationIntersection);
					for(Request request : listRequests) {
						actualTime = actualTime + request.getPickupDuration();
					}
				}
				if(iterator.hasNext()) {
					nextRequestPoint = iterator.next();
				}
			}
			roadsTime.add(actualTime);
		}
		return roadsTime;
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
							Intersection beforeDelivery, CityMap cityMap, LinkedList<Segment> path) {

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

		int indexBeforePickup = this.orderedAddresses.indexOf(beforePickup)+1;
		Intersection afterPickup = this.orderedAddresses.get(indexBeforePickup);
		int indexBeforeDelivery = this.orderedAddresses.indexOf(beforeDelivery)+1;
		Intersection afterDelivery = this.orderedAddresses.get(indexBeforeDelivery);

		this.orderedAddresses.add(indexBeforePickup, newDelivery);
		this.orderedAddresses.add(indexBeforeDelivery, newPickup);
		this.addARequestToMap(this.mapPickupAddressToRequest, newPickup, newRequest);
		this.addARequestToMap(this.mapDeliveryAddressToRequest, newDelivery, newRequest);
		
		LinkedList<Segment> pickupPath = new LinkedList<Segment>();
		LinkedList<Segment> deliveryPath = new LinkedList<Segment>();
		List<Intersection> zone = new ArrayList<Intersection>(4);
		if(beforePickup == beforeDelivery) {
			Intersection[] addresses = {beforePickup, newPickup, newDelivery, afterDelivery};
			pickupPath = this.findNewRoads(zone, cityMap, addresses);
			afterPickup = afterDelivery;
			beforeDelivery = afterDelivery;
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
	 * @param requestToDelete Request to delete
	 * @param cityMap Loaded map containing all segments and intersections to calculate the new path
	 * @param path Actual path after the tour's been computed
	 */
	public void deleteRequest(Request requestToDelete, CityMap cityMap, LinkedList<Segment> path){
		
		/* There are two main cases :
		  * 	- the delivery address of the request to delete does not follow its pickup address : 
		  * 		2 new paths have to be computed and added to the tour :
		  * 		from beforePickup to afterPickup and from beforeDelivery to afterDelivery
		  * 
		  * 	- the delivery address of the request to delete does not follow its pickup address :
		  * 		1 new path has to be computed and added to the tour :
		  * 		from beforePickup to afterDelivery
		  * 
		  * The method is in three steps :
		  * 	- finding important points, such as afterDelivery and afterPickup, and adding the new Request into the
		  * 		the list of addresses that have to be visited
		  * 	
		  * 	- computing the new shortest intermediates paths, and finding all the intermediates nodes from the cityMap
		  * 	
		  * 	- cutting and concatenating the old path in order to create a new path with the new intermediates paths.
		  * */

		int indexPickUpToDelete = this.orderedAddresses.indexOf(requestToDelete.getPickup());
		int indexDeliveryToDelete = this.orderedAddresses.indexOf(requestToDelete.getDelivery());
		
		//Get the intersections before and after the request to delete
		Intersection beforePickup = this.orderedAddresses.get(indexPickUpToDelete-1);
		Intersection afterPickup = this.orderedAddresses.get(indexPickUpToDelete+1);
		Intersection beforeDelivery = this.orderedAddresses.get(indexDeliveryToDelete-1);
		Intersection afterDelivery = this.orderedAddresses.get(indexDeliveryToDelete+1);
		
		//Remove the request to delete
		this.orderedAddresses.remove(indexPickUpToDelete);
		this.orderedAddresses.remove(indexDeliveryToDelete-1);
		this.removeARequestToMap(this.mapPickupAddressToRequest, requestToDelete.getPickup(), requestToDelete);
		this.removeARequestToMap(this.mapDeliveryAddressToRequest, requestToDelete.getDelivery(), requestToDelete);
		
		LinkedList<Segment> pickupPath = new LinkedList<Segment>();
		LinkedList<Segment> deliveryPath = new LinkedList<Segment>();
		List<Intersection> zone = new ArrayList<Intersection>(2);
		
		// Compute the new shortest intermediates paths
		if (beforeDelivery == requestToDelete.getPickup()) {
			Intersection[] addressesPickup = {beforePickup, afterDelivery};
			pickupPath = this.findNewRoads(zone, cityMap, addressesPickup);
		} else {
			Intersection[] addressesPickup = {beforePickup, afterPickup};
			pickupPath = this.findNewRoads(zone, cityMap, addressesPickup);
	
			Intersection[] addressesDelivery = {beforeDelivery, afterDelivery};
			deliveryPath = this.findNewRoads(zone, cityMap, addressesDelivery);
		}
		calculateNewTotalPath(path, pickupPath, deliveryPath, beforePickup, afterPickup, beforeDelivery, afterDelivery);
	}

	
	/**
	 * Print the addresses in the terminal, in the right order
	 */
	public void printReorderedAddresses() {
		int index = 0;
		for (Intersection currentAddress : this.orderedAddresses) {
			System.out.println("Address number : "+index+" "+currentAddress.toString());
			index++;
		}
	}

	
	/**
	 * Check if an intersection is before another one in the orderedAddresses liked
	 * list
	 * 
	 * @param i1 the first Intersection
	 * @param i2 the second Intersection
	 * @return true if i1 is indeed before i2 in the LinkedList orderedAdresses or
	 *         if i1 == i2, false is it is after or if one of the two intersections
	 *         were not found in the list
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
	 * Check if an intersection is the last of the LinkedList orderedAddresses
	 * @param i
	 * 			the Intersection
	 * @return true if i1 is indeed the last Intersection in the LinkedList orderedAdresses, false for any other case
	 * 
	 */
	public boolean isLastIntersection(Intersection i) {
		return this.orderedAddresses.getLast() == i;
	}

	public String printRoadMap(List<Segment> path) {
		int index = 1;
		String message = "";
		ListIterator<Intersection> itIntersection = this.orderedAddresses.listIterator();
		ListIterator<Segment> itSegment = path.listIterator();
		Segment currentSegment = itSegment.next();
		Segment previousSegment = currentSegment;
		Intersection currentIntersection = itIntersection.next();
		String status = this.getStatus(currentIntersection);
		float length = 0;
		
		while (itIntersection.hasNext()) {
			currentIntersection = itIntersection.next();
			length = currentSegment.getLength();
			if (!itIntersection.hasNext())
				break;
			
			status = this.getStatus(currentIntersection);
			message += "Point " + index + " : " + status + "\n";
			
			String name = currentSegment.getName();
			length = 0;
			
			while(currentSegment.getDestination() != currentIntersection) {
				currentSegment = itSegment.next();
				if(currentSegment.getName().equals(name)|| currentSegment.getName().equals("")) {
					length += currentSegment.getLength();
				} else {
					if(length > 0) { //prevent from printing when you leave a street
						message += "    Follow road \""+name+"\" for "+length+"\n";
					}
					length = currentSegment.getLength();
					name = currentSegment.getName();
				}
				previousSegment = currentSegment;
			}
			itSegment.previous();
			previousSegment = itSegment.previous();
			if(currentSegment.getName().equals(previousSegment.getName())|| currentSegment.getName().equals("")) {
				length += currentSegment.getLength();
			} else {
				length =  currentSegment.getLength();
			}
			message += "    Follow road \""+currentSegment.getName()+"\" for "+length;
			message += "\n";
			itSegment.next();
			previousSegment = itSegment.next();
			
			index++;
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		currentSegment = itSegment.next();
		previousSegment = currentSegment;
		message += "Go back to the Depot : \n";
		String name = currentSegment.getName();
		length = currentSegment.getLength();
		
		while(itSegment.hasNext()) {
			
			currentSegment = itSegment.next();
			if(currentSegment.getName().equals(name)|| currentSegment.getName().equals("")) {
				length += currentSegment.getLength();
				if(!currentSegment.getName().equals("")) {
					name = currentSegment.getName();
				}
			} else {
				message += "    Follow road \""+name+"\" for "+length+"\n";
				length = currentSegment.getLength();
				name = currentSegment.getName();
				
			}
			previousSegment = currentSegment;
		}
		itSegment.previous();
		previousSegment = itSegment.previous();
		if(currentSegment.getName().equals(previousSegment.getName())|| currentSegment.getName().equals("")) {
			length += currentSegment.getLength();
		} else {
			length = currentSegment.getLength();
		}
		message += "    Follow road \""+currentSegment.getName()+"\" for "+length;
		return message;
	}

	private String getStatus(Intersection intersection) {
		String type = "";
		if (this.mapPickupAddressToRequest.containsKey(intersection)) {
			type = "pickup ";
			if(this.mapDeliveryAddressToRequest.containsKey(intersection)) {
				type += "and delivery ";
			}
		} else if (this.mapDeliveryAddressToRequest.containsKey(intersection)){
			type = "delivery ";
		}
		return type+"point";
	}

	/**
	 * Fill a map to link the addresses of a request represented by intersections to the reference of this request
	 * @param initialSetOfRequests
	 * 			initial set of requests containing a depot and a list of requests
	 */
	private void calculateMapAddressToRequest(SetOfRequests initialSetOfRequests) {
		//Add the request
		for (Request currentRequest : initialSetOfRequests.getRequests()) {
			Intersection currentPickup = currentRequest.getPickup();
			Intersection currentDelivery = currentRequest.getDelivery();
			this.addARequestToMap(this.mapPickupAddressToRequest, currentPickup, currentRequest);
			this.addARequestToMap(this.mapDeliveryAddressToRequest, currentDelivery, currentRequest);
		}
	}
	
	private void addARequestToMap(HashMap<Intersection, List<Request>> map, Intersection currentIntersection, Request currentRequest) {
		if (map.containsKey(currentIntersection)) {
			List<Request> requestList = map.get(currentIntersection);
			requestList.add(currentRequest);
		} else {
			List<Request> newRequestList = new LinkedList<Request>();
			newRequestList.add(currentRequest);
			map.put(currentIntersection, newRequestList);
		}
	}
	
	private void removeARequestToMap(HashMap<Intersection, List<Request>> map, Intersection currentIntersection, Request currentRequest) {
		if (map.containsKey(currentIntersection)) {
			List<Request> requestsList = map.get(currentIntersection);
			if(requestsList.contains(currentRequest)) {
				requestsList.remove(currentRequest);
			}
			if(requestsList.isEmpty()) {
				map.remove(currentIntersection);
			}
		}
	}

	/**
	 * Reorder the pickup and delivery addresses after computing the tour
	 * @param roadsAfterComputedTour
	 * 			ordered roads
	 */
	private void reorderAddresses(List<Segment> roadsAfterComputedTour) {
		HashMap<Intersection, List<Request>> pickupAddressesToFind = new HashMap<Intersection,List<Request>>();
		pickupAddressesToFind.putAll(mapPickupAddressToRequest); 
		HashMap<Intersection, List<Request>> deliveryAddressesToFind = new HashMap<Intersection,List<Request>>();
		deliveryAddressesToFind.putAll(mapDeliveryAddressToRequest); 
		
		for (Segment currentSegment : roadsAfterComputedTour) {
			Intersection currentIntersection = currentSegment.getOrigin();
			if (deliveryAddressesToFind.containsKey(currentIntersection)) {
				List<Request> requests = deliveryAddressesToFind.get(currentIntersection);
				boolean everyPickupAddressesVisited = true;
				for (Request request : requests) {
					Intersection pickUpAddress = request.getPickup();
					if (pickupAddressesToFind.containsKey(pickUpAddress)) {
						everyPickupAddressesVisited = false;
					}
				}
				//If every pickup addresses were visited we add the delivery address
				if (everyPickupAddressesVisited) {
					this.orderedAddresses.add(currentIntersection);
					deliveryAddressesToFind.remove(currentIntersection);
				}
				/* If the delivery address is also a pickup address
					we remove the address from list of pickup address to visit
				*/
				if(pickupAddressesToFind.containsKey(currentIntersection)) {
					pickupAddressesToFind.remove(currentIntersection);
				}
			} else if (pickupAddressesToFind.containsKey(currentIntersection)){
				this.orderedAddresses.add(currentIntersection);
				pickupAddressesToFind.remove(currentIntersection);
			}
			// We stop to iterate on each segment when all pickup and delivery addresses were found
			if (pickupAddressesToFind.isEmpty() && deliveryAddressesToFind.isEmpty()) {
				break;
			}
		}
	}


	private LinkedList<Segment> findNewRoads(List<Intersection> zone, CityMap cityMap, Intersection[] addresses) {
		zone.clear();
		for (Intersection address : addresses) {
			zone.add(address);
		}
		return adjustRoadMap(zone, cityMap);
	}
	
	private LinkedList<Segment> adjustRoadMap(List<Intersection> zone, CityMap cm) {
		LinkedList<Segment> path = new LinkedList<Segment>();
		
		CompleteGraph pickupGraph = new CompleteGraph(cm, zone);

		for (int i = 0; i < zone.size() - 1; i++) {
			path.addAll(this.constructPath(zone.get(i), zone.get(i + 1), cm, pickupGraph));
		}
		return path;
	}
	
	private List<Segment> constructPath(Intersection first, Intersection second, CityMap cm, CompleteGraph g){

		List<Segment> path = new LinkedList<Segment>();

		List<Integer> intermediateNodes = new LinkedList<Integer>();
		int firstInt = cm.getIntFromIntersectionMap(first);
		int secondInt = cm.getIntFromIntersectionMap(second);
		int[] precedence = g.getPrecedenceOfANode(firstInt);
		for (int i = secondInt; i != firstInt; i = precedence[i]) {
			intermediateNodes.add(i);
		}
		intermediateNodes.add(firstInt);

		ListIterator<Integer> iterator = intermediateNodes.listIterator(intermediateNodes.size());
		Intersection currentNodeInter = cm.getIntersectionFromIdMap(iterator.previous());
		Intersection previousNodeInter = currentNodeInter;
		while (iterator.hasPrevious()){
			int previousNodeId = iterator.previous();
			previousNodeInter = cm.getIntersectionFromIdMap(previousNodeId);
			path.add(cm.getSegmentFromInter(currentNodeInter, previousNodeInter));
			currentNodeInter = previousNodeInter;
		}
		return path;
	}
	
	private Segment addIntersectionToPath(ListIterator<Segment> iterator, Segment next, 
			List<Segment> listSegments, Intersection intersectionLimit ) {
		while (next.getOrigin() != intersectionLimit && iterator.hasNext()) {
			listSegments.add(next);
			next = iterator.next();
		}
		return next;
	}
	
	private Segment addIntersectionToPathBegin(ListIterator<Segment> iterator, Segment next,
			List<Segment> listSegments, Intersection intersectionLimit ) {
		
		if (this.mapPickupAddressToRequest.containsKey(intersectionLimit)) {
			while (next.getOrigin() != intersectionLimit && iterator.hasNext()) {
				listSegments.add(next);
				next = iterator.next();
			}
		} else if (this.mapDeliveryAddressToRequest.containsKey(intersectionLimit)) {
			List<Request> listRequests = this.mapDeliveryAddressToRequest.get(intersectionLimit);
			int indexLastPickup = 0;
			for (Request request : listRequests) {
				int currentIndex = this.orderedAddresses.indexOf(request.getPickup());
				if (indexLastPickup < currentIndex) {
					indexLastPickup = currentIndex;
				}
			}
			Intersection lastPickup = this.orderedAddresses.get(indexLastPickup);
			while (next.getOrigin() != lastPickup && iterator.hasNext()) {
				listSegments.add(next);
				next = iterator.next();
			}
			while (next.getOrigin() != intersectionLimit && iterator.hasNext()) {
				listSegments.add(next);
				next = iterator.next();
			}
		}
		return next;
	}
	
	private LinkedList<Segment> calculateNewTotalPath(LinkedList<Segment> path, LinkedList<Segment> pickupPath,
			LinkedList<Segment> deliveryPath, Intersection beforePickup, Intersection afterPickup,
			Intersection beforeDelivery, Intersection afterDelivery){

		LinkedList<Segment> beginning = new LinkedList<Segment>();
		LinkedList<Segment> middle = new LinkedList<Segment>();
		LinkedList<Segment> end = new LinkedList<Segment>();
		ListIterator<Segment> iterator = path.listIterator();
		Segment next = iterator.next();
		
		this.addIntersectionToPathBegin(iterator, next, beginning, beforePickup);
		while (next.getOrigin() != afterPickup && iterator.hasNext()) {
			next = iterator.next();
		}
		this.addIntersectionToPath(iterator, next, middle, beforeDelivery);
		while (next.getOrigin() != afterDelivery && iterator.hasNext()) {
			next = iterator.next();
		}
		Segment lastSegment = this.addIntersectionToPath(iterator, next, end, null);
		
		if (lastSegment != null) {
			if (!end.isEmpty()) {
				end.add(lastSegment);
			} else if (!deliveryPath.isEmpty()) {
				if (deliveryPath.getLast().getDestination() != lastSegment.getDestination()) {
					end.add(lastSegment);
				}
			} else if (!middle.isEmpty()) {
				if (middle.getLast().getDestination() != lastSegment.getDestination()) {
					end.add(lastSegment);
				}
			} else if (!pickupPath.isEmpty()) {
				if (pickupPath.getLast().getDestination() != lastSegment.getDestination()) {
					end.add(lastSegment);
				}
			}
		}
		
		path.clear();
		path.addAll(beginning);
		path.addAll(pickupPath);
		path.addAll(middle);
		path.addAll(deliveryPath);
		path.addAll(end);
		return path;
	}

		/**
	 * Gets the intersection before another one in the orderedAdrdresses list
	 * @param i
	 * 			the Intersection 
	 * @return the Intersection visited before i
	 * 
	 */
	public Intersection getIntersectionBefore(Intersection i) {
		int indexOfI = orderedAddresses.indexOf(i);
		System.out.println(indexOfI);
		Intersection iBefore = orderedAddresses.get(indexOfI -1);
		return iBefore;
	}
	
	public LinkedList<Intersection> getOrderedAddresses() {
		return orderedAddresses;
	}
	
	public HashMap<Intersection, List<Request>> getMapPickupAddressToRequest() {
		return mapPickupAddressToRequest;
	}
	
	public HashMap<Intersection, List<Request>> getMapDeliveryAddressToRequest() {
		return mapDeliveryAddressToRequest;
	}
}