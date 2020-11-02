package controller;

import model.Tour;

public class MapWithoutRequestsState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) throws Exception {
		// specific behavior of the state when loading requests
		tour.setRequests(fp);
	}

}
