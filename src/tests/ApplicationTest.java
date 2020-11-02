package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import controller.Application;
import model.Tour;
import view.HomeWindow;
import static org.mockito.Mockito.*;

import java.io.IOException;

/**
 * @author Arthur Batel
 *
 */
class ApplicationTest{
	public static final String MAP_FILE_PATH = "./XML_data/mediumMap.xml";
	public static final String CORRUPTED_MAP_FILE_PATH = "./XML_data/smallCorruptedMap.xml";
	public static final String REQUEST_FILE_PATH = "./XML_data/requestsMedium5.xml";
	public static final String CORRUPTED_REQUEST_FILE_PATH = "./XML_data/requestsSmallCorrupted.xml";
	public static final String INCORRECT_PATH = "./XML_data/xxxx";
	private Application app;
	private HomeWindow homeWindow;
	private Tour tourMock;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}
	
	@BeforeEach
	void setUp() throws Exception {
		//tour instance is replaced by a mock
		tourMock = mock(Tour.class);

		//Behavior simulation
		doThrow(new IllegalArgumentException()).when(tourMock).setMap("");
		doThrow(new IOException()).when(tourMock).setMap(INCORRECT_PATH);
		doThrow(new SAXException()).when(tourMock).setMap(CORRUPTED_MAP_FILE_PATH);
		doThrow(new IllegalArgumentException()).when(tourMock).setRequests("");
		doThrow(new IOException()).when(tourMock).setRequests(INCORRECT_PATH);
		doThrow(new SAXException()).when(tourMock).setRequests(CORRUPTED_MAP_FILE_PATH);

		homeWindow = new HomeWindow("home window");
		app = new Application(homeWindow, tourMock);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoadMap() {
		app.loadMap(MAP_FILE_PATH);
		app.loadMap("");
		app.loadMap(INCORRECT_PATH);
		app.loadMap(CORRUPTED_MAP_FILE_PATH);
		
		try {
		verify(tourMock).setMap(MAP_FILE_PATH);
		verify(tourMock).setMap("");
		verify(tourMock).setMap(INCORRECT_PATH);
		verify(tourMock).setMap(CORRUPTED_MAP_FILE_PATH);
		} catch (Exception e) {}
		
	}

	@Test
	void testLoadRequests() {
		app.loadRequests(REQUEST_FILE_PATH);
		app.loadRequests("");
		app.loadRequests(INCORRECT_PATH);
		app.loadRequests(CORRUPTED_REQUEST_FILE_PATH);
		
		try {
		verify(tourMock).setRequests(REQUEST_FILE_PATH);
		verify(tourMock).setRequests("");
		verify(tourMock).setRequests(INCORRECT_PATH);
		verify(tourMock).setRequests(CORRUPTED_REQUEST_FILE_PATH);
		} catch (Exception e) {}
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
	void testComputeTour() {
		app.computeTour();
		verify(tourMock).computeTour();
	}

	@Test
	void testPropertyChange() {
		//TODO
	}

}
