package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.MapParser;
import model.CityMap;

class MapParserTest {
	public static final String MAP_FILE = "./XML_data/smallMap.xml";
	public static final int NB_SEGMENTS = 616;
	public static final int NB_INTER = 308;
	public MapParser mp;
	public CityMap map;
	
	@BeforeEach
	void setUp() throws Exception {
		mp = new MapParser(MAP_FILE);
	}

	@AfterEach
	void tearDown() throws Exception {
		mp = null;
		assertNull(mp);
	}

	@Test
	void testMapParser() {
		mp = new MapParser(MAP_FILE);
	}

	@Test
	void testLoadMap() {
		// the loaded map (smallMap) has exactly 308 intersections and 616 segments
		map = mp.loadMap();
		assertEquals(map.getSegments().size(), NB_SEGMENTS);
		assertEquals(map.getInstersections().size(), NB_INTER);
	}

}
