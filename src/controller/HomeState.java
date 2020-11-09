package controller;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is
 * displaying a tour on home page (when opening the application)
 */
public class HomeState implements State {

	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
	}

	@Override
	public void loadMap(Application a, HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
			a.getListOfCommands().add(new LoadMapCommand(tour, fp));
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().initiateState(a, homeWindow);
			homeWindow.addMouseMotionOnMapListener();
		} catch (Exception e) {
			a.setCurrentState(a.mapExceptionState);
			a.getCurrentState().initiateState(a, homeWindow);
			a.getCurrentState().handleException(a, e, homeWindow, this);
		}
	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw) {
		l.redo();
		a.setCurrentState(a.mapWoRequestsState);
		a.getCurrentState().initiateState(a, hw);
		// a.getCurrentState().setButtons(hw, l);
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on
	 * the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
	private void setButtons(HomeWindow hw, ListOfCommands l) {
		hw.setButtonsEnabled(true, false, false, false, false, false, false, false, l.redoPossible(), false, false);
	}

}
