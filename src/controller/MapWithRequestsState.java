package controller;

import model.Tour;

/**
 * State class used by the controller to handle the actions when it is displaying requests on the map before 
 * the tour calculation
 */
public class MapWithRequestsState implements State {
	
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
