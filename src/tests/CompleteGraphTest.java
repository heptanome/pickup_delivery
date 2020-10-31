package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	private int nbVertices;

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
		this.cityMap = new CityMap(intersections, segments);
		this.nbVertices = cityMap.getNbVertices();
		
		requests = new ArrayList<Request>();
		requests.add(new Request("1", "5", 2, 3));
		requests.add(new Request("4", "3", 6, 3));
		this.sor = new SetOfRequests("0", new Date(1,2,3), requests);
		
		completeGraph = new CompleteGraph(cityMap, sor);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCompleteGraph() {
		// TODO
		
	}
	
	@Test
	void testGetNbVertices() {
		assertEquals(completeGraph.getNbVertices(), requests.size()*2 + 1);
	}
	
	@Test
	void testGetCost() {
		assertEquals(-1, completeGraph.getCost(-1,3));
		for (int i = 1; i < completeGraph.getNbVertices(); i++) {
			assertEquals(0, completeGraph.getCost(i,i));
		}
		assertTrue(1.4 - completeGraph.getCost(0,1) < 0.000001 && 1.4 - completeGraph.getCost(0,1) > - 0.000001 );
	}

	@Test
	void testIsArc() {
		for (int i = 1; i < completeGraph.getNbVertices(); i++) {
			assertTrue(completeGraph.isArc(i-1, i));
			assertFalse(completeGraph.isArc(i, i));
		}
	}
	
	@Test
	void testIsDeliveryAddress() {
		assertTrue(completeGraph.isDeliveryAddress(1));
	}
	
	@Test
	void testGetPickUpFromDelivery() {
		for (int i=0; i<= completeGraph.getNbVertices(); i++) {
			if (completeGraph.isDeliveryAddress(i)) {
				assertTrue(completeGraph.getPickUpFromDelivery(i) == 3 || completeGraph.getPickUpFromDelivery(i) == 5);
			}
		}
	}

	@Test
	void testToString() {
		System.out.println("---------------------");
		System.out.println(completeGraph.toString());
		System.out.println("---------------------");

	}

	@Test
	void testGetNodeNames() {
		int j = 0;
		String testString = "{0=0";
		for (int i = 1; i <= requests.size(); i++) {
			j += 1;
			testString = testString + ", " + j + "=" + requests.get(i-1).getDeliveryAddress();
			j = i+1;
			testString = testString + ", " + j + "=" + requests.get(i-1).getPickupAddress();
		}
		testString += "}";
		// TODO
		//assertEquals(testString, completeGraph.getNodeNames());
	}

	@Test
	void testGetPrecedenceOfANode() {
		for (int i = 0; i < completeGraph.getNbVertices(); i++) {
			System.out.println(completeGraph.getPrecedenceOfANode(i));
		}
		// TODO
	}
	
}

















