package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.MapParser;
import model.Map;

class MapParserTest {
	public MapParser mp;
	public Map map;
	
	@BeforeEach
	void setUp() throws Exception {
		mp = new MapParser("./XML_data/smallMap.xml");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMapParser() {
		fail("Not yet implemented");
	}

	@Test
	void testLoadMap() {
		map = mp.loadMap();
		assertTrue(1 == 1);
	}

}
