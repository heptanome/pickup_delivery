package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;
import model.Request;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        try {
            System.out.println("preceeding delivery address " + i.getNumber() );
            hw.setPreceedingDelivery(i);
    
            Request r = hw.getNewRequest();
            JOptionPane.showMessageDialog(hw, "<html>The following request :<br>  - Pickup address " +r.getPickupAddress() +" (pickup duration : " + r.getPickupDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingPickup().getNumber() + " <br>  - Delivery address " +r.getDeliveryAddress() +" (delivery duration : " + r.getDeliveryDuration() 
                + " minutes) to visit after the address " + hw.getPreceedingDelivery().getNumber() + " <br>Will be added to the tour.</html>");
        } catch (NullPointerException e) {
	        JOptionPane.showMessageDialog(hw, "You did not chose a valid intersection.");
        } catch (Exception e) {
            System.out.println("An error occured");
        }

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