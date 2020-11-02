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
		//tour instance is replaced by a mock
		tourMock = mock(Tour.class);

		//Behavior simulation
		doThrow(new IllegalArgumentException()).when(tourMock).setMap(new String());
		doThrow(new IOException()).when(tourMock).setMap(INCORRECT_PATH);
		doThrow(new SAXException()).when(tourMock).setMap(CORRUPTED_MAP_FILE_PATH);
		doThrow(new IllegalArgumentException()).when(tourMock).setRequests(new String());
		doThrow(new IOException()).when(tourMock).setRequests(INCORRECT_PATH);
		doThrow(new SAXException()).when(tourMock).setRequests(CORRUPTED_REQUEST_FILE_PATH);
		
		//homeState and homeWindow are replaced by a mock
		stateMock = mock(HomeState.class);
		homeWindowMock = mock(HomeWindow.class);
		
		app = new Application(homeWindowMock, tourMock, stateMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoadMap() throws Exception {
		app.loadMap(MAP_FILE_PATH);
		app.loadMap(new String());
		app.loadMap(INCORRECT_PATH);
		app.loadMap(CORRUPTED_MAP_FILE_PATH);
		
		try {
		verify(tourMock).setMap(MAP_FILE_PATH);
		verify(tourMock).setMap(new String());
		verify(tourMock).setMap(INCORRECT_PATH);
		verify(tourMock).setMap(CORRUPTED_MAP_FILE_PATH);
		} catch (Exception e) {}
		verify(stateMock, times(4)).loadMap(anyString(), this.tourMock);
		
	}

	@Test
	void testLoadRequests() throws Exception {
		app.loadRequests(REQUEST_FILE_PATH);
		app.loadRequests(new String());
		app.loadRequests(INCORRECT_PATH);
		app.loadRequests(CORRUPTED_REQUEST_FILE_PATH);
		
		try {
		verify(tourMock).setRequests(REQUEST_FILE_PATH);
		verify(tourMock).setRequests(new String());
		verify(tourMock).setRequests(INCORRECT_PATH);
		verify(tourMock).setRequests(CORRUPTED_REQUEST_FILE_PATH);
		} catch (Exception e) {}
		verify(stateMock, times(4)).loadRequests(anyString(), this.tourMock);
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
