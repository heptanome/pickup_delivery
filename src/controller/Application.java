package controller;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.Map;
import model.MapParser;
import model.RequestParser;
import model.SetOfRequests;
import model.Tour;
import view.HomeWindow;
import view.WelcomeWindow;

public class Application implements PropertyChangeListener {
	private WelcomeWindow welcomeWindow;
	private Tour tour;
	
	public Application(WelcomeWindow ww, Tour t) {
		this.tour = t;
		this.welcomeWindow = ww;
		this.tour.addPropertyChangeListener(this.welcomeWindow);
		this.welcomeWindow.addPropertyChangeListener(this);
	}
	
  public static void main(String[] args) {
	System.out.println("Bienvenue sur Pickup and Delivery");
	
	Tour tour = new Tour();
    WelcomeWindow welcomeWindow = new WelcomeWindow("Welcome Window", "Image/Logo_PD.png");
    Application app = new Application(welcomeWindow, tour);
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
	  this.loadMap((String) evt.getNewValue());
  }
}
