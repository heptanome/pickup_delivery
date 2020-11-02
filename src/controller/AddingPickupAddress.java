package controller;

import view.HomeWindow;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(String s, HomeWindow hw) throws Exception{
        System.out.println("pickup address " + s );
        //hw.getNewRequest().setPickupAddress(s);
    }
    
    @Override
	public State nextState() throws Exception{
        return new AddingPointPreceedingPickup();
	}
}
