package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import view.HomeWindow;

public class AddingPointPreceedingPickup implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("preceeding pickup address " + i.getNumber() );
        hw.setPreceedingPickup(i);

        //TODO : Send to the tour to update (fire event?) the tour (nothing implemented there yet)
    }
    
    @Override
	public State nextState() throws Exception{
        return new AddingDeliveryAddress();
    }
    
    @Override
	public void describeState(HomeWindow hw) throws Exception{
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the pickup point"); 
	}
}