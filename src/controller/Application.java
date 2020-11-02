package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.IOException;
import org.xml.sax.SAXException;

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
		System.out.println("ajout d'une requête : ");
		currentState = apa;
		homeWindow.addSingleMouseClickOnMapListener();
		/*
		try {
			currentState.pointClicked("apa", homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
		
		currentState = appp;
		try {
			currentState.pointClicked("appp", homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
		
		currentState = ada;
		try {
		currentState.pointClicked("apd", homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
		currentState = appd;
		try {
			currentState.pointClicked("appd", homeWindow);
		} catch (IllegalArgumentException e) {
			System.out.println("error");
		}
		currentState = workingState;*/

	}

	public void pointClicked(String pointId) throws Exception {
		currentState.pointClicked(pointId, homeWindow);
		currentState = currentState.nextState();
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
				// TODO Auto-generated catch block
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
		case "computeTour":
			try {
				this.computeTour();
			} catch(Exception e) {
				e.printStackTrace();
			}
		case "addRequest":
			try {
				this.addRequest();
			} catch(Exception e) {
				e.printStackTrace();
			}
		case "pointClicked":
			try {
				this.pointClicked((String) evt.getNewValue());
			} catch(Exception e) {
				e.printStackTrace();
			}
		default:
			break;
		}
	}
}
