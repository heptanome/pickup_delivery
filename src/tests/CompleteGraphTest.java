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

class CompleteGraphTest extends GraphTest {
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
			segments.add(new Segment(intersections.get(i-1), intersections.get(i), Integer.toString(i), (float) 1.4)); 
		}
		this.cityMap = new CityMap(intersections, segments);
		this.nbVertices = cityMap.getNbVertices();
		
		requests = new ArrayList<Request>();
		requests.add(new Request(intersections.get(2),intersections.get(6), 2, 3));
		requests.add(new Request(intersections.get(5),intersections.get(4), 6, 3));
		this.sor = new SetOfRequests(intersections.get(0), new Date(1,2,3), requests);
		
		completeGraph = new CompleteGraph(cityMap, sor);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Override
	void testGetNbVertices() {
		assertEquals(completeGraph.getNbVertices(), requests.size()*2 + 1);
	}
	
	/*
	 TODO : Se renseigner sur l'algorithme de calcul de coûts. Est-ce qu'on peut le tester sans le réecrire ici ? Comment ?
	@Test
	@Override
	void testGetCost() {
		assertEquals(-1, completeGraph.getCost(-1,3));
		for (int i = 1; i < completeGraph.getNbVertices(); i++) {
			assertEquals(0, completeGraph.getCost(i,i));
		}
		assertTrue(1.4 - completeGraph.getCost(0,1) < 0.000001 && 1.4 - completeGraph.getCost(0,1) > - 0.000001 );
	}
	*/
	
	@Test
	@Override
	void testIsArc() {
		for (int i = 1; i < completeGraph.getNbVertices(); i++) {
			assertTrue(completeGraph.isArc(i-1, i));
			assertFalse(completeGraph.isArc(i, i));
		}
	}
	
	@Test
	@Override
	void testIsDeliveryAddress() {
		assertTrue(completeGraph.isDeliveryAddress(1));
	}
	
	@Test
	@Override
	void testGetCost() {
		assertEquals(completeGraph.getCost(1, 1), 0);
		assertEquals(completeGraph.getCost(1, 2), completeGraph.getCost(2, 3));
		assertEquals(completeGraph.getCost(1, 10), -1);
	}
	
	@Test
	@Override
	void testMinArcCost() {
		assertEquals(completeGraph.minArcCost(), 0);
	}
	
	@Test
	void testGetPickUpFromDelivery() {
		/* TODO : Demander et commenter quels sont les paramètres d'entrée et de sortie de la méthode testée
		for (int i=0; i< completeGraph.getNbVertices(); i++) {
			if (completeGraph.isDeliveryAddress(i)) {
				assertTrue(completeGraph.getPickUpFromDelivery(i) == 2 || completeGraph.getPickUpFromDelivery(i) == 4);
			}
		}*/
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

















