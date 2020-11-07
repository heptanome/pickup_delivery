package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a tour on home page (when opening the application)
 */
public class HomeState implements State {
	
	@Override
	public void loadMap(String fp, Tour tour) throws Exception {
		tour.setMap(fp);
	}

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, false, false, false, false, false, false, false);
	}

}
