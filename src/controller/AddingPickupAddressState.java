package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

/**
 * State class used by the controller to handle the selection of the new pickup address (and its pickup duration)
 * when adding a request to the tour.
 */
public class AddingPickupAddressState implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) {
  
            //Get pickup duration
	        int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a pickup duration (number of minutes)"));
            System.out.println("Pickup address " + i.getNumber() + " Duration :" + duration );

            //Set the new request
            Request newR = new Request(new Intersection("",0,0), new Intersection("",0,0), 0 ,0);
            newR.setPickupAddress(i);
            newR.setPickupDuration(duration);
            hw.setNewRequest(newR);

            //Go to the next state (AddingPointPreceedingPickupState)
			a.setCurrentState(a.appp);
			a.getCurrentState().setButtons(hw);
			a.getCurrentState().describeState(hw);
			a.getCurrentState().setMouseListener(hw);

    }
    
    
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Select a pickup point on the map for the new request");
		System.out.println("apa");
    }
    
	@Override
    public void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
        //The new pickup intersection can be any type of intersection : A special one (depot, pickup or delivery) or not.
		hw.addSingleMouseClickOnAnyPointListener();
    }
    
    @Override
    public  void setButtons(HomeWindow hw) {
        hw.setButtonsEnabled(false, false, false, false, false, false, false, true);
	}
}
