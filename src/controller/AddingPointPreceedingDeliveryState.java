package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;
import model.Request;

/**
 * State class used by the controller to handle the selection of the intersection that will preceed the new pickup address
 * when adding a request to the tour.
 */
public class AddingPointPreceedingDeliveryState implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {

            //Check the validity of the point selected
    		boolean isAfterPPP = tour.getRoadMap().checkPrecedence(hw.getPreceedingPickup(), i); //must be true to be valid
    		boolean isLast = tour.getRoadMap().isLastIntersection(i); // must be false to be valid
            
            if (isLast) {
            //i can't be the last intersection of the tour (the depot) 
            JOptionPane.showMessageDialog(hw, "<html>The point you chose is the depot. You can't add anything after the depot. "
            + "  <br> Choose another point !</html>");
            } else if(!isAfterPPP ){
                //i is not after the point preceeding pickup, the user has to choose again
                JOptionPane.showMessageDialog(hw, "<html>The point you chose is visited before the one preceeding the new pickup, in the current tour. "
                + "  <br> It is not possible, choose another one !</html>");
            } else  {
                //Normal case
                //Set the point preceeding the delivery
                System.out.println("preceeding delivery address " + i.getNumber() );
                hw.setPreceedingDelivery(i);
        
                //Inform the user of the complete requets that is going to be added
                Request r = hw.getNewRequest();
                JOptionPane.showMessageDialog(hw, "<html>The following request :<br>  - Pickup address " +r.getPickupAddress() +" (pickup duration : " + r.getPickupDuration() 
                    + " minutes) to visit after the address " + hw.getPreceedingPickup().getNumber() + " <br>  - Delivery address " +r.getDeliveryAddress() +" (delivery duration : " + r.getDeliveryDuration() 
                    + " minutes) to visit after the address " + hw.getPreceedingDelivery().getNumber() + " <br>Will be added to the tour.</html>");

                //Update the tour
                tour.addRequest(hw.getNewRequest(), hw.getPreceedingDelivery(), hw.getPreceedingPickup());

                //Go to the next state (DisplayingTourOnMapState)
    			a.setCurrentState(a.displayingTourState);
    			a.getCurrentState().setButtons(hw);
    			a.getCurrentState().setMouseListener(hw);
            }

    }

    @Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the delivery point"); 
        System.out.println("appd");
    }
    
	@Override
    public void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
		hw.addSingleMouseClickOnSpecialPointListener();
    }
    
    @Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false, true);
	}
}