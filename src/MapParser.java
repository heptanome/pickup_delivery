import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapParser extends Parser {
  MapParser(String fp) {
    super(fp);

    NodeList interList = doc.getElementsByTagName("intersection");
    for (Node n : asList(interList)) {
      Element inter = (Element) n;
      System.out.println(inter.getAttribute("id"));
      System.out.println(inter.getAttribute("latitude"));
      System.out.println(inter.getAttribute("longitude"));
      System.out.println("");
    }

    NodeList segList = doc.getElementsByTagName("segment");
    for (Node n : asList(segList)) {
      Element seg = (Element) n;
      System.out.println(seg.getAttribute("destination"));
      System.out.println(seg.getAttribute("length"));
      System.out.println(seg.getAttribute("name"));
      System.out.println("");
    }
  }

  // to run inside of the src directory
  // javac *.java && java MapParser ../XML_data/smallMap.xml
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("nope");
      System.exit(1);
    }
    MapParser mp = new MapParser(args[0]);
  }
}
