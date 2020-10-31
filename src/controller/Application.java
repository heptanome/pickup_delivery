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

	public static void main(String[] args) {
		System.out.println("Bienvenue sur Pickup and Delivery");

		Tour tour = new Tour();
		HomeWindow homeWindow = new HomeWindow("home window");
		Application app = new Application(homeWindow, tour);
	}

	public Application(HomeWindow hw, Tour t) {
		this.tour = t;
		this.homeWindow = hw;
		// Window listens to Tour events
		this.tour.addPropertyChangeListener(this.homeWindow);
		// Application listens to Window events
		this.homeWindow.addPropertyChangeListener(this);
	}

	public void loadMap(String fp) {
		System.out.println("Chargement de la Map localisée par le chemin : " + fp);
		try {
			this.tour.setMap(fp);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the file couldn't be read
		} catch (SAXException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the file couldn't be parse
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the filepath is empty
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadRequests(String fp) {
		System.out.println("Chargement de la requête localisée par le chemin : " + fp);
		try {
			this.tour.setRequests(fp);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the file couldn't be read
		} catch (SAXException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the file couldn't be parse
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			//TODO implement a PopUp window indicating that the filepath is empty
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addRequest() {
		System.out.println("ajout d'une requête : ");

		// TODO : A implémenter
	}

	public static void deleteRequest() {
		System.out.println("Suppression d'une requête");

		// TODO : A implémenter
	}

	public void computeTour() {
		System.out.println("Calcul d'un chemin");
		this.tour.computeTour(); // <-- renvoie une liste de segment
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		switch (propName) {
		case "loadMap":
			this.loadMap((String) evt.getNewValue());
			break;
		case "loadRequests":
			this.loadRequests((String) evt.getNewValue());
			break;
		case "computeTour":
			this.computeTour();
		default:
			break;
		}
	}
}
