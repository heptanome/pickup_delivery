package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CityMap;
import model.MapParser;

class MapTest {
	public static final String MAP_FILE = "./XML_data/smallMap.xml";
	public static final int NB_SEGMENTS = 616;
	public static final int NB_INTER = 308;
	public MapParser mp;
	public CityMap map;

	@BeforeEach
	void setUp() throws Exception {
		mp = new MapParser(MAP_FILE);
		map = mp.loadMap();
	}

	@AfterEach
	void tearDown() throws Exception {
		map = null;
		assertNull(map);
	}

	@Test
	void testMap() {
		mp = new MapParser(MAP_FILE);
		map = mp.loadMap();
	}

	@Test
	void testGetInstersections() {
		int nb_inter = map.getInstersections().size();
		assertEquals(nb_inter, NB_INTER);
	}

	@Test
	void testGetSegments() {
		int nb_segments = map.getSegments().size();
		assertEquals(nb_segments, NB_SEGMENTS);
	}

}
