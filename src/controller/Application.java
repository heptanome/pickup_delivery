package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.IOException;
import org.xml.sax.SAXException;

import model.Intersection;
import model.Tour;
import view.HomeWindow;

public class Application implements PropertyChangeListener {
	private HomeWindow homeWindow;
	private Tour tour;
	private State currentState;
	protected final HomeState homeState = new HomeState();
	protected final MapWithoutRequestsState mapWoRequestsState = new MapWithoutRequestsState();
	protected final MapWithRequestsState mapWithRequestsState = new MapWithRequestsState();
	protected final DisplayingTourOnMapState workingState = new DisplayingTourOnMapState();
	protected final AddingPickupAddress apa = new AddingPickupAddress();
	protected final AddingPointPreceedingPickup appp = new AddingPointPreceedingPickup();
	protected final AddingDeliveryAddress ada = new AddingDeliveryAddress();
	protected final AddingPointPreceedingDelivery appd = new AddingPointPreceedingDelivery();
	protected final DeleteRequestState deleteRequestState = new DeleteRequestState();

	public static void main(String[] args) {
		System.out.println("Welcome on Pickup and Delivery");

		Tour tour = new Tour();
		HomeWindow homeWindow = new HomeWindow("Home Window");
		Application app = new Application(homeWindow, tour, new HomeState());
	}

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
		} catch (Exception e) {}
	}

	public void loadRequests(String fp) {
		try {
			currentState.loadRequests(fp, this.tour);
			currentState = workingState;
		} catch (IllegalArgumentException e) {
			System.out.println("requests file path argument is null");
		} catch (IOException e) {
			System.out.println("An IO error occured");
		} catch (SAXException e) {
			System.out.println("Unable to parse the document");
		} catch (Exception e) {}
	}

	public void addRequest() throws Exception {
		System.out.println("Ajout d'une requête : ");
		currentState = apa;
		currentState.describeState(homeWindow);
		homeWindow.addSingleMouseClickOnMapListener();
		
	}

	public void pointClicked(Object selectedPoint) throws Exception {
		currentState.pointClicked((Intersection)selectedPoint, homeWindow, tour);
		currentState = currentState.nextState();
		currentState.describeState(homeWindow);

		//TODO : replace this with a method setMouseListeners in the States
		if(currentState instanceof DisplayingTourOnMapState){

			System.out.println("done");
			homeWindow.removeAllMouseListeners();
			homeWindow.addMouseOnMapListener();
		}

	}

	public void deleteRequest() throws Exception {
		System.out.println("Suppression d'une requête");
		
		currentState = deleteRequestState;
		currentState.describeState(homeWindow);
		homeWindow.addSingleMouseClickOnMapListener();

	}

	public void computeTour() throws Exception {
		currentState.computeTour(tour);
	}

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
			} catch(Exception e) {
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
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
	}
}
