package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
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

class CompleteGraphTest {
	CompleteGraph completeGraph;
	
	private List<Intersection> intersections;
	private List<Segment> segments;
	
	private List<Request> requests;
	private SetOfRequests sor;
	
	private CityMap cityMap;

	@BeforeEach
	void setUp() throws Exception {
		intersections = new ArrayList<Intersection>();
		segments = new ArrayList<Segment>();
		intersections.add(new Intersection("0", 0, 0));
		for (int i = 1; i < 10; i++) {
			intersections.add(new Intersection(Integer.toString(i), i, i));
			segments.add(new Segment(intersections.get(i-1), intersections.get(i), Integer.toString(i), (float) 1.4)); 
		}
		this.cityMap = new CityMap(intersections, segments);
		cityMap.getNbVertices();
		
		requests = new ArrayList<Request>();
		requests.add(new Request(intersections.get(2),intersections.get(6), 2, 3));
		requests.add(new Request(intersections.get(5),intersections.get(4), 6, 3));
		this.sor = new SetOfRequests(intersections.get(0), LocalTime.of(1,2,3), requests);
		
		completeGraph = new CompleteGraph(cityMap, intersections);
		completeGraph = new CompleteGraph(cityMap, sor);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetNbVertices() {
		//assertEquals(intersections.size(), completeGraph.getNbVertices()); nb d'intersections des requêtes
	}
	
	@Test
	void testIsArc() {
		for (int i = 1; i < completeGraph.getNbVertices(); i++) {
			assertTrue(completeGraph.isArc(i-1, i));
			assertFalse(completeGraph.isArc(i, i));
		}
	}
	
	@Test
	void testIsDeliveryAddress() throws Exception {
		assertTrue(completeGraph.isDeliveryAddress(1));
	}
	
	@Test
	void testGetCost() {
		assertEquals(completeGraph.getCost(1, 1), 0);
		assertEquals(completeGraph.getCost(1, 2), completeGraph.getCost(2, 3));
		assertEquals(completeGraph.getCost(1, 10), -1);
	}
	
	@Test
	void testMinArcCost() {
		assertEquals(completeGraph.minArcCost(), 0);
	}
	
	@Test
	void testGetPickUpFromDelivery() throws Exception {
		assertEquals(completeGraph.getPickUpFromDelivery(1).get(0), Integer.parseInt(sor.getRequestNodes().get(1).getNumber()));
	}

	@Test
	void testToString() {
		System.out.println("---------------------");
		System.out.println(completeGraph.toString());
		System.out.println("---------------------");

	}

	@Test
	void testGetNodeNames() {
		assertTrue(completeGraph.getNodeNames() instanceof Map<?,?>);
	}
	
}

















