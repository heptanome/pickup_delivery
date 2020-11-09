package controller;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
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
			int duration = Integer
					.parseInt(JOptionPane.showInputDialog(hw, "Enter a pickup duration (number of minutes)", 5));
			System.out.println("Pickup address " + i.getNumber() + " Duration :" + duration);

			// Set the new request
			Request newR = new Request(new Intersection("", 0, 0), new Intersection("", 0, 0), 0, 0);
			newR.setPickupAddress(i);
			newR.setPickupDuration(duration);
			hw.setNewRequest(newR);

			// Go to the next state (AddingPointPreceedingPickupState)
			a.getListOfCommands().add(new AddPickupCommand(i, hw, duration));
			a.setCurrentState(a.appp);
			a.getCurrentState().initiateState(a, hw);
		}

	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a pickup point on the map for the new request");
		System.out.println("apa");
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
