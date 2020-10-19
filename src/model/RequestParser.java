package model;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;

public class RequestParser extends Parser {
  public RequestParser(String fp) {
    super(fp);

    /*NodeList depotList = doc.getElementsByTagName("depot");
    for (Node n : asList(depotList)) {
      Element depot = (Element) n;
      System.out.println(depot.getAttribute("address"));
      System.out.println(depot.getAttribute("departureTime"));
      System.out.println("");
    }

    NodeList reqList = doc.getElementsByTagName("request");
    for (Node n : asList(reqList)) {
      Element inter = (Element) n;
      System.out.println(inter.getAttribute("pickupAddress"));
      System.out.println(inter.getAttribute("deliveryAddress"));
      System.out.println(inter.getAttribute("deliveryDuration"));
      System.out.println(inter.getAttribute("pickupDuration"));
      System.out.println("");
    }*/
    
  }
  public SetOfRequests loadRequests() {
	SimpleDateFormat format = new SimpleDateFormat("H:M:s");
	
	NodeList depotList = doc.getElementsByTagName("depot");
    NodeList reqList = doc.getElementsByTagName("request");

    LinkedList<Request> requestsList = new LinkedList<Request>();
    
    Date departure = new Date();
    String idDepot = "non initializated";

    SetOfRequests sor;

    for (Node n : asList(depotList)) {
    	Element depot = (Element) n;
        idDepot = depot.getAttribute("address");
        try {
        	departure = format.parse(depot.getAttribute("departureTime"));
        }catch(Exception e) {
        	
        }
    }
    
    for (Node n : asList(reqList)) {
        Element request = (Element) n;
        String puAddress = request.getAttribute("pickupAddress");
        String delAddress = request.getAttribute("deliveryAddress");
        int delDuration = Integer.parseInt(request.getAttribute("deliveryDuration"));
        int puDuration = Integer.parseInt(request.getAttribute("pickupDuration"));
        requestsList.add(createRequest(delAddress,puAddress,delDuration,puDuration));
      }

    sor = createTour(idDepot,departure, requestsList);
    return sor;
  }
  private Request createRequest(String delivAdd, String pickupAdd, int delivDur, int pickupDur) {
    return new Request(delivAdd, pickupAdd, delivDur, pickupDur);
  }
  private SetOfRequests createTour(String idDepot, Date departure, List<Request> req) {
	  return new SetOfRequests(idDepot,departure,req);
  }
}
