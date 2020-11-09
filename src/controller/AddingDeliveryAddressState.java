package controller;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the selection of the new
 * delivery address (and its delivery duration) when adding a requets to the
 * tour.
 */
public class AddingDeliveryAddressState implements State {

	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
		describeState(hw);
		setMouseListener(hw);
	}

	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {

		boolean isLast = tour.getRoadMap().isLastIntersection(i); // must be false to be valid

		if (isLast) {
			// i can't be the last intersection of the tour (the depot)
			JOptionPane.showMessageDialog(hw, "<html>The point you chose is the depot. It cannot be a delivery point. "
					+ "  <br> Choose another point !</html>");
		} else {
			// Get delivery duration
			int duration = -1;
			while (duration < 0) {
				String dialog = JOptionPane.showInputDialog(hw, "Enter a delivery duration (number of minutes)", 5);
				duration = Integer.parseInt(dialog);
			}

			// Update de new request
			Request r = hw.getNewRequest();
			r.setDeliveryAddress(i);
			r.setDeliveryDuration(duration);
			hw.setNewRequest(r);

			// Go to the next state (AddingPointPreceedingDeliveryState)
			a.getListOfCommands().add(new AddDeliveryCommand(i, hw, duration));
			a.setCurrentState(a.appd);
			a.getCurrentState().initiateState(a, hw);
		}
	}

	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw) {
		l.undo();
		a.setCurrentState(a.appp);
		a.getCurrentState().initiateState(a, hw);
	}

	@Override
	public void redo(ListOfCommands l, Application a, HomeWindow hw) {
		l.redo();
		a.setCurrentState(a.appd);
		a.getCurrentState().initiateState(a, hw);
	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map  for the new request");
		System.out.println("ada");
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
		// The new delivery intersection can be any type of intersection : A special one
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

}
