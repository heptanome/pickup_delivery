package model;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapParser extends Parser {
	public MapParser(String fp) {
		super(fp);
	}

	public Map loadMap() {
		NodeList interList = doc.getElementsByTagName("intersection");
		NodeList segList = doc.getElementsByTagName("segment");

		LinkedList<Intersection> intersectionsList = new LinkedList<Intersection>();
		LinkedList<Segment> segmentsList = new LinkedList<Segment>();

		Map map;

		for (Node n : asList(interList)) {
			Element inter = (Element) n;
			String id = inter.getAttribute("id");
			float latitude = Float.parseFloat(inter.getAttribute("latitude"));
			float longitude = Float.parseFloat(inter.getAttribute("longitude"));
			intersectionsList.add(createIntersection(id, latitude, longitude));
		}

		for (Node n : asList(segList)) {
			Element seg = (Element) n;
			String idOrigin = seg.getAttribute("origin");
			String idDestination = seg.getAttribute("destination");
			float length = Float.parseFloat(seg.getAttribute("length"));
			String name = seg.getAttribute("name");
			segmentsList.add(createSegment(idOrigin, idDestination, name, length));
		}

		map = createMap(intersectionsList, segmentsList);
		return map;
	}

	private Intersection createIntersection(String id, float latitude, float longitude) {
		return new Intersection(id, longitude, latitude);
	}

	private Segment createSegment(String idOrigin, String idDestination, String name, float length) {
		return new Segment(idOrigin, idDestination, name, length);
	}

	private Map createMap(List<Intersection> intersec, List<Segment> seg) {
		return new Map(intersec, seg);
	}
}
