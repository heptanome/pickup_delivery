package controller;

import model.Tour;

public class MapWithoutRequestsState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) {
		System.out.println("Chargement de la Map localisée par le chemin : " + fp);
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) {
		// specific behavior of the state when loading requests
		
		System.out.println("Chargement de la requête localisée par le chemin : " + fp);
		tour.setRequests(fp);
	}

}
