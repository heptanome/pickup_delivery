package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour, Application a) throws Exception{
		try {
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
        } catch (NullPointerException e) {
	        JOptionPane.showMessageDialog(hw, "You did not chose a valid intersection.");
        } catch (Exception e) {
            System.out.println("An error occured");
        }


	}

	@Override
	public State nextState() {
        return new AddingPointPreceedingDelivery();
	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map  for the new request");  
	}

	@Override
    public  void setMouseListener(HomeWindow hw) {
		hw.removeAllMouseListeners();
		hw.addSingleMouseClickOnAnyPointListener();
	}


}
