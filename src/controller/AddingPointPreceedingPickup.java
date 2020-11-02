package controller;

import view.HomeWindow;
import model.Intersection;

public class AddingPointPreceedingPickup implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("preceeding pickup address " + i.getNumber() );
    }
    
    @Override
	public State nextState() throws Exception{
        return new AddingDeliveryAddress();
    }
    
    @Override
	public void describeState() throws Exception{
        System.out.println("Select a point on the map that will preceed the pickup point(colored point)");
	}
}