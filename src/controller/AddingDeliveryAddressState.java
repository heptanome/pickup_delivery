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
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a){

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
	}


	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map  for the new request");  
	}

	@Override
    public  void setMouseListener(HomeWindow hw) {
		hw.removeAllMouseListeners();
		//The new delivery intersection can be any type of intersection : A special one (depot, pickup or delivery) or not.
		hw.addSingleMouseClickOnAnyPointListener();
	}


}
