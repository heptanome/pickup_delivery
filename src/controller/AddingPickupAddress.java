package controller;

import view.HomeWindow;
import model.Intersection;
import model.Request;
import model.Tour;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        System.out.println("pickup address " + i.getNumber() );


		//TODO :  AddJOptionPane to ask for pickup duration and add it below
		Request newR = new Request(i, new Intersection("",0,0), 0,0);
		hw.setNewRequest(newR);
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
