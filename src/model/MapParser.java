package model;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The class responsible for parsing an XML file passed in as a file path
 * (string) retrieving all intersections and segments. If any error were to
 * occur, the parser should fail silently (without crashing the application).
 */
public class MapParser extends Parser {

	private List<Intersection> intersectionsList;
	private List<Segment> segmentsList;

	public MapParser(String fp) throws Exception {
		super(fp);
	}

	public CityMap loadMap() {

		NodeList interList = doc.getElementsByTagName("intersection");
		NodeList segList = doc.getElementsByTagName("segment");

		intersectionsList = new LinkedList<Intersection>();
		segmentsList = new LinkedList<Segment>();

		CityMap map;

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
			Intersection origin = findIntersection(idOrigin);
			Intersection destination = findIntersection(idDestination);
			Segment segment = createSegment(origin, destination, name, length);
			origin.addNeighbour(destination);
			segmentsList.add(segment);
		}

		map = createMap(intersectionsList, segmentsList);
		return map;
	}

	private Intersection createIntersection(String id, float latitude, float longitude) {
		return new Intersection(id, longitude, latitude);
	}

	private Segment createSegment(Intersection origin, Intersection destination, String name, float length) {
		return new Segment(origin, destination, name, length);
	}

	private CityMap createMap(List<Intersection> intersec, List<Segment> seg) {
		return new CityMap(intersec, seg);
	}

	private Intersection findIntersection(String id) {
		for (Intersection i : intersectionsList) {
			if (i.getNumber().equals(id))
				return i;
		}
		return null;
	}
}
