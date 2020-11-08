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
	private Collection<Integer> unvisited;
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
		requests.add(new Request(intersections.get(2),intersections.get(6), 2, 3));
		requests.add(new Request(intersections.get(5),intersections.get(4), 6, 3));
		this.sor = new SetOfRequests(intersections.get(0), new Date(1,2,3), requests);
		
		graph = new CompleteGraph(cityMap, sor);
		
		unvisited = new HashSet<Integer>();
		unvisited.add(1);
		unvisited.add(2);
		unvisited.add(3);
		
		this.seqIter = new SeqIter(unvisited, 0, graph);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSeqIter() {
		assertTrue(seqIter != null);
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
		seqIter.remove();
	}

}
