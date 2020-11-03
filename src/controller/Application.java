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
	private HomeState homeState = new HomeState();
	private WorkingState workingState = new WorkingState();
	private MapWithoutRequestsState mapWoRequestsState = new MapWithoutRequestsState();
	private AddingPickupAddress apa = new AddingPickupAddress();

	public static void main(String[] args) {
		System.out.println("Bienvenue sur Pickup and Delivery");

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
		currentState.describeState();
		homeWindow.addSingleMouseClickOnMapListener();
		
	}

	public void pointClicked(Object selectedPoint) throws Exception {
		currentState.pointClicked((Intersection)selectedPoint, homeWindow);
		currentState = currentState.nextState();
		currentState.describeState();

		//TODO : replace this with a method setMouseListeners in the States
		if(currentState instanceof WorkingState){
			homeWindow.removeAllMouseListeners();
			homeWindow.addMouseOnMapListener();
		}

	}

	public void deleteRequest() {
		// TODO : problem with static method
		// currentState.deleteRequest();
		System.out.println("Suppression d'une requête");

		// TODO : A implémenter
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
