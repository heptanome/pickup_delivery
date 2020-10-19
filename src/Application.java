public class Application {
  public static void main(String[] args) {
    System.out.println("Bienvenue sur Pickup and Delivery");

    Window window = new Window("Main", "Image/Logo_PD.png");
  }
  
  public static void loadMap (String chemin) {
	  System.out.println("Fichier choisi dans controlleur : " + chemin);
	  
	  MapParser mp = new MapParser(chemin);
	  Map loadedMap = mp.loadMap();
  }
}
