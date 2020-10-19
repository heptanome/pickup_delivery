public class Application {
  public static void main(String[] args) {
    
	System.out.println("Lancement de la 'Welcome Window'");
    WelcomeWindow welcomeWindow = new WelcomeWindow("Welcome Window", "Image/Logo_PD.png");
    System.out.println("Bienvenue sur Pickup and Delivery");
  }
  
  public static void loadMap (String chemin) {
	  System.out.println("Fichier choisi dans controlleur : " + chemin);
	  
	  MapParser mp = new MapParser(chemin);
	  Map loadedMap = mp.loadMap();
	  
	  System.out.println("Lancement de la 'Home Window'");
	  HomeWindow homeWindow = new HomeWindow("Home Window", loadedMap);
  }
}
