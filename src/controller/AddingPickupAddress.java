package controller;

import view.HomeWindow;
import model.Intersection;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("pickup address " + i.getNumber() );
        //hw.getNewRequest().setPickupAddress(s);
    }
    
    @Override
	public State nextState() throws Exception{
        return new AddingPointPreceedingPickup();
    }
    
    @Override
	public void describeState() throws Exception{
        System.out.println("Select a pickup point on the map(white point)");
	}
}
