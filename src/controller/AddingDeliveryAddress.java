package controller;

import view.HomeWindow;

public class AddingDeliveryAddress implements State {
	
	@Override
	public void pointClicked(String s, HomeWindow hw) throws Exception{
        System.out.println("delivery address " + s );
	}

	@Override
	public State nextState() throws Exception{
        return new AddingPointPreceedingDelivery();
	}

}
