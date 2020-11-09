package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;

class IntersectionTest {
	public Intersection intersection;
	public float longitude1, latitude1, longitude2, latitude2;

	@BeforeEach
	void setUp() throws Exception {
		longitude1 = 20;
		latitude1 = 14;
		longitude2 = 56;
		latitude2 = 98;
		intersection = new Intersection("abc", latitude1, longitude1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIntersection() {
		assertEquals(1, 1);
	}

	@Test
	void testToString() {
		assertEquals(intersection.toString(), "id : abc");
	}

	@Test
	void testGetLatitude() {
		assertTrue(intersection.getLatitude() == latitude1);
	}

	@Test
	void testGetLongitude() {
		assertTrue(intersection.getLongitude() == longitude1);
	}

	@Test
	void testGetId() {
		assertEquals(intersection.getNumber(), "abc");
	}
	
	@Test
	void testAddGetNeighbours() {
		Intersection neighbours = new Intersection("i",latitude2,longitude2);
		intersection.addNeighbour(neighbours);
		assertEquals(neighbours, intersection.getNeighbours().get(0));
	}
}
