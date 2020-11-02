package controller;

import model.Tour;

public class WorkingState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		// specific behavior of the working state when loading a map
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) throws Exception {
		// specific behavior of the working state when loading requests
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
	public void computeTour(Tour tour) throws Exception {
		// specific behavior of the working state when computing a tour		
		tour.computeTour(); // <-- renvoie une liste de segment
	}

}
