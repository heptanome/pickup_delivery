package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a map without requests.
 */
public class MapWithoutRequestsState implements State {
	
	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw);
	}
	
	@Override
	public void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
		} catch (Exception e) {
			a.setCurrentState(a.mapExceptionState);
			a.getCurrentState().initiateState(a, homeWindow);
			a.getCurrentState().handleException(a,e,homeWindow,this);
		}
	}
	
	@Override
	public void loadRequests(Application a, HomeWindow hw,  String fp, Tour tour) {
		try {
			tour.setRequests(fp);
			a.setCurrentState(a.mapWithRequestsState);
			a.getCurrentState().initiateState(a, hw);
		} catch (Exception e) {
			a.setCurrentState(a.requestExceptionState);
			a.getCurrentState().initiateState(a, hw);
			a.getCurrentState().handleException(a,e,hw,this);
		}
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 */
    private  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(true, true, false, false, false, false, false, true);
	}

}
