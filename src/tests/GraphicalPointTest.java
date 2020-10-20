package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;
import view.GraphicalPoint;

class GraphicalPointTest {
	private GraphicalPoint graphicalPoint;
	private Intersection intersection, intersection2;
	private int xPixel;
	private int yPixel;
	private String intersectionId;
	private Color color;

	float minLat, minLongi, maxLat, maxLongi;
	@BeforeEach
	void setUp() throws Exception {
		minLat = 1;
		maxLat = 20;
		minLongi = 3;
		maxLongi = 18;
		intersection = new Intersection("abc", 3, 11);
		graphicalPoint = new GraphicalPoint(intersection, minLat, minLongi, maxLat, maxLongi);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGraphicalPoint() {
		intersection2 = new Intersection("a", 2, 4);
		
	}

	@Test
	void testGetXPixel() {
		assertEquals(graphicalPoint.getXPixel(), 84);
	}

	@Test
	void testGetYPixel() {
		assertEquals(graphicalPoint.getYPixel(), 427);
	}

	@Test
	void testGetIntersectionId() {
		assertEquals(graphicalPoint.getIntersectionId(), "abc");
	}

	@Test
	void testGetColor() {
		assertEquals(graphicalPoint.getColor(), color.white);
	}

	@Test
	void testSetColor() {
		assertEquals(graphicalPoint.getColor(), color.white);
		graphicalPoint.setColor(color.blue);
		assertEquals(graphicalPoint.getColor(), color.blue);
	}

	@Test
	void testGetSize() {
		assertEquals(graphicalPoint.getSize(), 8);
	}

	@Test
	void testSetSize() {
		assertEquals(graphicalPoint.getSize(), 8);
		graphicalPoint.setSize(12);
		assertEquals(graphicalPoint.getSize(), 12);
	}

}
