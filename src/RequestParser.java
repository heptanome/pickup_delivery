import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RequestParser extends Parser {
  RequestParser(String fp) {
    super(fp);

    NodeList depotList = doc.getElementsByTagName("depot");
    for (Node n : asList(depotList)) {
      Element depot = (Element)n;
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
    }
  }

  // to run inside of the src directory
  // javac *.java && java RequestParser ../XML_data/requestsLarge7.xml
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("nope");
      System.exit(1);
    }
    RequestParser mp = new RequestParser(args[0]);
  }
}
