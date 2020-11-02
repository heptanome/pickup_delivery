package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import model.MapParser;
import model.CityMap;

class MapParserTest {
	public static final String MAP_FILE_PATH = "./XML_data/smallMap.xml";
	public static final String CORRUPTED_MAP_FILE_PATH = "./XML_data/smallCorruptedMap.xml";
	public static final String INCORRECT_PATH = "./XML_data/xxxx";
	public static final int NB_SEGMENTS = 616;
	public static final int NB_INTER = 308;
	public MapParser mp;
	public CityMap map;
	
	@BeforeEach
	void setUp() {
		try {
			mp = new MapParser(MAP_FILE_PATH);
		} catch (Exception e) {
			fail("Should not throw exception");
			e.printStackTrace();
		}
		
		try {
			mp = new MapParser(new String());
			fail("Should throw exception IllegalArgumentException");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		
		try {
			mp = new MapParser(INCORRECT_PATH);
			fail("Should throw exception FileNotFoundException");
		} catch (Exception e) {
			assertTrue(e instanceof FileNotFoundException);
		}
		
		try {
			mp = new MapParser(CORRUPTED_MAP_FILE_PATH);
			fail("Should throw exception SAXParseException");
		} catch (Exception e) {
			assertTrue(e instanceof SAXParseException);
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		mp = null;
		assertNull(mp);
	}

	@Test
	void testLoadMap() {
		// the loaded map (smallMap) has exactly 308 intersections and 616 segments
		map = mp.loadMap();
		assertEquals(map.getSegments().size(), NB_SEGMENTS);
		assertEquals(map.getInstersections().size(), NB_INTER);
	}

}
