package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import view.HomeWindow;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
		int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a Number"));
		System.out.println("delivery address " + i.getNumber() + "Duration : " + duration);
		Request r = hw.getNewRequest();
		r.setDeliveryAddress(i);
		r.setDeliveryDuration(duration);
		hw.setNewRequest(r);
	}

	@Override
	public State nextState() throws Exception{
        return new AddingPointPreceedingDelivery();
	}

	@Override
	public void describeState(HomeWindow hw) throws Exception{
		JOptionPane.showMessageDialog(hw, "Select a delivery point on the map (white point) for the new request");  
	}

}
