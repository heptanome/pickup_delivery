package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import org.xml.sax.SAXException;

import model.Intersection;
import model.Tour;
import tsp.CompleteGraph;
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
	protected final AddingPickupAddress apa = new AddingPickupAddress();
	protected final AddingPointPreceedingPickup appp = new AddingPointPreceedingPickup();
	protected final AddingDeliveryAddress ada = new AddingDeliveryAddress();
	protected final AddingPointPreceedingDelivery appd = new AddingPointPreceedingDelivery();
	protected final DeleteRequestState deleteRequestState = new DeleteRequestState();

	public static void main(String[] args) {
		Tour tour = new Tour();
		HomeWindow homeWindow = new HomeWindow("Home Window");
		State state = new HomeState();

		Application app = new Application(homeWindow, tour, state);
	}

	/**
	 * "Connects" the Model with the View and the View with the Controller by
	 * setting up the property listeners
	 * 
	 * @param hw    the View to work with
	 * @param t     the Model to work with
	 * @param state the initial state to start in
	 */
	public Application(HomeWindow hw, Tour t, State state) {
		this.currentState = state;

		this.tour = t;
		this.homeWindow = hw;
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


	 /* Loads a map from a file path
	 * 
	 * @param fp The map's file path
	 */
	public void loadMap(String fp) {
		try {
			currentState.loadMap(fp, this.tour);
			currentState = mapWoRequestsState;
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
	 */
	public void loadRequests(String fp) {
		try {
			currentState.loadRequests(fp, this.tour);
			currentState = displayingTourState;
		} catch (IllegalArgumentException e) {
			System.out.println("requests file path argument is null");
		} catch (IOException e) {
			System.out.println("An IO error occured");
		} catch (SAXException e) {
			System.out.println("Unable to parse the document");
		} catch (Exception e) {
		}
	}

	public void addRequest() throws Exception {
		System.out.println("Ajout d'une requête : ");
		currentState = apa;
		currentState.describeState(homeWindow);
		currentState.setMouseListener(homeWindow);
		//homeWindow.removeAllMouseListeners();
		//homeWindow.addSingleMouseClickOnAnyPointListener();
		
	}

	public void pointClicked(Object selectedPoint) throws Exception {
		currentState.pointClicked((Intersection)selectedPoint, homeWindow, tour, this);
		//currentState = currentState.nextState();
		currentState.describeState(homeWindow);
		currentState.setMouseListener(homeWindow);

	}

	public void deleteRequest() throws Exception {
		System.out.println("Suppression d'une requête");
		
		currentState = deleteRequestState;
		currentState.describeState(homeWindow);
		homeWindow.addSingleMouseClickOnSpecialPointListener();

	}

	public void computeTour() throws Exception {
		currentState.computeTour(tour);
	}

	/**
	 * We define different scenarios in this method, according to the events that
	 * can be brought up tp the controller.
	 * 
	 * "loadMap": triggers the controller into loading a map
	 * 
	 * "loadRequests": triggers the controller into loading a request
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
