package controller;

import view.HomeWindow;

import javax.swing.JOptionPane;

import model.Intersection;
import model.Request;
import model.Tour;

public class AddingPickupAddress implements State {

    @Override
	public void pointClicked(Intersection i, HomeWindow hw, Tour tour) throws Exception{
        int duration = Integer.parseInt(JOptionPane.showInputDialog (hw, "Enter a Number"));
        System.out.println("pickup address " + i.getNumber() + " Duration :" + duration );
        Request newR = new Request(new Intersection("",0,0), new Intersection("",0,0), 0 ,0);
        newR.setPickupAddress(i);
        newR.setPickupDuration(duration);
        hw.setNewRequest(newR);
    }
    
    @Override
	public State nextState() {
        return new AddingPointPreceedingPickup();
    }
    
    @Override
	public void describeState(HomeWindow hw){
        JOptionPane.showMessageDialog(hw, "Select a pickup point on the map for the new request"); 
	}
}
