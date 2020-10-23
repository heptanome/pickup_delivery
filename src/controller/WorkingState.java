package controller;

import model.Tour;

public class WorkingState implements State {
	
	public WorkingState() {
		
	}
	
	@Override
	public void loadMap(String fp, Tour tour) {
		// specific behavior of the working state when loading a map
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) {
		// specific behavior of the working state when loading requests
	}
	
	@Override
	public void addRequest() {
		// specific behavior of the working state when adding a request
		
	}

	@Override
	public void deleteRequests() {
		// specific behavior of the working state when deleting a request
	}
	
	@Override
	public void computeTour(Tour tour) {
		// specific behavior of the working state when computing a tour		
		System.out.println("Calcul d'un chemin");
		tour.computeTour(); // <-- renvoie une liste de segment
	}

}
