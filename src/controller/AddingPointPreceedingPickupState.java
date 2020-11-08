package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;

/**
 * State class used by the controller to handle the selection of the intersection that will preceed the new delivery address
 * when adding a request to the tour.
 */
public class AddingPointPreceedingPickupState implements State {
	
	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
		describeState(hw);
		setMouseListener(hw);
	}

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {
    
            //Set the point prexeeding the pickup point
            System.out.println("preceeding pickup address " + i.getNumber() );
            hw.setPreceedingPickup(i);

            // Go to the next state (AddingDeliveryAdress)
			a.setCurrentState(a.ada);
			a.getCurrentState().initiateState(a, hw);
    }

    
	/**
	 * Method called by the States to display a message about specific information of the current State
	 * 
	 * @param hw the HomeWindow
	 */
	private void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the pickup point");
        System.out.println("appp");
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
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
    private void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false,  false, false, true);
	}
}