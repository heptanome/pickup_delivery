package controller;

import model.Map;
import model.MapParser;
import model.RequestParser;
import model.SetOfRequests;
import view.HomeWindow;
import view.WelcomeWindow;

public class Application {
  public static void main(String[] args) {
    
	System.out.println("Lancement de la 'Welcome Window'");
    WelcomeWindow welcomeWindow = new WelcomeWindow("Welcome Window", "Image/Logo_PD.png");
    System.out.println("Bienvenue sur Pickup and Delivery");
  }
  
  public static void loadMap (String chemin) {
	  System.out.println("Chargement de la Map localisée par le chemin : " + chemin);
	  
	  MapParser mp = new MapParser(chemin);
	  Map loadedMap = mp.loadMap();
	  
	  System.out.println("Lancement de la 'Home Window'");
	  HomeWindow homeWindow = new HomeWindow("Home Window", loadedMap);
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
}
