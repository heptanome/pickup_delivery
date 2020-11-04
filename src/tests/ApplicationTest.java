package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import controller.Application;
import controller.HomeState;
import controller.State;
import model.Tour;
import view.HomeWindow;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;

/**
 * @author Arthur Batel
 *
 */
class ApplicationTest {
	public static final String MAP_FILE_PATH = "./XML_data/mediumMap.xml";
	public static final String CORRUPTED_MAP_FILE_PATH = "./XML_data/smallCorruptedMap.xml";
	public static final String REQUEST_FILE_PATH = "./XML_data/requestsMedium5.xml";
	public static final String CORRUPTED_REQUEST_FILE_PATH = "./XML_data/requestsSmallCorrupted.xml";
	public static final String INCORRECT_PATH = "./XML_data/xxxx";
	private Application app;
	private HomeWindow homeWindowMock;
	private Tour tourMock;
	private State stateMock;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}
	
	@BeforeEach
	void setUp() throws Exception {
		//tour, homeState and homeWindow  instance is replaced by a mock
		tourMock = mock(Tour.class);
		stateMock = mock(HomeState.class);
		homeWindowMock = mock(HomeWindow.class);
		
		//Behavior simulation
		doThrow(new IllegalArgumentException()).when(stateMock).loadMap(new String(),tourMock);
		doThrow(new IOException()).when(stateMock).loadMap(INCORRECT_PATH,tourMock);
		doThrow(new SAXException()).when(stateMock).loadMap(CORRUPTED_MAP_FILE_PATH,tourMock);
		doThrow(new IllegalArgumentException()).when(stateMock).loadRequests(new String(),tourMock);
		doThrow(new IOException()).when(stateMock).loadRequests(INCORRECT_PATH,tourMock);
		doThrow(new SAXException()).when(stateMock).loadRequests(CORRUPTED_REQUEST_FILE_PATH,tourMock);
		
		app = new Application(homeWindowMock, tourMock, stateMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//TODO : Il faut tester stateMock et non pas tourMock
	@Test
	void testLoadMap() throws Exception {
		app.loadMap(new String());
		app.loadMap(INCORRECT_PATH);
		app.loadMap(CORRUPTED_MAP_FILE_PATH);
		app.loadMap(MAP_FILE_PATH);
		
		try {
		verify(stateMock).loadMap(MAP_FILE_PATH,tourMock);
		verify(stateMock).loadMap(new String(),tourMock);
		verify(stateMock).loadMap(INCORRECT_PATH,tourMock);
		verify(stateMock).loadMap(CORRUPTED_MAP_FILE_PATH,tourMock);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not throw exception");
		}
		
	}

	@Test
	void testLoadRequests() throws Exception {
		app.loadRequests(new String());
		app.loadRequests(INCORRECT_PATH);
		app.loadRequests(CORRUPTED_REQUEST_FILE_PATH);
		app.loadRequests(REQUEST_FILE_PATH);
		
		try {
		verify(stateMock).loadRequests(REQUEST_FILE_PATH, tourMock);
		verify(stateMock).loadRequests(new String(),tourMock);
		verify(stateMock).loadRequests(INCORRECT_PATH,tourMock);
		verify(stateMock).loadRequests(CORRUPTED_REQUEST_FILE_PATH,tourMock);
		} catch (Exception e) {
			fail("Should not throw exception");
		}
	}

	@Test
	void testAddRequest() {
		//TODO
	}

	@Test
	void testDeleteRequest() {
		//TODO
	}

	@Test
	void testComputeTour() throws Exception {
		app.computeTour();
		verify(stateMock).computeTour(this.tourMock);
	}
	
}
