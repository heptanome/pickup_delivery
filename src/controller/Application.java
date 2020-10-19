package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.RequestParser;
import model.SetOfRequests;
import model.Tour;
import view.HomeWindow;

public class Application implements PropertyChangeListener {
	private HomeWindow homeWindow;
	private Tour tour;
	
	public static void main(String[] args) {
			System.out.println("Bienvenue sur Pickup and Delivery");
			
			Tour tour = new Tour();
		    //WelcomeWindow welcomeWindow = new WelcomeWindow("Welcome Window", "Image/Logo_PD.png");
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
  
  public void loadMap (String fp) {
	  System.out.println("Chargement de la Map localisée par le chemin : " + fp);
	  this.tour.setMap(fp);
  }
  
  public static SetOfRequests loadRequest(String chemin) {
	  System.out.println("Chargement de la requête localisée par le chemin : " + chemin);
	  
	  RequestParser rp = new RequestParser(chemin);
	  SetOfRequests setOfRequest = rp.loadRequests();
	  return setOfRequest;
  }
  
  public static void addRequest() {
	  System.out.println("ajout d'une requête : ");
	  
	  //TODO : A implémenter
  }
  
  public static void deleteRequest() {
	  System.out.println("Suppression d'une requête");

	  //TODO : A implémenter
  }
  
  public static void computeTour() {
	  System.out.println("Calcul d'un chemin");

	  //TODO : A implémenter
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
	  String propName = evt.getPropertyName();
	  
	  switch(propName) {
	  	case "loadMap":
	  		this.loadMap((String) evt.getNewValue());
	  		break;
	  	default:
	  		break;
	  }
  }
}
