import java.io.File;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* Parser: read and parse a given XML file
   inspired from:
      https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
*/
public abstract class Parser {
  protected Document doc;

  public Parser(String fp) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      this.doc = dBuilder.parse(fp);
      this.doc.getDocumentElement().normalize();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // iterable design pattern
  // https://stackoverflow.com/a/19591302
  public static List<Node> asList(NodeList n) {
    return n.getLength() == 0 ? Collections.<Node>emptyList() : new NodeListWrapper(n);
  }

  static final class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
    private final NodeList list;
    NodeListWrapper(NodeList l) {
      list = l;
    }
    public Node get(int index) {
      return list.item(index);
    }
    public int size() {
      return list.getLength();
    }
  }
}
