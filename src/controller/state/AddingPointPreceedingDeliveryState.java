package controller.state;

import javax.swing.JOptionPane;

import controller.Application;
import controller.command.AddCompleteRequestCommand;
import controller.command.ListOfCommands;
import model.Intersection;
import model.Request;
import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the selection of the
 * intersection that will preceed the new pickup address when adding a request
 * to the tour.
 */
public class AddingPointPreceedingDeliveryState implements State {

	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
		describeState(hw);
		setMouseListener(hw);
		setHelp(hw);
	}

	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {
		// Check the validity of the point selected
		boolean isAfterPPP = tour.getRoadMap().checkPrecedence(hw.getPreceedingPickup(), i); // must be true to be valid
		boolean isLast = tour.getRoadMap().isLastIntersection(i); // must be false to be valid

		if (isLast) {
			// i can't be the last intersection of the tour (the depot)
			JOptionPane.showMessageDialog(hw,
					"<html>The point you chose is the depot. You can't add anything after the depot. "
							+ "  <br> Choose another point !</html>");
		} else if (!isAfterPPP) {
			// i is not after the point preceeding pickup, the user has to choose again
			JOptionPane.showMessageDialog(hw,
					"<html>The point you chose is visited before the one preceeding the new pickup, in the current tour. "
							+ "  <br> It is not possible, choose another one !</html>");
		} else {
			// Normal case
			// Set the point preceeding the delivery
			hw.setPreceedingDelivery(i);

			// Ask for confirmation
			Request r = hw.getNewRequest();

			String message = "<html>The new request is :<br>  <br>  - Pickup address " + r.getPickupAddress()
					+ " (pickup duration : " + r.getPickupDuration() + " minutes) <br> - To visit after the address "
					+ hw.getPreceedingPickup().getNumber() + " <br>  - Delivery address " + r.getDeliveryAddress()
					+ " (delivery duration : " + r.getDeliveryDuration()
					+ " minutes) <br> - To visit after the address " + hw.getPreceedingDelivery().getNumber()
					+ " <br> <br> Click \"Ok\" to add it to the tour, \"Cancel\" to change the last selected point.</html>";
			String title = "Confirm the new request to add";
			int reply = JOptionPane.showConfirmDialog(hw, message, title, JOptionPane.OK_CANCEL_OPTION);
			if (reply == JOptionPane.YES_OPTION) {

				// Update the tour
				a.getListOfCommands().removeWithOutUndoing();
				a.getListOfCommands().removeWithOutUndoing();
				a.getListOfCommands().removeWithOutUndoing();
				try {
					a.getListOfCommands().add(new AddCompleteRequestCommand(tour, hw.getNewRequest(),
							hw.getPreceedingDelivery(), hw.getPreceedingPickup()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Go to the next state (DisplayingTourOnMapState)
				a.setCurrentState(a.displayingTourState);
				a.getCurrentState().initiateState(a, hw);

			} else {
				initiateState(a, hw);
			}

		}

	}

	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw) {
		l.undo();
		a.setCurrentState(a.ada);
		a.getCurrentState().initiateState(a, hw);
	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw,
				"Add Request - step 4\nSelect a point on the map (colored point) that will preceed the delivery point");
	}

	/**
	 * Method called by the States to set the help message in the homeWindow,
	 * depending on the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setHelp(HomeWindow hw) {
		String message = "<html>Add Request - step 4\nSelect a point on<br> the map (colored <br>point) that will <br>preceed the delivery <br>point</html>";
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
		hw.addSingleMouseClickOnSpecialPointListener();
	}

	/**
	 * Method called by the state to update which buttons are enabled depending on
	 * the state
	 * 
	 * @param hw the HomeWindow
	 * @param l  the current listOfCommands
	 */
	private void setButtons(HomeWindow hw, ListOfCommands l) {
		hw.setButtonsEnabled(false, false, false, false, false, false, false, true, false, true, true);
	}
}