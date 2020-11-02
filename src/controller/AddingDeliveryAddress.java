package controller;

import view.HomeWindow;
import model.Intersection;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("delivery address " + i.getNumber());
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
