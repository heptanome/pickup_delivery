package controller;

import javax.swing.JOptionPane;

import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the actions when it is
 * displaying a map without requests.
 */
public class MapWithoutRequestsState implements State {

	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
	}

	@Override
	public void loadMap(Application a, HomeWindow homeWindow, String fp, Tour tour) {
		try {
			tour.setMap(fp);
		} catch (Exception e) {
			a.setCurrentState(a.mapExceptionState);
			a.getCurrentState().initiateState(a, homeWindow);
			a.getCurrentState().handleException(a, e, homeWindow, this);
		}
	}

	@Override
	public void loadRequests(Application a, HomeWindow hw, String fp, Tour tour) {
		try {
			tour.setRequests(fp);
			a.getListOfCommands().add(new LoadRequestsCommand(tour, fp));
			a.setCurrentState(a.mapWithRequestsState);
			a.getCurrentState().initiateState(a, hw);
		} catch (Exception e) {
			a.setCurrentState(a.requestExceptionState);
			a.getCurrentState().initiateState(a, hw);
			a.getCurrentState().handleException(a, e, hw, this);
		}
	}

	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw) {

		l.undo();
		a.setCurrentState(a.homeState);
		a.getCurrentState().initiateState(a, hw);
		// a.getCurrentState().setButtons(hw , l);

	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw) {
		l.redo();
		a.setCurrentState(a.mapWithRequestsState);
		a.getCurrentState().initiateState(a, hw);
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on
	 * the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
	private void setButtons(HomeWindow hw, ListOfCommands l) {
		hw.setButtonsEnabled(true, true, false, false, false, false, false, true, l.redoPossible(), false);
	}
	
    /**
	 * Method called by the state to display a message with specific information about the state
	 * 
	 * @param hw the HomeWindow
	 */
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "A map was loaded successfully. Go on and load some requests.");
		System.out.println("apa");
    }

}
