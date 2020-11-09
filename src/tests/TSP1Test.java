package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
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
import tsp.Graph;
import tsp.TSP1;

class TSP1Test extends TemplateTSPTest {
	private TSP1 tsp = new TSP1();

	private Graph graph;
	private List<Intersection> intersections;
	private List<Segment> segments;
	private CityMap cityMap;
	private List<Request> requests;
	private SetOfRequests sor;

	@BeforeEach
	void setUp() throws Exception {
		intersections = new ArrayList<Intersection>();
		segments = new ArrayList<Segment>();
		intersections.add(new Intersection("0", 0, 0));
		for (int i = 1; i < 7; i++) {
			intersections.add(new Intersection(Integer.toString(i), i, i));
			segments.add(new Segment(intersections.get(i-1), intersections.get(i), Integer.toString(i), (float) 1.4)); 
		}
		this.cityMap = new CityMap(intersections, segments);
		
		requests = new ArrayList<Request>();
		requests.add(new Request(intersections.get(2),intersections.get(6), 2, 3));
		requests.add(new Request(intersections.get(5),intersections.get(4), 6, 3));
		this.sor = new SetOfRequests(intersections.get(0), LocalTime.of(1,2,3), requests);
		
		graph = new CompleteGraph(cityMap, sor);	
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testSearchSolution() {
		System.out.println(graph.toString());
		tsp.searchSolution(-1, graph);
		assertEquals(tsp.getSolution(1),-1);

	}

	@Test
	void testGetSolution() {
		tsp.searchSolution(100, graph);
		assertEquals(tsp.getSolution(-1),-1);
		assertEquals(tsp.getSolution(graph.getNbVertices()+1),-1);
	}

	@Test
	void testGetSolutionCost() {
		tsp.searchSolution(100, graph);
		assertEquals(tsp.getSolutionCost(),graph.getCost(1, 2));
	}

}
