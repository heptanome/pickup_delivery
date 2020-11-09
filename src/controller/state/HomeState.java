package controller.state;

import javax.swing.JOptionPane;

import controller.Application;
import controller.command.ListOfCommands;
import controller.command.LoadMapCommand;
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
		setHelp(hw);
	}

	@Override
	public void loadMap(Application a, HomeWindow homeWindow, String fp, Tour tour) {
		try {
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
		hw.setButtonsEnabled(true, false, false, false, false, false, false, false, l.redoPossible(), true, false);
	}
	
  
    @Override
	public void describeState(HomeWindow hw){
		JOptionPane.showMessageDialog(hw,"No map has been loaded so far. Let's load a map first.");
		System.out.println("apa");
	}
	
	/**
	 * Method called by the States to set the help message in the homeWindow, depending on the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setHelp(HomeWindow hw){
		String message = "<html>No map has been loaded <br>so far. Let's load<br> a map first.</html>";
		hw.setHelpText(message);
    }

}
