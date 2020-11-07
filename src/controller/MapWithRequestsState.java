package controller;

import java.io.IOException;

import org.xml.sax.SAXException;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying requests on the map before 
 * the tour calculation
 */
public class MapWithRequestsState implements State {
	
	@Override
	public void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().setButtons(homeWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadRequests(Application a, HomeWindow hw,  String fp, Tour tour) {
		try {
			tour.setRequests(fp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void computeTour(Application a, HomeWindow hw, Tour tour) {
		try {
			tour.computeTour(); // returns a list of segments
			a.setCurrentState(a.displayingTourState);
			a.getCurrentState().setButtons(hw);
		}catch (Exception e) {
			
		}
	}

	@Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, true, true, false, false, false, false, false);
	}

}
