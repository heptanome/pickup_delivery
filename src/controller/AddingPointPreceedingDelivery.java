package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;
import model.Request;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {

            //Set the point preceeding the delivery
            System.out.println("preceeding delivery address " + i.getNumber() );
            hw.setPreceedingDelivery(i);
    
            //Inform the user of the complete requets that is going to be added
            Request r = hw.getNewRequest();
            JOptionPane.showMessageDialog(hw, "<html>The following request :<br>  - Pickup address " +r.getPickupAddress() +" (pickup duration : " + r.getPickupDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingPickup().getNumber() + " <br>  - Delivery address " +r.getDeliveryAddress() +" (delivery duration : " + r.getDeliveryDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingDelivery().getNumber() + " <br>Will be added to the tour.</html>");

            //Go to the next state (DisplayingTourOnMapState)
            a.setCurrentState(a.displayingTourState);



    }

    @Override
	public State nextState(){
        return new DisplayingTourOnMapState();
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
}