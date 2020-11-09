package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controller.command.IrrelevantFileException;

/**
 * The class responsible for parsing a set of requests XML file passed in as a
 * file path (string) retrieving all requests, the time at which the tour starts
 * and the depot's point.
 */
public class RequestParser extends Parser {

	private CityMap map;

	/**
	 * Constructor
	 * @param fp
	 * 			File to parse
	 * @param cm
	 * 			City Map where the requests are 
	 * @throws Exception
	 */
	public RequestParser(String fp, CityMap cm) throws Exception {
		super(fp);
		this.map = cm;
	}

	/**
	 * Builds the set of request from the NodeLists
	 * 
	 * @return a populated set of requests the tour can then use
	 */
	public SetOfRequests loadRequests() {
		NodeList depotList = doc.getElementsByTagName("depot");
		NodeList reqList = doc.getElementsByTagName("request");

		LinkedList<Request> requestsList = new LinkedList<Request>();

		LocalTime departure = LocalTime.MIN;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("H:m:s");
		String idDepot = "non initializated";
		Intersection depot = null;

		SetOfRequests sor;

		for (Node n : asList(depotList)) {
			Element dep = (Element) n;
			idDepot = dep.getAttribute("address");
			depot = findIntersection(idDepot);
			try {
				departure = LocalTime.parse(dep.getAttribute("departureTime"),format);
			} catch (Exception e) {

			}
		}

		int numeroReq = 1;
		for (Node n : asList(reqList)) {
			Element request = (Element) n;
			String puAddress = request.getAttribute("pickupAddress");
			String delAddress = request.getAttribute("deliveryAddress");
			int delDuration = Integer.parseInt(request.getAttribute("deliveryDuration"))/60;
			int puDuration = Integer.parseInt(request.getAttribute("pickupDuration"))/60;
			Intersection pickup = findIntersection(puAddress);
			Intersection delivery = findIntersection(delAddress);
			requestsList.add(createRequest(delivery, pickup, delDuration, puDuration, numeroReq));
			numeroReq++;
		}
		
		if (depot == null || departure == null || requestsList.size() <1) {
			throw new IrrelevantFileException("Le fichier ne contient pas de requêtes correcte");
		}

		sor = createTour(depot, departure, requestsList);
		return sor;
	}

	private Request createRequest(Intersection delivAdd, Intersection pickupAdd, int delivDur, int pickupDur, int numeroReq) {
		return new Request(delivAdd, pickupAdd, delivDur, pickupDur, numeroReq);
	}

	private SetOfRequests createTour(Intersection idDepot, LocalTime departure, List<Request> req) {
		return new SetOfRequests(idDepot, departure, req);
	}

	private Intersection findIntersection(String id) {
		for (Intersection i : map.getInstersections()) {
			if (i.getNumber().equals(id))
				return i;
		}
		return null;
	}
}
