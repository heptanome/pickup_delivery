package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Intersection;

class IntersectionTest {
	public Intersection intersection;
	public float longitude, latitude;

	@BeforeEach
	void setUp() throws Exception {
		longitude = 20;
		latitude = 14;
		intersection = new Intersection("abc", latitude, longitude);
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
		assertEquals(intersection.toString(), "id : abc {14.0, 20.0}.");
	}

	@Test
	void testGetLatitude() {
		assertTrue(intersection.getLatitude() == latitude);
	}

	@Test
	void testGetLongitude() {
		assertTrue(intersection.getLongitude() == longitude);
	}

	@Test
	void testGetId() {
		assertEquals(intersection.getId(), "abc");
	}

}
