package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Tour;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        System.out.println("preceeding delivery address " + i.getNumber() );
        hw.setPreceedingDelivery(i);
    }

    @Override
	public State nextState(){
        return new DisplayingTourOnMapState();
    }
    
    @Override
	public void describeState(HomeWindow hw) {
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the delivery point"); 
	}
}