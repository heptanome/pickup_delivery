package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;

public class AddingPointPreceedingDelivery implements State {
    @Override
	public void pointClicked(Intersection i, HomeWindow hw) throws Exception{
        System.out.println("preceeding delivery address " + i.getNumber() );
        hw.setPreceedingDelivery(i);
    }

    @Override
	public State nextState() throws Exception{
        return new WorkingState();
    }
    
    @Override
	public void describeState(HomeWindow hw) throws Exception{
        JOptionPane.showMessageDialog(hw, "Select a point on the map (colored point) that will preceed the delivery point"); 
	}
}