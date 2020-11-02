package controller;

import view.HomeWindow;

public class AddingPointPreceedingPickup implements State {
    @Override
	public void pointClicked(String s, HomeWindow hw) throws Exception{
        System.out.println("preceeding pickup address " + s );
    }
    
    @Override
	public State nextState() throws Exception{
        return new AddingDeliveryAddress();
	}
}