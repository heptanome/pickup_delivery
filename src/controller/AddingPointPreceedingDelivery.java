package controller;

import view.HomeWindow;
import model.Intersection;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("preceeding delivery address " + i.getNumber() );
    }

    @Override
	public State nextState() throws Exception{
        return new WorkingState();
    }
    
    @Override
	public void describeState() throws Exception{
        System.out.println("Select a point on the map that will preceed the delivery point(colored point)");
	}
}