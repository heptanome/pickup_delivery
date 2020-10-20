package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.RequestParser;
import model.SetOfRequests;

class RequestParserTest {
	
	public RequestParser requestParser;
	public static final String FILE_PATH = "./XML_data/requestsLarge9.xml";
	public static final int NB_REQUEST = 9;
	public static final String ID_DEPOT = "25327124";

	@BeforeEach
	void setUp() throws Exception {
		requestParser = new RequestParser(FILE_PATH);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoadRequests() {
		SetOfRequests setOfRequest = requestParser.loadRequests();
		assertEquals(setOfRequest.getDepot(),ID_DEPOT);
		assertEquals(setOfRequest.getRequests().size(),NB_REQUEST);
	}

}
