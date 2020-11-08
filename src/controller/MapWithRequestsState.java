package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying requests on the map before 
 * the tour calculation
 */
public class MapWithRequestsState implements State {
	
	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
	}
	
	@Override
	public void loadMap(Application a,HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().initiateState(a, homeWindow);
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

	@Override
	public void computeTour(Application a, HomeWindow hw, Tour tour) {
		try {
			tour.computeTour(); // returns a list of segments
			a.setCurrentState(a.displayingTourState);
			a.getListOfCommands().reset(); //To remove if we decide that we can undo/redo "compute tour"
			a.getCurrentState().initiateState(a, hw);
		}catch (Exception e) {
			
		}
	}


	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw){

		l.undo();
		a.setCurrentState(a.mapWoRequestsState);
		a.getCurrentState().initiateState(a, hw);
		//a.getCurrentState().setButtons(hw , l);
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
    private void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(true, true, true, false, false, false, false,  true, false, false);
	}

}
