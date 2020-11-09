package controller.state;

import javax.swing.JOptionPane;

import controller.Application;
import controller.command.AddPickupCommand;
import controller.command.ListOfCommands;
import model.Intersection;
import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the selection of the new pickup
 * address (and its pickup duration) when adding a request to the tour.
 */
public class AddingPickupAddressState implements State {

	@Override
	public void initiateState(Application a, HomeWindow hw) {
		describeState(hw);
		setButtons(hw, a.getListOfCommands());
		setMouseListener(hw);
		setHelp(hw);
	}

	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {

		boolean isLast = tour.getRoadMap().isLastIntersection(i); // must be false to be valid

		if (isLast) {
			// i can't be the last intersection of the tour (the depot)
			JOptionPane.showMessageDialog(hw, "<html>The point you chose is the depot. It cannot be a pickup point. "
					+ "  <br> Choose another point !</html>");
		} else {
			// Get pickup duration
			int duration = -1;
			String result = null;
			while (duration < 0) {
				result = JOptionPane.showInputDialog(hw, "Enter a pickup duration (number of minutes)", 5);
				if(result == null) {
					break;
				}
				duration = Integer.parseInt(result);
			}
			
			if (result != null) {
				a.getListOfCommands().add(new AddPickupCommand(i, hw, duration));
	
				// Go to the next state (AddingPointPreceedingPickupState)
				a.setCurrentState(a.appp);
				a.getCurrentState().initiateState(a, hw);
			} else {
				a.getCurrentState().initiateState(a, hw);
			}
		}

	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw,  "Add Request - step 1\nSelect a pickup point on the map for the new request");
		System.out.println("apa");
	}

	/**
	 * Method called by the States to set the help message in the homeWindow, depending on the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setHelp(HomeWindow hw){
		String message = "<html>Add Request - step 1\nSelect a pickup point<br> on the map for <br>the new request</html>";
		hw.setHelpText(message);
	}
	
	@Override
	public void cancel(Application a, HomeWindow hw) {
		a.setCurrentState(a.displayingTourState);
		a.getCurrentState().initiateState(a, hw);
	}

	/**
	 * Method called by the state to change the mouse listeners of a HomeWindow
	 * according to the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setMouseListener(HomeWindow hw) {
		hw.removeAllMouseListeners();
		// The new pickup intersection can be any type of intersection : A special one
		// (depot, pickup or delivery) or not.
		hw.addSingleMouseClickOnAnyPointListener();
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on
	 * the state
	 * 
	 * @param hw the HomeWindow
	 * @param l  the current listOfCommands
	 */
	private void setButtons(HomeWindow hw, ListOfCommands l) {
		hw.setButtonsEnabled(false, false, false, false, false, false, false, true, l.redoPossible(), true, true);
	}

	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw) {
		a.setCurrentState(a.displayingTourState);
		a.getCurrentState().initiateState(a, hw);
	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw) {
		l.redo();
		a.setCurrentState(a.appp);
		a.getCurrentState().initiateState(a, hw);
	}
}
