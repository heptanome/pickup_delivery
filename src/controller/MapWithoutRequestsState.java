package controller;

import model.Tour;
import view.HomeWindow;

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

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, true, false, false, false, false, false, false);
	}

}
