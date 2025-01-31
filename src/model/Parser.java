package model;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parser, which both MapParser and RequestParser depend on. This class reads a
 * file and creates an iterator object with the XML nodes. This parser was
 * inspired from
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 */
public abstract class Parser {
	protected Document doc;

	/**
	 * Constructor
	 * 
	 * @param fp File to parse
	 * @throws Exception Can fail if the file can not be parsed for whatever reason
	 */
	public Parser(String fp) throws Exception {
		if (fp.isEmpty()) {
			throw new IllegalArgumentException();
		}
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.doc = dBuilder.parse(fp);
		this.doc.getDocumentElement().normalize();
	}

	/**
	 * Creates a a NodeList iterator, inspired by
	 * https://stackoverflow.com/a/19591302
	 * 
	 * @param n the NodeList to later iterate on
	 * @return a NodeListWrapper
	 */
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
