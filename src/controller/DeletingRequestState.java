package controller;

import javax.swing.JOptionPane;

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
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a){
		Request requestToBeDeleted = hw.getRequestFromIntersection(i);
		// display the JOptionPane showConfirmDialog
		String message = "The request " + requestToBeDeleted.toString() + " will be deleted.";
		String title = "Confirm delete";
		int reply = JOptionPane.showConfirmDialog(hw, message, title, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			// if the user confirmed the delete the request will be deleted from the set of requests of the tour
			tour.deleteRequest(hw.getRequestFromIntersection(i));
			// TODO: update tour

		} 
		//Go to the next state : DisplayingTourOnMapState
		a.setCurrentState(a.displayingTourState);
		
    }
	
	@Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a colored point on the map so that the corresponding request will "
        		+ "be deleted (pickup and delivery point)");
	}

	@Override
    public  void setButtons(HomeWindow hw, ListOfCommands l) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false,  false, false, true);
	}

	//TODO :override setMouseListener here instead of setting it in Application.java

}
