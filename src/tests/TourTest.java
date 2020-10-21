package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CityMap;
import model.Tour;
import model.SetOfRequests;

class TourTest implements PropertyChangeListener {
	public static final String MAP_FILE = "./XML_data/smallMap.xml";
	public static final int NB_INTER = 308;
	public static final String REQ_FILE = "./XML_data/requestsLarge9.xml";
	public static final int NB_REQUEST = 9;
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
		tour.setMap(MAP_FILE);
	}

	@Test
	void testSetRequests() {
		tour.setRequests(REQ_FILE);
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
