package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import tsp.SeqIter;

class SeqIterTest {
	private Integer[] candidates;
	private int nbCandidates;
	private Collection<Integer> unvisited;
	private int currentVertex;
	private Graph graph;
	private List<Intersection> intersections;
	private List<Segment> segments;
	private CityMap cityMap;
	private List<Request> requests;
	private SetOfRequests sor;
	private SeqIter seqIter;

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
		
		requests = new ArrayList<Request>();
		requests.add(new Request("1", "5", 2, 3));
		requests.add(new Request("4", "3", 6, 3));
		this.sor = new SetOfRequests("0", new Date(1,2,3), requests);
		
		graph = new CompleteGraph(cityMap, sor);
		
		
		unvisited = new HashSet<Integer>();
		unvisited.add(1);
		unvisited.add(2);
		unvisited.add(3);
		
		this.candidates = new Integer[unvisited.size()];
		
		for (Integer s : unvisited){
			if(graph.isDeliveryAddress(s)) {
				int pickup = graph.getPickUpFromDelivery(s);
				if (graph.isArc(currentVertex, s) && !(unvisited.contains(pickup)) )
					candidates[nbCandidates++] = s;
				
			} else {
				if (graph.isArc(currentVertex, s))
					candidates[nbCandidates++] = s;
			}
		}
		this.seqIter = new SeqIter(unvisited, 0, graph);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSeqIter() {
		// TODO
	}

	@Test
	void testHasNext() {
		assertTrue(seqIter.hasNext());
	}

	@Test
	void testNext() {
		assertEquals(seqIter.next(), 3);
		assertEquals(seqIter.next(), 2);
	}

	@Test
	void testRemove() {
		// TODO
	}

}
