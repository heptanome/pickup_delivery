package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import model.CityMap;
import model.Intersection;
import model.RequestParser;
import model.SetOfRequests;

import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class RequestParserTest {
	public RequestParser requestParser;
	public static final String MAP_FILE_PATH = "./XML_data/mediumMap.xml";
	public static final String REQUEST_FILE_PATH = "./XML_data/requestsLarge9.xml";
	public static final int NB_REQUEST = 9;
	public static final String ID_DEPOT = "25327124";
	public static final String[] ID_INTERSECTIONS = {"239603465","25319255","984553611","1678996781","1301805013","59654812","130144280","26035105","1362204817","26464256","1370403192","1368674802","26084216","25310896","25316399","25499154","25624158","843129906"};
	public static final String CORRUPTED_REQUEST_FILE_PATH = "./XML_data/requestsSmallCorrupted.xml";
	public static final String INCORRECT_PATH = "./XML_data/xxxx";

	public CityMap mapMock; 
	public Intersection depot;

	@BeforeEach
	void setUp() {
		depot = new Intersection(ID_DEPOT,0,1);
		ArrayList<Intersection> intersections = new ArrayList<Intersection>();
		for (int i = 0; i < ID_INTERSECTIONS.length; i++) {
			intersections.add(new Intersection(ID_INTERSECTIONS[i], i, i));
		}
		intersections.add(depot);
		
		//CityMap instance is replace by a mock
		mapMock = mock(CityMap.class);
		when(mapMock.getInstersections()).thenReturn(intersections);
	
		try {
			requestParser = new RequestParser(REQUEST_FILE_PATH, mapMock);
		} catch (Exception e) {
			fail("Should not throw exception");
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		requestParser = null;
		assertNull(requestParser);
	}
	
	@Test
	void testRequestParser() {
		//CityMap instance is replace by a mock
		mapMock = mock(CityMap.class);
		
		try {
			requestParser = new RequestParser(new String(),mapMock);
			fail("Should throw exception IllegalArgumentException");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
		
		try {
			requestParser = new RequestParser(INCORRECT_PATH,mapMock);
			fail("Should throw exception FileNotFoundException");
		} catch (Exception e) {
			assertTrue(e instanceof FileNotFoundException);
		}
		
		try {
			requestParser = new RequestParser(CORRUPTED_REQUEST_FILE_PATH,mapMock);
			fail("Should throw exception SAXParseException");
		} catch (Exception e) {
			assertTrue(e instanceof SAXParseException);
		}
	}

	@Test
	void testLoadRequests() {
		SetOfRequests setOfRequest = requestParser.loadRequests();
		assertEquals(depot,setOfRequest.getDepot());
		assertEquals(NB_REQUEST,setOfRequest.getRequests().size());
	}

}
