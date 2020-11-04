package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CityMap;
import model.Intersection;
import model.Segment;

class CityMapTest {
	private List<Intersection> intersections;
	private List<Segment> segments;
	private Map<Intersection, Integer> numberToIdMap;
	private final int nbIntersections = 20003;
	
	private CityMap cityMap;

	@BeforeEach
	void setUp() throws Exception {
		//Initialization segment and intersections
		intersections = new ArrayList<Intersection>();
		segments = new ArrayList<Segment>();
		intersections.add(new Intersection("0", 0, 0));
		for (int i = 1; i < nbIntersections; i++) {
			intersections.add(new Intersection(Integer.toString(i), i, i));
			segments.add(new Segment(intersections.get(i-1), intersections.get(i), Integer.toString(i), (float) 1.4)); 
		}
		
		//Initialization numberToIdMap
		numberToIdMap = new HashMap<Intersection,Integer>();
		int index = 0;
		for(Intersection intersection : this.intersections) {
			numberToIdMap.put(intersection,index);
			index ++;
		}
		
		//Initialization Map
		cityMap = new CityMap(intersections, segments);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCityMap() {
		assertTrue(cityMap != null);
	}
	
	@Test
	void testGetSegmentFromInter() {
		assertEquals(segments.get(2),cityMap.getSegmentFromInter(intersections.get(2), intersections.get(3)));
		assertEquals(segments.get(3),cityMap.getSegmentFromInter(intersections.get(3), intersections.get(4)));
		assertEquals(segments.get(nbIntersections/2),cityMap.getSegmentFromInter(intersections.get(nbIntersections/2), intersections.get(nbIntersections/2+1)));
		assertEquals(segments.get(nbIntersections-2),cityMap.getSegmentFromInter(intersections.get(nbIntersections-2), intersections.get(nbIntersections-1)));
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
	void testGetIntFromIntersectionMap() {
		assertEquals(intersections.get(0).getNumber(), Integer.toString(cityMap.getIntFromIntersectionMap(intersections.get(0))));
		assertEquals(intersections.get(6).getNumber(), Integer.toString(cityMap.getIntFromIntersectionMap(intersections.get(6))));
		assertEquals(intersections.get(nbIntersections/2).getNumber(), Integer.toString(cityMap.getIntFromIntersectionMap(intersections.get(nbIntersections/2))));
		assertEquals(intersections.get(nbIntersections-1).getNumber(), Integer.toString(cityMap.getIntFromIntersectionMap(intersections.get(nbIntersections-1))));
	}

	@Test
	void testGetIntersectionFromIdMap() {
		assertEquals(intersections.get(0), cityMap.getIntersectionFromIdMap(0));
		assertEquals(intersections.get(12), cityMap.getIntersectionFromIdMap(12));
		assertEquals(intersections.get(nbIntersections/2), cityMap.getIntersectionFromIdMap(nbIntersections/2));
		assertEquals(intersections.get(nbIntersections-1), cityMap.getIntersectionFromIdMap(nbIntersections-1));
	}
	
	@Test
	void testGetInstersections() {
		assertEquals(intersections, cityMap.getInstersections());
	}

	@Test
	void testGetSegments() {
		assertEquals(segments, cityMap.getSegments());
	}

}
