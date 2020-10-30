package controller;

import model.Tour;

public class HomeState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) {
		System.out.println("Chargement de la Map localisée par le chemin : " + fp);
		tour.setMap(fp);
	}

}
