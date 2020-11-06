package controller;

import model.Tour;

/**
 * State class used by the controller to handle the actions when it is displaying a map without requests.
 */
public class MapWithoutRequestsState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) throws Exception {
		tour.setRequests(fp);
	}

}
