package controller.state;

import javax.swing.JOptionPane;

import controller.Application;
import controller.command.DeleteRequestCommand;
import controller.command.ListOfCommands;
import model.Intersection;
import model.Request;
import model.Tour;
import view.HomeWindow;

/**
 * State class used by the controller to handle the selection of the request to delete in the process of deleting
 * a requets from the tour.
 */
public class DeletingRequestState implements State {
	
	@Override
	public void initiateState(Application a, HomeWindow hw) {
		setButtons(hw, a.getListOfCommands());
		describeState(hw);
		setMouseListener(hw);
		setHelp(hw);
	}
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a){
		int nbRequets = 1;
		Request requestToBeDeleted = hw.getRequestFromIntersection(i);
		// display the JOptionPane showConfirmDialog
		String message = "The request " + requestToBeDeleted.toString() + " will be deleted.";
		String title = "Confirm delete";
		int reply = JOptionPane.showConfirmDialog(hw, message, title, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			// if the user confirmed the delete the request will be deleted from the set of requests of the tour
			
			Intersection preceedingPickup = tour.getIntersectionBefore(requestToBeDeleted.getPickup());
			Intersection preceedingDelivery = tour.getIntersectionBefore(requestToBeDeleted.getDelivery());

			a.getListOfCommands().add(new DeleteRequestCommand(requestToBeDeleted, tour, preceedingPickup, preceedingDelivery));
			nbRequets = tour.getSOR().getRequests().size();
		} 
		
		if(nbRequets!=0) {
			//Go to the next state : DisplayingTourOnMapState
			a.setCurrentState(a.displayingTourState);
			a.getCurrentState().initiateState(a, hw);
		} else {
			//Go to the next state : DisplayingMapWithoutRequetsState
			a.setCurrentState(a.mapWoRequestsState);
			a.getCurrentState().initiateState(a, hw); 
		}
		
	}
	
	@Override
	public void undo(ListOfCommands l, Application a, HomeWindow hw){
		a.setCurrentState(a.displayingTourState);
		a.getCurrentState().initiateState(a, hw);
	}
	
	@Override
	public void describeState(HomeWindow hw) {
		String message = "<html>Select a colored<br> point on the map so that <br>the corresponding request will<br></html> "
						+ "be deleted (pickup <br>and delivery point)";
		hw.setHelpText(message);
		JOptionPane.showMessageDialog(hw, "Select a colored point on the map so that the correspondingrequest will "
		+ "be deleted (pickup and delivery point)");
	}


	/**
	 * Method called by the States to set the help message in the homeWindow, depending on the State
	 * 
	 * @param hw the HomeWindow
	 */
	private void setHelp(HomeWindow hw){
		String message = "<html>Map and requests <br>were loaded successfully. <br>Let's compute a tour!</html>";
		hw.setHelpText(message);
    }
	
	/**
	 * Method called by the state to change the mouse listeners of a HomeWindow
	 * according to the State
	 * 
	 * @param hw the HomeWindow
	 */
    private void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
        //The new pickup intersection can be any type of intersection : A special one (depot, pickup or delivery) or not.
		hw.addSingleMouseClickOnSpecialPointListener();
    }


	/**
	 * Method called by the state to update which buttons are enabled depending on the state
	 * 
	 * @param hw the HomeWindow
	 * @param l the current listOfCommands
	 */
    private void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false,  true, false, true, false);
	}


}
