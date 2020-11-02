package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;
import model.Segment;

class SegmentTest {
	Segment segment;
	Intersection intersectionOrigin, intersectionDestination;
	private String name;
	private float length;

	@BeforeEach
	void setUp() throws Exception {
		intersectionOrigin = new Intersection("25303832",(float) 45.749344, (float) 4.876714);
		intersectionDestination = new Intersection("25468070", (float) 45.76204, (float) 4.862937);
		name = "avenue Albert Einstein";
		length = 42;
		segment = new Segment(intersectionOrigin,intersectionDestination,name,length);
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSegment() {
		assertTrue(true);
	}

	@Test
	void testToString() {
		assertEquals(segment.toString(),"Name : " + name + ", origin : " + intersectionOrigin.getNumber() + ", destination : " + intersectionDestination.getNumber() + " (length : " + length
				+ ").");
	}

	@Test
	void testGetIdOrigin() {
		assertEquals(segment.getNumberOrigin(), intersectionOrigin.getNumber());
	}

	@Test
	void testGetIdDestination() {
		assertEquals(segment.getNumberDestination(), intersectionDestination.getNumber());
	}

}
