package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

/**
 * State class used by the controller to handle the selection of the new delivery address (and its delivery duration)
 * when adding a requets to the tour.
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

			//Get delivery duration
			int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a delivery duration (number of minutes)"));
			System.out.println("delivery address " + i.getNumber() + "Duration : " + duration);

			//Update de new request
			Request r = hw.getNewRequest();
			r.setDeliveryAddress(i);
			r.setDeliveryDuration(duration);
			hw.setNewRequest(r);

			//Go to the next state (AddingPointPreceedingDeliveryState)
			a.setCurrentState(a.appd);
			a.getCurrentState().initiateState(a, hw);
	}


	/**
	 * Method called by the States to display a message about specific information of the current State
	 * 
	 * @param hw the HomeWindow
	 */
	private void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map  for the new request");  
		System.out.println("ada");
	}

	/**
	 * Method called by the state to change the mouse listeners of a HomeWindow
	 * according to the State
	 * 
	 * @param hw the HomeWindow
	 */
    private void setMouseListener(HomeWindow hw) {
		hw.removeAllMouseListeners();
		//The new delivery intersection can be any type of intersection : A special one (depot, pickup or delivery) or not.
		hw.addSingleMouseClickOnAnyPointListener();
	}


	/**
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
    private void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false,  false, false, true);
	}


}
