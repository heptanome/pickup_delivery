package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a tour on the map
 */
public class DisplayingTourOnMapState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		tour.setMap(fp);
	}
	
	@Override
	public void loadRequests(String fp, Tour tour) throws Exception {
		tour.setRequests(fp);
	}

	@Override
	public void computeTour(Tour tour) throws Exception {
		tour.computeTour(); // returns a list of segments
	}

	@Override
	public void setMouseListener(HomeWindow hw)  {	
		hw.removeAllMouseListeners();
		hw.addMouseOnMapListener();
	}

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, true, false, true, true, true, false, true);
	}

}
