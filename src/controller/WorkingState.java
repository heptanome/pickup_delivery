package controller;

import model.Tour;

public class WorkingState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) {
		// specific behavior of the working state when loading a map
		
		System.out.println("Chargement de la Map localisée par le chemin : " + fp);
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) {
		// specific behavior of the working state when loading requests
		
		System.out.println("Chargement de la requête localisée par le chemin : " + fp);
		tour.setRequests(fp);
	}
	
	@Override
	public void addRequest() {
		// specific behavior of the working state when adding a request
		
		// TODO
		
	}

	@Override
	public void deleteRequests() {
		// specific behavior of the working state when deleting a request
		
		// TODO
	}
	
	@Override
	public void computeTour(Tour tour) {
		// specific behavior of the working state when computing a tour		
		System.out.println("Calcul d'un chemin");
		tour.computeTour(); // <-- renvoie une liste de segment
	}

}
