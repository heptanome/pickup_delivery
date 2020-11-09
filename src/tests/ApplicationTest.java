package tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.Application;
import controller.HomeState;
import controller.State;
import model.Intersection;
import model.Tour;
import view.HomeWindow;

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
		// tour, homeState and homeWindow instance is replaced by a mock
		tourMock = mock(Tour.class);
		stateMock = mock(HomeState.class);
		homeWindowMock = mock(HomeWindow.class);

		// Behavior simulation
		app = new Application(homeWindowMock, tourMock, stateMock);

	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testApplication() {		
		verify(stateMock).initiateState(app, homeWindowMock);
		verify(tourMock).addPropertyChangeListener(homeWindowMock);
		verify(homeWindowMock).addPropertyChangeListener(app);
	}
	
	@Test
	void testLoadMap() {
		app.loadMap(new String());
		app.loadMap(INCORRECT_PATH);
		app.loadMap(CORRUPTED_MAP_FILE_PATH);
		app.loadMap(MAP_FILE_PATH);
		app.loadMap(REQUEST_FILE_PATH);

		verify(stateMock).loadMap(app, homeWindowMock, MAP_FILE_PATH, tourMock);
		verify(stateMock).loadMap(app, homeWindowMock, new String(), tourMock);
		verify(stateMock).loadMap(app, homeWindowMock, INCORRECT_PATH, tourMock);
		verify(stateMock).loadMap(app, homeWindowMock, CORRUPTED_MAP_FILE_PATH, tourMock);
		verify(stateMock).loadMap(app, homeWindowMock, REQUEST_FILE_PATH, tourMock);
	}

	@Test
	void testLoadRequests() {
		app.loadRequests(new String());
		app.loadRequests(INCORRECT_PATH);
		app.loadRequests(CORRUPTED_REQUEST_FILE_PATH);
		app.loadRequests(REQUEST_FILE_PATH);
		app.loadRequests(MAP_FILE_PATH);

		verify(stateMock).loadRequests(app, homeWindowMock, REQUEST_FILE_PATH, tourMock);
		verify(stateMock).loadRequests(app, homeWindowMock, new String(), tourMock);
		verify(stateMock).loadRequests(app, homeWindowMock, INCORRECT_PATH, tourMock);
		verify(stateMock).loadRequests(app, homeWindowMock, CORRUPTED_REQUEST_FILE_PATH, tourMock);
		verify(stateMock).loadRequests(app, homeWindowMock, MAP_FILE_PATH, tourMock);
	}

	@Test
	void testAddRequest() {
		app.addRequest();
		verify(stateMock).addRequests(app,homeWindowMock);
	}
	
	@Test
	void testPointClicked() {
		Intersection i = mock(Intersection.class);
		app.pointClicked(i);
		verify(stateMock).pointClicked(i,homeWindowMock,tourMock, app);
	}

	@Test
	void testDeleteRequest() {
		app.deleteRequest();
		verify(stateMock).deleteRequests(app,homeWindowMock);
	}

	@Test
	void testComputeTour() {
		app.computeTour();
		verify(stateMock).computeTour(app, homeWindowMock, tourMock);
	}
	
	@Test
	void testDisplayHelp() {
		app.displayHelp();
		verify(stateMock).describeState(homeWindowMock);
	}
	
	@Test
	void testCancel() {
		app.cancel();
		verify(stateMock).cancel(app, homeWindowMock);
	}

}
