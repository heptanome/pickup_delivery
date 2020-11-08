package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The class responsible for parsing a map XML file passed in as a file path
 * (string) retrieving all intersections and segments. If any error were to
 * occur, the parser should fail silently (without crashing the application).
 */
public class MapParser extends Parser {

	private final int NB_THREADS = 3;

	private List<Intersection> intersectionsList;
	private List<Segment> segmentsList;

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private HashMap<String, Intersection> interMap;

	public MapParser(String fp) throws Exception {
		super(fp);
		interMap = new HashMap<>();
	}

	/**
	 * Builds a CityMap from the intersections and segments NodeLists
	 * 
	 * @return a functioning CityMap
	 * @throws InterruptedException
	 */
	public CityMap loadMap() throws InterruptedException {
		NodeList interList = doc.getElementsByTagName("intersection");
		NodeList segList = doc.getElementsByTagName("segment");

		intersectionsList = new LinkedList<Intersection>();
		segmentsList = new LinkedList<Segment>();

		CityMap map;
		Thread parserThreads[] = new Thread[NB_THREADS + 1];
		int interSize = interList.getLength() / NB_THREADS;
		int segSize = segList.getLength() / NB_THREADS;
		List<Node> castedInterList = asList(interList);
		List<Node> castedSegList = asList(segList);

		// parsing intersections
		for (int i = 0; i < NB_THREADS; i++) {
			List<Node> partInterList;
			partInterList = castedInterList.subList(interSize * i, interSize * (i + 1));
			parserThreads[i] = new IntersectionThread(partInterList);
			parserThreads[i].start();
		}

		List<Node> partInterList;
		partInterList = castedInterList.subList(interSize * NB_THREADS, interList.getLength());
		parserThreads[NB_THREADS] = new IntersectionThread(partInterList);
		parserThreads[NB_THREADS].start();

		for (int i = 0; i <= NB_THREADS; i++) {
			parserThreads[i].join();
		}

		// parsing segments
		for (int i = 0; i < NB_THREADS; i++) {
			List<Node> partSegList;
			partSegList = castedSegList.subList(segSize * i, segSize * (i + 1));
			parserThreads[i] = new SegmentThread(partSegList);
			parserThreads[i].start();
		}

		List<Node> partSegList;
		partSegList = castedSegList.subList(segSize * NB_THREADS, segList.getLength());
		parserThreads[NB_THREADS] = new SegmentThread(partSegList);
		parserThreads[NB_THREADS].start();

		for (int i = 0; i <= NB_THREADS; i++) {
			parserThreads[i].join();
		}

		map = createMap(intersectionsList, segmentsList);
		return map;
	}

	private Intersection createIntersection(String id, float latitude, float longitude) {
		Intersection newInter = new Intersection(id, longitude, latitude);
		interMap.put(id, newInter);
		return newInter;
	}

	private Segment createSegment(Intersection origin, Intersection destination, String name, float length) {
		return new Segment(origin, destination, name, length);
	}

	private CityMap createMap(List<Intersection> intersec, List<Segment> seg) {
		return new CityMap(intersec, seg);
	}

	class IntersectionThread extends Thread {
		private List<Node> interList;

		public IntersectionThread(List<Node> intersections) {
			this.interList = intersections;
		}

		@Override
		public void run() {
			for (Node n : interList) {
				Element inter = (Element) n;
				String id = inter.getAttribute("id");
				float latitude = Float.parseFloat(inter.getAttribute("latitude"));
				float longitude = Float.parseFloat(inter.getAttribute("longitude"));

				// must be done with the utmost precaution
				lock.writeLock().lock();
				intersectionsList.add(createIntersection(id, latitude, longitude));
				lock.writeLock().unlock();
			}
		}
	}

	class SegmentThread extends Thread {
		private List<Node> segList;

		public SegmentThread(List<Node> segments) {
			this.segList = segments;
		}

		@Override
		public void run() {
			for (Node n : segList) {
				Element seg = (Element) n;
				String idOrigin = seg.getAttribute("origin");
				String idDestination = seg.getAttribute("destination");
				float length = Float.parseFloat(seg.getAttribute("length"));
				String name = seg.getAttribute("name");
				Intersection origin = interMap.get(idOrigin);
				Intersection destination = interMap.get(idDestination);
				Segment segment = createSegment(origin, destination, name, length);

				// must be done with the utmost precaution
				lock.writeLock().lock();
				origin.addNeighbour(destination);
				segmentsList.add(segment);
				lock.writeLock().unlock();
			}
		}
	}

}
