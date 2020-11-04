package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;
import view.HomeWindow;


public class AddingPointPreceedingPickup implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        System.out.println("preceeding pickup address " + i.getNumber() );
        hw.setPreceedingPickup(i);
        
        
    }
    
    @Override
	public State nextState() {
        return new AddingDeliveryAddress();
    }
    
    @Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the pickup point"); 
    }
    
    @Override
    public  void setMouseListener(HomeWindow hw) {
        hw.removeAllMouseListeners();
		hw.addSingleMouseClickOnSpecialPointListener();
	}
}