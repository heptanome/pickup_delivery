public class Application {
  public static void main(String[] args) {
    System.out.println("Bienvenue sur Pickup and Delivery");

    MapParser mp = new MapParser("./XML_data/smallMap.xml");
    Map loadedMap = mp.loadMap();

    //Window window = new Window("Main", "Image/Logo_PD.png");
    HomeWindow window = new HomeWindow("Main", loadedMap);
  }
  
  public static void loadMap (String chemin) {
	  System.out.println("Fichier choisi dans controlleur : " + chemin);
	  
	  MapParser mp = new MapParser(chemin);
	  Map loadedMap = mp.loadMap();
  }
}
