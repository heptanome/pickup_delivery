package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CityMap;
import model.Intersection;
import model.Request;
import model.Segment;
import model.SetOfRequests;
import tsp.CompleteGraph;

class CityMapTest {
	private List<Intersection> intersections;
	private List<Segment> segments;
	private int nbVertices;
	private Map<String, Integer> numberToIdMap;
	
	private CityMap cityMap;

	@BeforeEach
	void setUp() throws Exception {
		intersections = new ArrayList<Intersection>();
		segments = new ArrayList<Segment>();
		intersections.add(new Intersection("0", 0, 0));
		for (int i = 1; i < 10; i++) {
			intersections.add(new Intersection(Integer.toString(i), i, i));
			segments.add(new Segment(intersections.get(i-1).getNumber(), intersections.get(i).getNumber(), Integer.toString(i), (float) 1.4)); 
		}
		
		cityMap = new CityMap(intersections, segments);
		this.nbVertices = cityMap.getNbVertices();
		
		numberToIdMap = new HashMap<String,Integer>();
		
		int index = 0;
		for(Intersection intersection : this.intersections) {
			numberToIdMap.put(intersection.getNumber(),index);
			index ++;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCityMap() {
		assertTrue(cityMap != null);
	}

	@Test
	void testToString() {
		// System.out.println(cityMap.toString());
		// TODO
	}

	@Test
	void testGetNbVertices() {
		assertEquals(cityMap.getNbVertices(), intersections.size());
	}

	@Test
	void testGetNumberIdMap() {
		assertEquals(numberToIdMap, cityMap.getNumberIdMap());
		
	}

	@Test
	void testGetIntFromNumberMap() {
		assertEquals(numberToIdMap.get("1"), cityMap.getIntFromIntersectionMap("1"));
	}

	@Test
	void testGetStringFromIdMap() {
		Map<Integer, String> idToNumberMap = new HashMap<Integer,String>();

		int index = 0;
		for(Intersection intersection : this.intersections) {
			idToNumberMap.put(index,intersection.getNumber());
			index ++;
		}
		assertEquals(idToNumberMap.get(1), cityMap.getStringFromIdMap(1));
	}

	@Test
	void testGetInstersections() {
		assertEquals(intersections, cityMap.getInstersections());
	}

	@Test
	void testGetSegments() {
		assertEquals(segments, cityMap.getSegments());
	}

	@Test
	void testGetSegmentFromPoints() {
		assertEquals(segments.get(0), cityMap.getSegmentFromPoints("0", "1"));
		assertNull(cityMap.getSegmentFromPoints("0", "2"));
	}

}
