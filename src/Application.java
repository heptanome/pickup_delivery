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
  
  public static void loadRequest(String chemin) {
	  System.out.println("Chargement de la requête localisée par le chemin : " + chemin);
	  
	  RequestParser rp = new RequestParser(chemin);
	  SetOfRequests setOfRequest = rp.loadRequests();
	  
	  //TODO : Afficher les requêtes
  }
}
