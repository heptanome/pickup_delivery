package controller;

import view.HomeWindow;
import model.Intersection;
import model.Request;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
		System.out.println("delivery address " + i.getNumber());
		Request r = hw.getNewRequest();

		//TODO :  AddJOptionPane to ask for pickup duration and add it
		
		r.setDeliveryAddress(i);
		hw.setNewRequest(r);
	}

	@Override
	public State nextState() throws Exception{
        return new AddingPointPreceedingDelivery();
	}

	@Override
	public void describeState() throws Exception{
        System.out.println("Select a delivery point on the map (white point)");
	}

}
