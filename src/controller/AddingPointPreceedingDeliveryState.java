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

            //TODO : Utiliser la methoe checkPrecedence de la roadMap pour verifier que hw.getPreceedingPickup est avant i
            // si oui faire ci dessous, si non message que pas valide et rester sur cet état.
            // Pour ce faire, elle est où la roadMap? Elle est dans tour.getRoadMap

            //Set the point preceeding the delivery
            System.out.println("preceeding delivery address " + i.getNumber() );
            hw.setPreceedingDelivery(i);
    
            //Inform the user of the complete requets that is going to be added
            Request r = hw.getNewRequest();
            JOptionPane.showMessageDialog(hw, "<html>The following request :<br>  - Pickup address " +r.getPickupAddress() +" (pickup duration : " + r.getPickupDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingPickup().getNumber() + " <br>  - Delivery address " +r.getDeliveryAddress() +" (delivery duration : " + r.getDeliveryDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingDelivery().getNumber() + " <br>Will be added to the tour.</html>");

            //TODO : compute the new tour (and display it)!
            tour.addRequest(hw.getNewRequest(), hw.getPreceedingDelivery(), hw.getPreceedingPickup());
            

            //Go to the next state (DisplayingTourOnMapState)
            a.setCurrentState(a.displayingTourState);

    }

    @Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the delivery point"); 
    }
    
    @Override
    public  void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
		hw.addSingleMouseClickOnSpecialPointListener();
    }
    
    @Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false, true);
	}
}