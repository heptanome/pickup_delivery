package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import view.HomeWindow;
import model.Tour;


public class AddingPointPreceedingPickup implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        System.out.println("preceeding pickup address " + i.getNumber() );
        hw.setPreceedingPickup(i);

        //TODO : Send to the tour to update (fire event?) the tour (nothing implemented there yet)
    }
    
    @Override
	public State nextState() {
        return new AddingDeliveryAddress();
    }
    
    @Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the pickup point"); 
	}
}