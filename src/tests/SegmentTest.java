package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Segment;

class SegmentTest {
	Segment segment;
	private String idOrigin, idDestination, name;
	private float length;

	@BeforeEach
	void setUp() throws Exception {
		idOrigin = "abc";
		idDestination = "def";
		name = "NAME";
		length = 42;
		segment = new Segment(idOrigin, idDestination, name, length);
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
		assertEquals(segment.toString(), "Name : NAME, origin : abc, destination : def (length : 42.0).");
	}

	@Test
	void testGetIdOrigin() {
		assertEquals(segment.getNumberOrigin(), idOrigin);
	}

	@Test
	void testGetIdDestination() {
		assertEquals(segment.getNumberDestination(), idDestination);
	}

}
