package model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;

public class RequestParser extends Parser {
	
	private CityMap map;
	
	public RequestParser(String fp, CityMap cm) throws Exception {
		super(fp);
		this.map = cm;
	}

	public SetOfRequests loadRequests() {
		SimpleDateFormat format = new SimpleDateFormat("H:M:s");

		NodeList depotList = doc.getElementsByTagName("depot");
		NodeList reqList = doc.getElementsByTagName("request");

		LinkedList<Request> requestsList = new LinkedList<Request>();

		Date departure = new Date();
		String idDepot = "non initializated";
		Intersection depot = null;

		SetOfRequests sor;

		for (Node n : asList(depotList)) {
			Element dep = (Element) n;
			idDepot = dep.getAttribute("address");
			depot = findIntersection(idDepot);
			try {
				departure = format.parse(dep.getAttribute("departureTime"));
			} catch (Exception e) {

			}
		}

		for (Node n : asList(reqList)) {
			Element request = (Element) n;
			String puAddress = request.getAttribute("pickupAddress");
			String delAddress = request.getAttribute("deliveryAddress");
			int delDuration = Integer.parseInt(request.getAttribute("deliveryDuration"));
			int puDuration = Integer.parseInt(request.getAttribute("pickupDuration"));
			Intersection pickup = findIntersection(puAddress);
			Intersection delivery = findIntersection(delAddress);
			requestsList.add(createRequest(delivery, pickup, delDuration, puDuration));
		}

		sor = createTour(depot, departure, requestsList);
		return sor;
	}

	private Request createRequest(Intersection delivAdd, Intersection pickupAdd, int delivDur, int pickupDur) {
		return new Request(delivAdd, pickupAdd, delivDur, pickupDur);
	}

	private SetOfRequests createTour(Intersection idDepot, Date departure, List<Request> req) {
		return new SetOfRequests(idDepot, departure, req);
	}
	
	private Intersection findIntersection(String id) {
		for(Intersection i : map.getInstersections()) {
			if(i.getNumber().equals(id))
				return i;
		}
		return null;
	}
}
