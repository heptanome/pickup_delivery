package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import view.HomeWindow;
import model.Tour;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
		int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a Number"));
		System.out.println("delivery address " + i.getNumber() + "Duration : " + duration);
		Request r = hw.getNewRequest();
		r.setDeliveryAddress(i);
		r.setDeliveryDuration(duration);
		hw.setNewRequest(r);

	}

	@Override
	public State nextState() {
        return new AddingPointPreceedingDelivery();
	}

	@Override
	public void describeState(HomeWindow hw) {
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map  for the new request");  
	}

}
