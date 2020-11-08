package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is displaying a tour on the map
 */
public class DisplayingTourOnMapState implements State {
	
	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
		setMouseListener(hw);
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
	public void addRequests(Application a, HomeWindow hw) {
		System.out.println("Ajout d'une requête : ");
		a.setCurrentState(a.apa);
		a.getCurrentState().initiateState(a, hw);
	}
	
	@Override
	public void deleteRequests(Application a, HomeWindow hw)  {
		System.out.println("Suppression d'une requête");
		a.setCurrentState(a.deleteRequestState);
		a.getCurrentState().initiateState(a,hw);
	}

	@Override
	public void computeTour(Application a, HomeWindow hw, Tour tour) {
		try {
			tour.computeTour(); // returns a list of segments
		}catch (Exception e) {
			
		}
	}
	
	/**
	 * Method called by the state to change the mouse listeners of a HomeWindow
	 * according to the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setMouseListener(HomeWindow hw)  {	
		hw.removeAllMouseListeners();
		hw.addMouseOnMapListener();
	}


	/**
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 */
    private void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(true, true, false, true, true, true, false , false, false, true);
	}

}
