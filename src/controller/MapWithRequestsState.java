package controller;

import model.Tour;
import view.HomeWindow;

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

	@Override
	public void computeTour(Tour tour) throws Exception {
		tour.computeTour(); // returns a list of segments
	}

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, true, true, false, false, false, false, false);
	}

}
