package controller;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;
import view.HomeWindow;

public class DeleteRequestState implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        try {
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
        }
        catch (NullPointerException e) {
	        JOptionPane.showMessageDialog(hw, "You did not chose a valid intersection.");
        }
    }
	
	@Override
	public void describeState(HomeWindow hw) throws Exception{
        JOptionPane.showMessageDialog(hw, "Select a colored point on the map so that the corresponding request will "
        		+ "be deleted (pickup and delivery point)");
	}
	
	@Override
	public State nextState() throws Exception{
		// TODO: better not instanciate a new instance
        return new WorkingState();
    }
}
