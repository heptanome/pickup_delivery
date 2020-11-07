package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.xml.sax.SAXException;

import model.Intersection;
import model.Tour;
import view.HomeWindow;

/**
 * Main class of the controller. It implements the Tour (main class of the
 * Model) and HomeWindow (main class of the View). It is responsible for
 * transmitting the view events to the model.
 */
public class Application implements PropertyChangeListener {
	private HomeWindow homeWindow;
	private Tour tour;
	private State currentState;
	protected final HomeState homeState = new HomeState();
	protected final MapWithoutRequestsState mapWoRequestsState = new MapWithoutRequestsState();
	protected final MapWithRequestsState mapWithRequestsState = new MapWithRequestsState();
	protected final DisplayingTourOnMapState displayingTourState = new DisplayingTourOnMapState();
	protected final AddingPickupAddressState apa = new AddingPickupAddressState();
	protected final AddingPointPreceedingPickupState appp = new AddingPointPreceedingPickupState();
	protected final AddingDeliveryAddressState ada = new AddingDeliveryAddressState();
	protected final AddingPointPreceedingDeliveryState appd = new AddingPointPreceedingDeliveryState();
	protected final DeletingRequestState deleteRequestState = new DeletingRequestState();

	public static void main(String[] args) {
		Tour tour = new Tour();
		HomeWindow homeWindow = new HomeWindow("Home Window");

		new Application(homeWindow, tour);
	}

	/**
	 * "Connects" the Model with the View and the View with the Controller by
	 * setting up the property listeners
	 * 
	 * @param hw    the View to work with
	 * @param t     the Model to work with
	 * @param state the initial state to start in
	 */
	public Application(HomeWindow hw, Tour t) {
		this.tour = t;
		this.homeWindow = hw;
		this.currentState = homeState;
		this.currentState.setButtons(homeWindow);
		// Window listens to Tour events
		this.tour.addPropertyChangeListener(this.homeWindow);
		// Application listens to Window events
		this.homeWindow.addPropertyChangeListener(this);
	}

	/**
	 * Change the current state of the controller
	 * @param state the new current state
	 */
	protected void setCurrentState(State state){
		currentState = state;
	}


	 /**
	  * Loads a map from a file path
	 * 
	 * @param fp The map's file path
	 * @throws IllegalArgumentException if the file path is null
	 * @throws IOException if there is a I/O error
	 * @throws SAXException if the xml file couldn't be parsed correctly
	 * @throws Exception for any other exception
	 */
	public void loadMap(String fp) throws IllegalArgumentException, IOException, SAXException, Exception{
		try {
			currentState.loadMap(fp, this.tour);
			currentState = mapWoRequestsState;
			currentState.setButtons(homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("map file path argument is null");
		} catch (IOException e) {
			System.out.println("An IO error occured");
		} catch (SAXException e) {
			System.out.println("Unable to parse the document");
		} catch (Exception e) {
		}
	}

	/**
	 * Loads a request from a file path
	 * 
	 * @param fp The set of requests' file path
	 * @throws IllegalArgumentException if the file path is null
	 * @throws IOException if there is a I/O error
	 * @throws SAXException if the xml file couldn't be parsed correctly
	 * @throws Exception for any other exception
	 */
	public void loadRequests(String fp) throws IllegalArgumentException, IOException, SAXException, Exception{
		try {
			currentState.loadRequests(fp, this.tour);
			currentState = mapWithRequestsState;
			currentState.setButtons(homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("requests file path argument is null");
		} catch (IOException e) {
			System.out.println("An IO error occured");
		} catch (SAXException e) {
			System.out.println("Unable to parse the document");
		} catch (Exception e) {
		}
	}


	/**
	 * Starts the creation of a new request to add to the tour
	 * Pre condition : a set of requests has to be loaded
	 * 
	 */
	public void addRequest() {
		System.out.println("Ajout d'une requête : ");
		currentState = apa;
		currentState.setButtons(homeWindow);
		currentState.describeState(homeWindow);
		currentState.setMouseListener(homeWindow);
	}

	/**
	 * Method called when a point (an intersction) is selected on the map in one of
	 * the process of adding a request, or when deleting a request.
	 * 
	 * @param selectedPoint : the point that has been clicked
	 */
	public void pointClicked(Object selectedPoint)  {
			currentState.pointClicked((Intersection)selectedPoint, homeWindow, tour, this);
			currentState.setButtons(homeWindow);
			currentState.describeState(homeWindow);
			currentState.setMouseListener(homeWindow);
	}

	/**
	 * Starts the process of deleting a request from the tour
	 * Pre condition : a set of requests has to be loaded
	 * //TODO : exception 
	 * @throws Exception 
	 */
	public void deleteRequest() throws Exception {
		System.out.println("Suppression d'une requête");
		
		currentState = deleteRequestState;
		currentState.setButtons(homeWindow);
		currentState.describeState(homeWindow);
		homeWindow.addSingleMouseClickOnSpecialPointListener();

	}

	/**
	 * Starts the process of computing a tour
	 * Pre condition : a set of requests has to be loaded
	 * 
	 */
	public void computeTour() throws Exception {
		currentState.computeTour(tour);
		setCurrentState(displayingTourState);
		currentState.setButtons(homeWindow);
	}

	/**
	 * We define different scenarios in this method, according to the events that
	 * can be brought up tp the controller.
	 * 
	 * "loadMap": triggers the controller into loading a map
	 * 
	 * "loadRequests": triggers the controller into loading a request
	 * 
	 * "addRequest" : triggers the controller into adding  a request
	 * 
	 * "deleteRequest" : triggers the controller into its response to a clic during the adding and deleting processes
	 * 
	 * "deleteRequest" : triggers the controller into deleting  a request
	 * 
	 * "computeTour": triggers the controller into computing the tour
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		
		
		switch (propName) {
		case "loadMap":
			try {
				this.loadMap((String) evt.getNewValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "loadRequests":
			try {
				this.loadRequests((String) evt.getNewValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "addRequest":
			try {
				this.addRequest();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case "pointClicked":
			try {
				this.pointClicked(evt.getNewValue());
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case "deleteRequest":
			try {
				this.deleteRequest();
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case "computeTour":
			try {
				this.computeTour();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
	}
}
