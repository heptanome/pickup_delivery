package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import model.CityMap;
import model.Tour;
import model.SetOfRequests;

class TourTest implements PropertyChangeListener {
	public static final String MAP_FILE_PATH = "./XML_data/smallMap.xml";
	public static final int NB_INTER = 308;
	public static final String CORRUPTED_MAP_FILE_PATH = "./XML_data/smallCorruptedMap.xml";
	public static final String REQUEST_FILE_PATH = "./XML_data/requestsLarge9.xml";
	public static final int NB_REQUEST = 9;
	public static final String CORRUPTED_REQUEST_FILE_PATH = "./XML_data/requestsSmallCorrupted.xml";
	public static final String INCORRECT_PATH = "./XML_data/xxxx";
	
	public Tour tour;

	@BeforeEach
	void setUp() throws Exception {
		tour = new Tour();
		tour.addPropertyChangeListener(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		tour = null;
		assertNull(tour);
	}

	@Test
	void testTour() {
		tour = new Tour();
	}

	@Test
	void testSetMap() {
		try {
			tour.setMap(MAP_FILE_PATH);
		} catch (Exception e) {
			fail("Should not throw exception");
		}
		
		try {
			tour.setMap(new String());
			fail("Should throw exception IllegalArgumentException");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		
		try {
			tour.setMap(INCORRECT_PATH);
			fail("Should throw exception FileNotFoundException");
		} catch (Exception e) {
			assertTrue(e instanceof FileNotFoundException);
		}
		
		try {
			tour.setMap(CORRUPTED_MAP_FILE_PATH);
			fail("Should throw exception SAXParseException");
		} catch (Exception e) {
			assertTrue(e instanceof SAXParseException);
		}
	}

	@Test
	void testSetRequests() throws Exception {
		try {
			tour.setRequests(REQUEST_FILE_PATH);
		} catch (Exception e) {
			fail("Should not throw exception");
		}
		
		try {
			tour.setRequests(new String());
			fail("Should throw exception IllegalArgumentException");
		} catch (Exception e) {
			System.out.println(e);
			assertTrue(e instanceof IllegalArgumentException);
		}
		
		try {
			tour.setRequests(INCORRECT_PATH);
			fail("Should throw exception FileNotFoundException");
		} catch (Exception e) {
			assertTrue(e instanceof FileNotFoundException);
		}
		
		try {
			tour.setRequests(CORRUPTED_REQUEST_FILE_PATH);
			fail("Should throw exception SAXParseException");
		} catch (Exception e) {
			assertTrue(e instanceof SAXParseException);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		switch (propName) {
		case "updateMap":
			CityMap map = (CityMap)evt.getNewValue();
			assertEquals(map.getInstersections().size(), NB_INTER);
			break;
		case "updateRequests":
			SetOfRequests set = (SetOfRequests)evt.getNewValue();
			assertEquals(set.getRequests().size(), NB_REQUEST);
			break;
		default:
			break;
		}
	}

}
